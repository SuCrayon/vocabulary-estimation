import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/main',
      name: 'Main',
      component: () => import('@/views/main/index')
    },
    {
      path: '/test',
      name: 'Test',
      component: () => import('@/views/test')
    }
  ]
})
