/*
 * @Author: Crayon
 * @Date: 2021-06-23 11:48:16
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-25 14:16:25
 */
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
// 全局清除默认样式
import '@/styles/reset.css'
import '@/styles/common.css'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css' // element-ui基础样式

Vue.config.productionTip = false

Vue.use(ElementUI);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  components: { App },
  template: '<App/>'
})
