<template>
	<view>
		<view class="nav" @click="goback">
			<u-icon name="arrow-left" size="45" color="#e7e7e7"></u-icon>
		</view>
		<image class="background" src="../../static/background.jpg"></image>
		<view class="main">
			<view class="center">
				<view class="logo">
					<image mode="widthFix" src="../../static/logo.jpg"></image>
				</view>
				<view class="form">
					<button class="btn" open-type="getPhoneNumber" @getphonenumber="getPhoneNumber">微信一键登录</button>
				</view>
			</view>
		</view>
	</view>
	</view>
</template>

<script>
	import md5 from "@/utils/md5.min.js";
	import {cwUrl} from '@/utils/env.js'
	export default {
		data() {
			return {
			}
		},
		onShow() {
			uni.checkSession({
				success: (data) => {
					this.getSessionId()
				},
				fail: () => {
					this.getSessionId()
				}
			})
		},
		onLoad() {

		},
		methods: {
			goback(){
				uni.navigateBack()
			},
			//获取用户的手机号
			getPhoneNumber(info) {
				let wxAuth = {
					encryptedData: info.detail.encryptedData,
					iv: info.detail.iv,
					sessionId: uni.getStorageSync("sessionId")
				}
				this.$http("/user/authLogin", "post", wxAuth).then((res) => {
					if (res.data.code == 0 && res.data.data.token) {
						uni.setStorage({
							key: 'token',
							data: res.data.data.token,
							success: function() {
								uni.reLaunch({
									url: '../home/home?isLogin=true'
								})
							}
						})
					} else {
						uni.showToast({
							title: res.data.message,
							icon: "none"
						})
					}
				})
			},
			//获取sessionId
			getSessionId() {
				uni.login({
					provider: 'weixin',
					success: (res3) => {
						this.$http("/user/getSessionId", "get", {
							code: res3.code
						}).then(res2 => {
							if (res2.data.data.sessionId) {
								uni.setStorageSync("sessionId", res2.data.data.sessionId)
							}
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	.nav {
		margin-left: 20rpx;
		position: absolute;
		z-index: 1;
		top: 60rpx;
	}

	.main {
		height: 100vh;
		width: 100vw;
		display: flex;
		justify-content: center;

		.center {
			margin-top: 300rpx;
			width: 500rpx;
			height: 500rpx;
			display: flex;
			flex-direction: column;
			justify-content: center;

			.logo {
				display: flex;
				justify-content: center;

				image {
					box-shadow: 0upx 8upx 25upx rgba(0, 0, 0, 0.2);
					width: 230rpx;
					height: 230rpx;
					border-radius: 50%;
					margin-bottom: 50rpx;
				}
			}

			.form {
				display: flex;
				flex-direction: column;
				align-items: center;
				justify-content: center;

				.btn {
					margin: 0;
					color: #000;
					// width: 200rpx;
					width: 80%;
					height: 90upx;
					display: flex;
					justify-content: center;
					align-items: center;
					border-radius: 45upx;
					background-color: #fff;
					font-size: 40upx;
				}
			}
		}
	}



	.background {
		left: -40vw;
		position: absolute;
		width: 100vh;
		z-index: -1;
		height: 100vh;
	}
</style>
