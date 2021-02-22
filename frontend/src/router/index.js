import Vue from 'vue'
import VueRouter from 'vue-router'

// components
import Index from '../components/Index.vue'
import BoardList from '../components/board/BoardList.vue'
import BoardView from '../components/board/BoardView.vue'
import BoardWrite from '../components/board/BoardWrite.vue'
import NoticeList from '../components/notice/NoticeList.vue'
import NoticeView from '../components/notice/NoticeView.vue'
import NoticeWrite from '../components/notice/NoticeWrite.vue'

Vue.use(VueRouter);

const routes = [
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
];

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
});

export default router
