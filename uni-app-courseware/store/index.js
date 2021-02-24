import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)
import http from '@/utils/http.js'
import {getUserInfo} from '../utils/api.js'
const store = new Vuex.Store({
	state: {
		userInfo: null,
	},
	mutations: {
		setUserInfo(state, userInfo) {
			if (userInfo.token) {
				uni.setStorage({
					key:'token',
					data:userInfo.token
				})
			}
			if(!userInfo.portrait){

			}
			if (!userInfo.background) {
				userInfo.background = ""
			}
			state.userInfo = userInfo
		}
	},
	actions: {
		async getUserInfo(context) {	
			if (context.state.userInfo == null) {
				const res= await  getUserInfo()
				context.commit("setUserInfo", res.data.data)
			}
		},
		refreshUserInfo(context) {
			getUserInfo({refresh:true}).then((res) => {
				context.commit("setUserInfo", res.data.data)
			})
		}
	}
})
export default store
