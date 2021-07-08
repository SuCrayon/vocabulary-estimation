/*
 * @Author: Crayon
 * @Date: 2021-06-23 11:48:16
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-07-03 10:57:19
 */
import Vue from 'vue'
import Router from 'vue-router'

// 去除重复路由报错
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/',
      redirect: '/estimation'
    },
    {
      path: '/estimation',
      name: 'Estimation',
      component: (resolve) => require([/* webpackChunkName:'estimation'*/'@/views/pc/estimation/index'], resolve)
    },
    {
      path: '/result',
      name: 'Result',
      component: (resolve) => require([/* webpackChunkName:'result'*/'@/views/pc/result/index'], resolve)
    },
    {
      path: '/m_estimation',
      name: 'M_Estimation',
      component: (resolve) => require([/* webpackChunkName:'m_estimation'*/'@/views/mobile/estimation/index'], resolve)
    },
    {
      path: '/m_result',
      name: 'M_Result',
      component: (resolve) => require([/* webpackChunkName:'m_result'*/'@/views/mobile/result/index'], resolve)
    },
    {
      path: '/m_wordBattleGame',
      name: 'M_WordBattleGame',
      component: (resolve) => require([/* webpackChunkName:'m_wordBattleGame'*/'@/views/mobile/word-battle-game/index'], resolve)
    }
  ]
})

export default router
