import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      meta: { idx: 1 },
      component: Home
    },
    {
      path: '/homeowner',
      meta: { idx: 2 },
      component: () => import('./views/Homeowner.vue')
    },
    {
      path: '/company',
      meta: { idx: 3 },
      component: () => import('./views/Company.vue')
    },
    {
      path: '/arbitration',
      meta: { idx: 4 },
      component: () => import('./views/Arbitration.vue')
    },
    {
      path: '/login',
      meta: { idx: 5 },
      component: () => import('./views/login/Options.vue')
    },
    {
      path: '/center',
      meta: { idx: 5 },
      component: () => import('./views/center/Personal.vue'),
      children: [
        {
          path: '/center/info',
          meta: { idx: 5, side: 0 },
          component: () => import('./components/side/Infomation.vue')
        },
        // {
        //  path: '/center/order',
        //  meta: { idx: 5, side: 1 },
        //  component: () => import('./components/side/Orders.vue')
        // },
        {
          path: '/center/houses',
          meta: { idx: 5, side: 1 },
          component: () => import('./components/side/Houses.vue')
        },
        {
          path: '/center/publish',
          meta: { idx: 5, side: 2 },
          component: () => import('./components/side/Publish.vue')
        }
        // {
        // path: '/center/transfer',
        // meta: { idx: 5, side: 4 },
        // component: () => import('./components/side/Transfer.vue')
        // },
        // {
        //  path: '/center/apply',
        //  meta: { idx: 5, side: 4 },
        //  component: () => import('./components/side/Apply.vue')
        // }
        // {
        //  path: '/center/share',
        //  meta: { idx: 5, side: 6 },
        //  component: () => import('./components/side/Share.vue')
        // }
      ]
    }
  ]
})
