import http from './http.js'
import {cwUrl} from '@/utils/env.js'
//user-section
export const login = function(data) {
	return http('/user/login', 'post', data)
}
export const getUserInfo = function(data) {
	return http('/user/userinfo', 'get', data)
}
export const updateUserInfo = function(data) {
	return http('/user/updateUser', 'post', data)
}
//user-section
//upload-section

export const upload = (filePath) => {
	let token = "Bearer ";
	token += uni.getStorageSync('token');
	const then= uni.uploadFile({
		url: cwUrl + "/fh-upload/upload",
		filePath: filePath,
		name:'file',
		header: {
			'Authorization': token
		}
	})
	return then
}
//