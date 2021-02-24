<template>
	<view>
		<view>
			<web-view :webview-styles="webviewStyles" :src="url" v-if="url"></web-view>
		</view>
	</view>
</template>

<script>
	import {
		Base64
	} from '@/utils/base64.min.js'
	import {
		getImgPath
	} from '@/utils/common.js'
	export default {
		mixins: [getImgPath],
		data() {
			return {
				url: '',
				webviewStyles: {
					progress: {
						color: '#FF3333'
					}
				}
			}
		},
		onLoad(ops) {
			//将地址编码成后台要求的格式
			this.url = "http://127.0.0.1:8012/onlinePreview?url=" + decodeURIComponent(Base64.encode(this.getImgPath(ops.url,
				false)))
		},
		methods: {

		}
	}
</script>

<style>

</style>
