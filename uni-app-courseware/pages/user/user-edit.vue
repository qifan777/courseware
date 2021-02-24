<template>
	<view>
		<view class="user-row">
			<userRow>
				<template v-slot:left>
					头像
				</template>
				<template v-slot:right>
					<image :src="getImgPath(tempInfo.portrait)" class="portrait" @click="uploadPortrait" v-if="tempInfo.portrait"></image>
					<image src="../../static/icon/user.png" class="portrait" @click="uploadPortrait"  v-else></image>
				</template>
			</userRow>
		</view>
		<view  class="user-row">
			<userRow>
				<template v-slot:left>
					手机号
				</template>
				<template v-slot:right>
					{{tempInfo.phoneNumber}}
				</template>
			</userRow>
		</view>
		<view @click="showNickname=true" class="user-row">
			<userRow>
				<template v-slot:left>
					昵称
				</template>
				<template v-slot:right>
					{{tempInfo.nickname}}
				</template>
			</userRow>
		</view>
		<view @click="showGender=true" class="user-row">
			<userRow>
				<template v-slot:left>
					性别
				</template>
				<template v-slot:right>
					{{tempInfo.gender}}
				</template>
			</userRow>
		</view>
		<u-modal v-model="showGender" :show-cancel-button="true" confirm-text="确定" title="性别选择" @cancel="closeDilaog"
		 @confirm="saveChange">
			<view class="gender-choose">
				<view :class="{'maleActive':tempInfo.gender=='男'}" @click="changeGender('男')">男</view>
				<view :class="{'femaleActice':tempInfo.gender=='女'}" @click="changeGender('女')">女</view>
				<view :class="{'privacyActive':tempInfo.gender=='保密'}" @click="changeGender('保密')">保密</view>
			</view>
		</u-modal>
		<u-modal v-model="showNickname" :show-cancel-button="true" confirm-text="确定" title="昵称修改" @cancel="closeDilaog"
		 @confirm="saveChange">
			<view class="modify-nickname">
				<input v-model="tempInfo.nickname" maxlength="10" placeholder="请输入新昵称" />
			</view>
		</u-modal>
	</view>
</template>

<script>
	import userRow from '../../components/user/edit-row.vue'
	import {
		getImgPath
	} from '@/utils/common.js'
	import {
		mapState
	} from 'vuex'
	import {
		updateUserInfo,
		upload
	} from '@/utils/api.js'
	export default {
		//知识点组件,slot
		components: {
			userRow
		},
		//提出公用方法,知识点:混入
		mixins: [getImgPath],
		//计算属性,对数据进行统一操作
		computed: {
			//等价 userInfo:()=>{return this.$store.state.userInfo}
			...mapState(['userInfo'])
		},
		data() {
			return {
				showGender: false,
				showNickname: false,
				tempInfo: {}
			};
		},
		methods: {
			onLoad() {
				//之所以要延迟是因为,onLoad开始执行的时候,userInfo还没加载进来。
				setTimeout(() => {
					//深拷贝一份tempInfo
					this.tempInfo = JSON.parse(JSON.stringify(this.userInfo))
				}, 400)
			},
			signOut() {
				uni.removeStorage({
					key: 'token',
					success: () => {
						uni.navigateTo({
							url: '../login/login'
						})
					}
				})
			},
			//点击空白处,放弃更改
			closeDilaog() {
				this.showGender = false
				this.showNickname = false
				//tempInfo复原
				this.tempInfo = JSON.parse(JSON.stringify(this.userInfo))
			},
			changeGender(active) {
				this.tempInfo.gender = active
			},
			uploadPortrait() {
				//选择图片
				uni.chooseImage({
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					count: 1,
					success: (res) => {

						//将选择的图片返回给success,res代表选择的图片
						for (let i = 0; i < res.tempFiles.length; i++) {
							upload(res.tempFilePaths[i]).then((res) => {
								this.tempInfo.portrait = JSON.parse(res[1].data).data.url
								this.saveChange()
							})
						}
					}
				});
			},
			//点击确认,保存
			saveChange() {
				//关闭modal
				this.showGender = false
				this.showNickname = false
				updateUserInfo(this.tempInfo).then((res) => {
					if (res.data.data.id) {
						//将更改后的信息给userInfo
						this.$store.commit('setUserInfo', this.tempInfo)
						uni.showToast({
							title: "更新成功"
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss">
	//modal关键帧
	@keyframes showModal {
		0% {
			transform: scale(2)
		}

		25% {
			transform: scale(1.7)
		}

		50% {
			transform: scale(1.4)
		}

		75% {
			transform: scale(1.2)
		}

		100% {
			transform: scale(1)
		}
	}


	.modal {
		position: absolute;
		display: flex;
		justify-content: center;
		align-items: center;
		top: 0;
		width: 100vw;
		height: 100vh;
		background-color: grey;
		opacity: 0;
		//一定要设置成负数，要不然覆盖在别的元素上,虽然它是透明的。
		z-index: -1;

		// &.代表和modal并列,当
		&.show-modal {
			animation: showModal .1s linear;
			z-index: 10;
			opacity: .7;
		}

		.center {
			display: flex;
			flex-direction: column;
			background-color: white;
			width: 60vw;

			.title {
				text-align: center;
				border-bottom: 2rpx solid #e2e2e2;
				padding: 20rpx 0;
			}

			.footer {
				display: flex;
				justify-content: center;
				padding: 20rpx;
			}
		}
	}

	//modal里面只是中间的不一样,所以把中间部分抽取出来
	.gender-choose {
		display: flex;
		justify-content: space-around;
		border-bottom: 2rpx solid #e2e2e2;
		padding: 30rpx 0;
		align-items: center;

		view {
			width: 80rpx;
			height: 80rpx;
			line-height: 80rpx;
			text-align: center;
			border-radius: 50%;
			transition: background-color .5s;
		}

		.maleActive {
			background-color: #2775b6;
		}

		.femaleActice {
			background-color: #f1939c;
		}

		.privacyActive {
			background-color: #ababab;
		}
	}

	.modify-nickname {
		input {
			padding: 10rpx;
			margin: 20rpx;
			border-radius: 10rpx;
			box-shadow: 0px 1px 2px #d6d6d6;
		}
	}

	.portrait {
		width: 100upx;
		height: 100upx;
		border-radius: 20upx;
	}
</style>
