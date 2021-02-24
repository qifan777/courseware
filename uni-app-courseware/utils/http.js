import {cwUrl} from './env.js'
const httpRequest = (opts, data) => {
	let httpDefaultOpts = {
		url: cwUrl + opts.url,
		data: data,
		method: opts.method,
		header: opts.method == 'get' ? {
			"Accept": "application/json, text/plain, */*",
			"Content-Type": "application/json; charset=UTF-8"
		} : {
			"Accept": "application/json, text/plain, */*",
			'Content-Type': 'application/json; charset=UTF-8'
		},
		dataType: 'json',
	}
	let promise = new Promise(function(resolve, reject) {
		uni.request(httpDefaultOpts).then(
			(res) => {
				resolve(res[1])
			}
		).catch(
			(response) => {
				reject(response)
			}
		)
	})
	return promise
};
//带Token请求
const httpTokenRequest = (url,method, data) => {
	let token = "Bearer ";
	 token+= uni.getStorageSync('token');
	//此token是登录成功后后台返回保存在storage中的
	let httpDefaultOpts = {
		url: cwUrl + url,
		data: data,
		method: method,
		header:{
			'Authorization': token
		},
		dataType: 'json',
	}
	let promise = new Promise(function(resolve, reject) {
		uni.request(httpDefaultOpts).then(
			(res) => {
				if ((20000 <= res[1].data.code && res[1].data.code <= 30000) || res[1].data.code >= 70003) {
					uni.navigateTo({
						url: '/pages/login/login'
					})
				} else {
					resolve(res[1])
				}
			}
		).catch(
			(response) => {
				reject(response)
			}
		)
	})
	return promise
};

export default httpTokenRequest
