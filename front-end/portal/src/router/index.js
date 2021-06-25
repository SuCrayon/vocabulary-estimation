/*
 * @Author: Crayon
 * @Date: 2021-06-23 11:48:16
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-24 23:43:10
 */
import Vue from 'vue'
import Router from 'vue-router'

// 去除重复路由报错
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      redirect: '/estimation'
    },
    {
      path: '/estimation',
      name: 'Estimation',
      component: () => import(/* webpackChunkName:'estimation'*/'@/views/estimation/index')
    },
    {
      path: '/result',
      name: 'Result',
      component: () => import(/* webpackChunkName:'result'*/'@/views/result/index')
    }
  ]
})
