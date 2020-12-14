import Vue from 'vue'
import Router from 'vue-router'
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Index from '../components/Index'
import Navbar from '../components/Navbar'
import RouterTest from '../components/RouterTest'
import BoardWrite from '../components/BoardWrite'

Vue.use(Router)

// Install BootstrapVue
Vue.use(BootstrapVue)
// Optionally install the BootstrapVue icon components plugin
Vue.use(IconsPlugin)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/navbar',
      name: 'Navbar',
      component: Navbar
    },
    {
      path: '/RouterTest',
      name: 'RouterTest',
      component: RouterTest
    },
    {
      path: '/board/write',
      name: 'BoardWrite',
      component: BoardWrite
    }
  ]
})
