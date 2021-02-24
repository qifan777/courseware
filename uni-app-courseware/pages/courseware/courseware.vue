<template>
	<view>
		<view class="main">
			<view class="title">已购课件</view>
			<scroll-view class="coursewares">
				<view v-for="(courseware,index) in coursewares" :key="index">
					<view class="courseware" @tap="goDeep(courseware)">
						<image class="icon" src="../../static/icon/courseware.png"></image>
						<view class="info">
							<view class="name">{{courseware.courseware.name}}</view>
							<view class="time">{{courseware.createTime}}</view>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
		<view class="footer">
			<view class="exchange">
				<input type="text" placeholder="请输入兑换码" v-model="exchangeCode" />
				<button @tap="exchange">兑换</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				coursewares: [],
				exchangeCode: "",
				start: 1,
				finish: false,
				files: []
			}
		},
		onShow() {
			//每次页面显示都将变量初始化
			this.start = 1
			this.finish = false
			this.coursewares = []
			this.exchangeCode = ""
			//异步获取本地的token来判断用户是否登录,已经登录就加载“我的课件”。
			//如果一开始没登陆直接就loadCourseware,页面会直接定向到登录页面,
			//用户还没了解app就被强行要求登录,体验感不好,所以先判断本地有没有token,发送请求。
			uni.getStorage({
				key: "token",
				success: () => {
					this.loadCourseware()
				}
			})
		},
		methods: {
			exchange() {
				this.$http("/cw-exchange-key/use", 'get', {
					key: this.exchangeCode
				}).then((res) => {
					if (res.data.data == true) {
						uni.showModal({
							title: "兑换结果",
							content: "兑换成功,在我的课件中查看"
						})
					} else {
						uni.showModal({
							title: "兑换结果",
							content: "兑换失败,key已失效或你已经拥有该课件"
						})
					}
				})
			},
			goDeep(row) {
				uni.navigateTo({
					url: 'courseware-deep?url=' + row.courseware.url + "&filename=" +
						row.courseware.name
				})
			},
			loadCourseware() {
				this.$http("/cw-user-courseware/myCourseware", "get").then(res => {
					this.coursewares = res.data.data
					console.log(this.coursewares)
				})
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: white;
	}

	.main {
		.title {
			padding-top: 20rpx;
			padding-left: 20rpx;
			font-size: 35rpx;
			font-weight: bold;
			background-color: white;
		}

		.coursewares {
			height: 70vh;

			.courseware {
				background-color: white;
				margin: 30rpx 20rpx;
				padding-bottom: 10rpx;
				display: flex;
				align-items: center;
				border-bottom: 1px solid #f3f3f3;

				.icon {
					width: 55rpx;
					height: 50rpx;
					margin-right: 30rpx;
				}

				.info {
					display: flex;
					flex-direction: column;

					.time {
						color: #c2c2c2;
					}
				}
			}
		}
	}

	.footer {
		position: absolute;
		width: 100%;
		bottom: 30rpx;
		display: flex;
		justify-content: center;

		.exchange {
			display: flex;
			align-items: center;

			input {
				border: 1px solid #dddddd;
				height: 70rpx;
				padding: 0 20rpx;
				width: 300rpx;
				border-top-left-radius: 15rpx;
				border-bottom-left-radius: 15rpx;
			}

			button {
				height: 76rpx;
				width: 150rpx;
				line-height: 76rpx;
				background-color: #3b3b3b;
				color: white;
				margin: 0;
				border-top-left-radius: 0;
				border-bottom-left-radius: 0;
				font-size: 35rpx;
			}
		}
	}
</style>
