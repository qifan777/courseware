<template>
	<view>
		<view class="top">
			<u-swiper border-radius="0" :duration="1000" @click="swiperChange" height="500" :list="imgList" mode="dot"
			 indicator-pos="bottomCenter" v-if="imgList.length>0"></u-swiper>
		</view>
		<view class="divider">
			<view class="front">为您推荐</view>
			<view class="rear">RECOMMENDED TO YOU</view>
		</view>
		<scroll-view scroll-y="true" class="coursewares" @scrolltolower="loadCW">
			<view class="courseware" v-for="(course,index) in coursewares" :key="index" @click="switchToDtails(course)">
				<image class="cover" :src="getImgPath(course.cover)"></image>
				<view class="info">
					<view class="name">{{course.name}}</view>
					<view class="buy-num">{{course.count+'人已购买'}}</view>
					<view class="price-addbtn">
						<view class="price">{{'￥'+course.price}}</view>
						<image src="../../static/icon/gouwuche.png"></image>
					</view>
				</view>
			</view>
		</scroll-view>
		<view>
			<u-modal v-model="show" :show-cancel-button="true" :show-confirm-button="false" title="是否同意获取你的公开信息(头像,昵称)">
				<view class="center">
					<u-button type="primary" size="medium" open-type="getUserInfo" @getuserinfo="initUserInfo">
						确认
					</u-button>
				</view>
			</u-modal>
		</view>
	</view>
</template>

<script>

	import {
		getUserInfo
	} from '@/utils/api.js'

	export default {
		data() {
			return {
				userInfo: "",
				coursewares: [],
				swiperList: [],
				imgList: [],
				start: 1,
				finish: false,
				show: false
			}
		},
		methods: {
			//点击课件封面,如果课件的免费的,直接会进入详情页查看,如果是收费的会弹出收费
			switchToDtails(row) {
				if (!this.userInfo) {
					uni.showModal({
						title: '登录提醒',
						content: '您还未登录,请确认是否登录',
						success: (res) => {
							if (res.confirm) {
								uni.navigateTo({
									url: '../login/login'
								})
							}
						}
					})
				} else {
					//如果有连接,就直接查看,没有需要购买
					if (row.url) {
						uni.navigateTo({
							url: '../courseware/courseware-details?url=' + row.url
						})
					} else {
						uni.showModal({
							title: "购买课件",
							content: "是否确认购买课件?",
							success: (res) => {
								if (res.confirm) {
									this.$http("/cw-order/create", 'post', {
										cwId: row.id,
										price: row.price
									}).then((res) => {
										if (res.data.data.message) {
											uni.showToast({
												title: "已经购买请勿重复购买",
												icon: "none"
											})
										} else {
											uni.navigateToMiniProgram({
												appId: 'wxd9634afb01b983c0',
												path: '/pages/pay/pay',
												extraData: res.data.data,
												success(res) {
													// 打开成功
												}
											})
										}
									})
								}
							}
						})
					}
				}
			},
			//点击轮播图弹出购买
			swiperChange(index) {
				this.switchToDtails(this.swiperList[index])
			},
			//loadCourseware 
			loadCW() {
				if (!this.finish) {
					this.$http("/cw-courseware/list", 'get', {
						start: this.start
					}).then((res) => {
						if (this.start >= res.data.data.list.length) {
							this.finish = true
						}
						this.coursewares.push(...res.data.data.list)
						this.start++
					})
				}
			},
			//获取微信的信息来初始化用户信息
			initUserInfo(info) {
				this.show = false
				const uf = info.detail.userInfo
				this.userInfo.gender = uf.gender == 0 ? "女" : "男";
				this.userInfo.nickname = uf.nickName;
				this.userInfo.portrait = uf.avatarUrl;
				this.$http("/user/initUserInfo", "post", this.userInfo).then((res) => {
					if (res.data.data == true) {
						this.$store.dispatch('refreshUserInfo')
					}
				})
			},
			//获取用户的信息,refresh代表刷新token,延长使用登录时间
			getInfo() {
				getUserInfo({
					refresh: true
				}).then((res) => {
					if (!res.data.data.portrait) {
						if (!this.userInfo.portrait) {
							this.show = true
						}
					}
					this.userInfo = res.data.data
					this.$store.commit("setUserInfo", res.data.data)
				})
			}
		},
		onLoad(ops) {
			//异步获取本地的token来判断用户是否登录,已经登录就加载“我的信息”。
			//如果一开始没登陆直接就getInfo肯定得不到信息,后台返回未登录。
			//又因为我在http.js里面对返回结果进行了统一处理,请求结果是没登陆,页面会直接定向到登录页面,
			//用户还没了解app就被强行要求登录,体验感不好,所以先判断本地有没有token,有token代表已经登录了
			//这时候发送请求就不会被重定向到登录页面。
			uni.getStorage({
				key: "token",
				success: () => {
					this.getInfo()
				}
			})
			//加载轮播图
			this.$http('/cw-courseware/getCarousel', 'get', null).then((res) => {
				this.swiperList = res.data.data
				this.swiperList.forEach((x) => {
					this.imgList.push(this.getImgPath(x.carouselUrl, false, 1))
				})
			})
			this.loadCW()
		}
	}
