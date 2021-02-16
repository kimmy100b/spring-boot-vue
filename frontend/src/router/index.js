import Vue from 'vue'
import Router from 'vue-router'
import {BootstrapVue, IconsPlugin} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

// fort-awesome
import { library } from '@fortawesome/fontawesome-svg-core'
import { faThumbsUp as fasThumbsUp } from '@fortawesome/free-solid-svg-icons'
import { faThumbsUp as farThumbsUp, faCommentDots } from '@fortawesome/free-regular-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'

// vee-validate
import {
  ValidationObserver,
  ValidationProvider,
  extend,
  localize
} from 'vee-validate'
import ko from 'vee-validate/dist/locale/ko.json'
import * as rules from 'vee-validate/dist/rules'

import Index from '../components/Index.vue'
import BoardList from '../components/board/BoardList.vue'
import BoardView from '../components/board/BoardView.vue'
import BoardWrite from '../components/board/BoardWrite.vue'
import NoticeList from '../components/notice/NoticeList.vue'
import NoticeView from '../components/notice/NoticeView.vue'
import NoticeWrite from '../components/notice/NoticeWrite.vue'

Vue.use(Router)

// BootstrapVue
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

// fort-awesome
library.add(
  fasThumbsUp,
  farThumbsUp,
  faCommentDots
)
Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.config.productionTip = false

// vee-validate
Object.keys(rules).forEach(rule => {
  extend(rule, rules[rule])
})
localize('ko', ko)
Vue.component('ValidationObserver', ValidationObserver)
Vue.component('ValidationProvider', ValidationProvider)

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
      path: '/notice-view/:nid',
      name: 'NoticeView',
      component: NoticeView,
      props: true
    },
    {
      path: '/notice-write',
      name: 'NoticeWrite',
      component: NoticeWrite
    },
    {
      path: '/notice-modify/:nid',
      name: 'NoticeModify',
      component: NoticeWrite,
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
      path: '/board/write',
      name: 'BoardWrite',
      component: BoardWrite,
      props: true
    },
    {
      path: '/board/modify/:bid',
      name: 'BoardModify',
      component: BoardWrite,
      props: true
    }
  ]
})
