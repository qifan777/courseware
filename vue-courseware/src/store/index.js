import Vue from 'vue'
import Vuex from 'vuex'
import {getUserInfo} from "@/api/api";
Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo:{}
  },
  mutations: {
    setUserInfo(state, userInfo) {
      if (userInfo.token) {
        localStorage.setItem("token", userInfo.token)
      }
      if (!userInfo.background) {
        userInfo.background = ""
      }
      state.userInfo = userInfo
    },
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
  },
})