</script>

<style lang="scss" scoped>
	.top {
		.navbar {
			top: 60rpx;
			z-index: 2;
			position: absolute;
			color: white;
			width: 100%;
			text-align: center;
			font-size: 35rpx;
		}

		.background {
			width: 100vw;
		}
	}

	.coursewares {
		display: flex;
		flex-wrap: wrap;
		height: 100vh;

		.courseware {
			border-radius: 20rpx;
			background-color: white;
			position: relative;
			width: 47%;
			display: inline-block;
			margin: 0rpx 0 15rpx 15rpx;

			image {
				border-radius: 20rpx;

				width: 100%;
				height: 46.7vw;
				background-color: white;
			}

			.info {
				width: 100%;

				.buy-num {
					color: #dcdcdc;
					font-size: 25rpx;
					margin: 10rpx 0 5rpx 20rpx;

				}

				.name {
					margin-left: 20rpx;
					color: #2C405A;
				}

				.price-addbtn {
					display: flex;
					justify-content: space-between;
					padding: 10rpx 20rpx;

					.price {
						color: #7e7e00;
						font-weight: bold;
						font-size: 30rpx;
					}

					image {
						height: 40rpx;
						width: 44rpx;
					}
				}

			}
		}
	}

	.swiper {
		width: 100%;
		display: flex;
		justify-content: center;

		.swiper-box {
			width: 100%;
			height: 75.7vw;
			overflow: hidden;
			// border-radius: 15upx;
			// box-shadow: 0upx 8upx 25upx rgba(0, 0, 0, 0.2);
			//兼容ios，微信小程序
			position: relative;
			z-index: 1;

			swiper {
				width: 100%;
				height: 75.7vw;

				swiper-item {
					image {
						width: 100%;
						height: 75.7vw;
					}
				}
			}

			.indicator {
				position: absolute;
				bottom: 20upx;
				left: 20upx;
				background-color: rgba(255, 255, 255, 0.4);
				width: 150upx;
				height: 5upx;
				border-radius: 3upx;
				overflow: hidden;
				display: flex;

				.dots {
					width: 0upx;
					background-color: rgba(255, 255, 255, 1);
					transition: all 0.3s ease-out;

					&.on {
						width: (100%/3);
					}
				}
			}
		}
	}

	.center {
		display: flex;
		justify-content: center;
		padding: 50rpx 0;
	}

	.divider {
		display: flex;
		padding: 20rpx;
		align-items: flex-end;

		.front {
			font-size: 35rpx;
			font-weight: bold;
			margin-right: 10rpx;
		}

		.rear {
			font-size: 25rpx;
			color: #cccccc;
		}
	}
</style>
