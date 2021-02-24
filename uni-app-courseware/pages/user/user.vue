<template>
	<view>
		<view class="user-head">
			<image class="background" src="../../static/img/background.jpg"></image>
			<view class="user-wrapper">
				<view class="user-section">
					<view class="user-info" @click="switchPage('user-edit')">
						<image mode="widthFix" :src="getImgPath(userInfo.portrait)" v-if="userInfo.portrait"></image>
						<image mode="widthFix" src="../../static/logo.jpg" v-else></image>
						<view class="username">
							<text class="name">{{userInfo.nickname?userInfo.nickname:"您还未登录,点击登录"}}</text>
							<u-icon name="arrow-right"></u-icon>
						</view>
					</view>
				</view>
			</view>
		</view>

		<view class="divider">
			<view class="front">我的服务</view>
			<view class="rear">MY SERVICE</view>
		</view>
		<view class="functions">
			<view class="function" @click="switchPage('user-edit')">
				<image class="icon" src="../../static/icon/myInfo.png"></image>
				<button class="name" size="mini" plain="">我的信息</button>
			</view>
			<view class="function" @click="switchTab('../courseware/courseware')">
				<image class="icon" src="../../static/icon/courseware.png"></image>

				<button class="name" size="mini" plain>我的课件</button>
			</view>
			<view class="function" @click="handleZixun">
				<image class="icon" src="../../static/icon/zixun.png"></image>
				<button class="name" size="mini" plain="">最新咨询</button>
			</view>
			<view class="function">
				<image class="icon" src="../../static/icon/share.png"></image>
				<button class="name" open-type="share" size="mini" plain>分享好友</button>
			</view>
			<view class="function" @click="show=true">
				<image class="icon" src="../../static/icon/lianxi.png"></image>
				<button class="name" size="mini" plain>联系客服</button>
			</view>
			<view class="function" @click="signOut" v-if="userInfo">
				<image class="icon" src="../../static/icon/signOut.png"></image>
				<button class="name" size="mini" plain>退出登录</button>
			</view>
		</view>
		<u-modal v-model="show" :show-cancel-button="true" title=" ">
			<view class="kefu-content">
				<image class="kefu" src="../../static/icon/kefu.jpg"></image>
			</view>
		</u-modal>
	</view>
</template>

<script>
	import {
		mapState
	} from 'vuex'
	export default {
		computed: {
			...mapState(['userInfo'])
		},
		data() {
			return {
				show: false
			};
		},
		methods: {
			signOut() {
				this.$store.commit('setUserInfo',{})
				uni.removeStorage({
					key: 'token',
					success: () => {
						uni.navigateTo({
							url: '../login/login'
						})
					}
				})
			},
			switchPage(value) {
				if (value == 'user-edit' && !this.userInfo) {
					value = "../login/login"
				}
				uni.navigateTo({
					url: value
				})
			},
			switchTab(value) {
				uni.switchTab({
					url: value
				})
			},
			handleZixun() {
				uni.showToast({
					title: '待开发中',
					icon: 'loading'
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background-color: #fcfcfc;
	}

	.user-head {
		position: relative;

		.background {
			position: relative;
			max-width: 100%;
			max-height: 500.35upx;
			width: 100%;
		}

		.user-wrapper {
			position: absolute;
			width: 100%;
			display: flex;
			justify-content: center;
			bottom: -75rpx;

			.user-section {
				box-shadow: 0upx 8upx 25upx rgba(0, 0, 0, 0.2);
				position: relative;
				width: 100%;
				height: 150rpx;
				background-color: white;
				float: right;
				margin: 20upx 40upx;
				padding: 20upx;
				border-radius: 10upx;

				.user-info {
					position: absolute;
					top: -60rpx;
					left: 70rpx;
					display: flex;
					align-items: flex-end;

					image {
						width: 140.67rpx;
						height: 140.67rpx;
						border-radius: 50%;
						margin-right: 50rpx;
						box-shadow: 0upx 8upx 25upx rgba(0, 0, 0, 0.2);

					}

					.username {
						margin-bottom: 20rpx;

						.name {
							font-weight: bold;
							font-size: 35rpx;
							margin-right: 20rpx;
						}
					}
				}
			}
		}

	}



	.functions {
		box-shadow: 0upx 8upx 25upx rgba(0, 0, 0, 0.2);
		background-color: white;
		margin: 0 40upx;
		padding: 40upx 20upx;
		border-radius: 10upx;
		display: flex;
		// justify-content: space-around;
		align-items: center;
		flex-wrap: wrap;

		.function {
			display: flex;
			flex-direction: column;
			align-items: center;
			width: 25%;

			.name {
				background-color: white;
				border: 0;
				padding: 10rpx 0;
				color: #9e9e9e;
				font-size: 25rpx;
				// padding: 0;
			}
		}
	}

	.icon {
		width: 55rpx;
		height: 50rpx;
	}

	.kefu-content {
		width: 100%;
		display: flex;
		justify-content: center;

		.kefu {
			width: 400rpx;
			height: 592rpx;
		}
	}

	.divider {
		margin-top: 70upx;
		display: flex;
		padding: 40rpx 0 10rpx 40rpx;
		align-items: flex-end;

		.front {
			font-size: 35rpx;
			font-weight: bold;
			margin-right: 10rpx;
		}

		.rear {
			font-size: 25rpx;
			color: #c3c3c3;
		}
	}
</style>
