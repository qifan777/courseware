<template>
	<view>
		<view class="main">
			<scroll-view class="coursewares">
				<view class="courseware" v-for="(courseware,index) in coursewares" @tap="switchToDtails(courseware)" :key="index">
					<image class="icon" src="../../static/icon/PDF.png"></image>
					<view class="info">
						<view class="name">{{courseware.name}}</view>
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				coursewares: []
			}
		},
		methods: {
			switchToDtails(row) {
				if (row.url) {
					uni.navigateTo({
						url: 'courseware-details?url=' + row.url
					})
				}
			}
		},
		onLoad(ops) {
			uni.setNavigationBarTitle({
				title: ops.name
			})
			ops.url.split(";").forEach((value) => {
				let filename = value.split("/");
				this.coursewares.push({
					name: filename[filename.length - 1],
					url: value
				})
			})
		}
	}
</script>

<style lang="scss" scoped>
	page {
		background-color: white;
	}
	.main {
		background-color: white;
		width: 100%;
		height: 100vh;
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
</style>
