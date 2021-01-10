import Vue from 'vue'
import Router from 'vue-router'
import {BootstrapVue, IconsPlugin} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

import Index from '../components/Index.vue'
import BoardList from '../components/BoardList.vue'
import BoardView from '../components/BoardView.vue'
import Test2 from '../components/Test2.vue'
import NoticeList from '../components/NoticeList.vue'
import NoticeView from '../components/NoticeView.vue'

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
      path: '/notice-list',
      name: 'NoticeList',
      component: NoticeList
    },
    {
      path: '/notice-view/:id',
      name: 'NoticeView',
      component: NoticeView,
      props: true
    },
    {
      path: '/board/list',
      name: 'BoardList',
      component: BoardList
    },
    {
      path: '/board/view/:bid',
      name: 'BoardView',
      component: BoardView,
      props: true
    },
    {
      path: '/test2',
      name: 'test2',
      component: Test2
    }
  ]
})
