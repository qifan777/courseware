export const share= {
    onShareAppMessage(res) { //发送给朋友
        return {}
    },
    onShareTimeline(res) {//分享到朋友圈
        return {}
    },
}
export const getImgPath = {
	methods: {
		getImgPath(url, resize = true, type = 0) {
			if(url.startsWith("http")){
				return url
			}
			if (resize) {
				//这个地址换成你自己的oss地址
				return 'https://my-community.oss-cn-qingdao.aliyuncs.com' + url.replace("/resource","") + '?x-oss-process=image/resize,w_200';
			} else {
				return 'https://my-community.oss-cn-qingdao.aliyuncs.com' + url.replace("/resource","")
			}
		}
	}
}

