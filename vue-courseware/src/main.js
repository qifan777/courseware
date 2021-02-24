import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import http from "../http";
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import md5 from 'js-md5';
import OSS from 'ali-oss'
import {
    Loading,
    MessageBox,
    Message,
    Notification
} from 'element-ui'

const OSSParams = {
    region: 'oss 桶的区域',
    accessKeyId: 'oss key',
    accessKeySecret: 'oss secret',
    bucket: '桶名'
}
let client = new OSS(OSSParams);
Vue.prototype.$client = client
Vue.prototype.$loading = Loading.service;
Vue.prototype.$msgbox = MessageBox;
Vue.prototype.$alert = MessageBox.alert;
Vue.prototype.$confirm = MessageBox.confirm;
Vue.prototype.$prompt = MessageBox.prompt;
Vue.prototype.$notify = Notification;
Vue.prototype.$message = Message;
Vue.prototype.$md5 = md5
Vue.prototype.$axios = axios
Vue.prototype.$http = http
Vue.config.productionTip = false
Vue.use(ElementUI)

new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
