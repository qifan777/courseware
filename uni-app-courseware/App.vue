<script>
	export default {
		onLaunch: function() {
			console.log('App Launch');

			// setTimeout(() => {
			// 	uni.setTabBarBadge({
			// 		index: 1,
			// 		text: '31'
			// 	});
			// 	uni.showTabBarRedDot({
			// 		index: 3
			// 	});
			// }, 1000);
		},
		onShow(options) {
			//跳转回商户小程序参数示例：{"code": 0,"data":{"orderNo":"123456"},"msg": "支付成功"}
			let extraData = options.referrerInfo.extraData;
			if (extraData) {
				if(extraData.code==0){
					uni.showModal({
						title:"支付结果",
						content:"支付成功！请在我的课件中查看"
					})
				}else{
					uni.showToast({
						title:"支付失败",
						icon:'none'
					})
				}
				// //不管成功失败 先把支付结果赋值
				// this.globalData.payStatus = extraData.code == 0 ? true : false;
				// if (extraData.code != 0) {
				// 	wx.showToast({
				// 		title: extraData.msg, //错误提示
				// 		icon: 'none',
				// 		duration: 3000
				// 	});
				// 	return;
				// }
				// //支付成功
				// this.globalData.orderNo = extraData.data.orderNo;
			}
		},
		onHide: function() {
			console.log('App Hide');
		}
	};
</script>

<style lang="scss">
	@import "uview-ui/index.scss";

	/*每个页面公共css */
	page {
		background-color: #f1f1f1;
	}
</style>
