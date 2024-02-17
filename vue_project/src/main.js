import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
// 导入编写的样式 or import '@/assets/global.css'
import './assets/global.css'
import axios from 'axios' // 导入axios
import './plugins/charts.js' //导入echarts

axios.defaults.baseURL = 'http://192.168.106.130:8888' // 定义根路径
// axios.defaults.baseURL = 'http://localhost:8888' // 定义根路径
Vue.prototype.$http = axios // 在原型链上给$http赋值为axios对象

Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
