import Vue from 'vue'
import VueRouter from 'vue-router'

/** components **/
import Index from '../components/Index.vue'
// board
import BoardList from '../components/board/BoardList.vue'
import BoardView from '../components/board/BoardView.vue'
import BoardWrite from '../components/board/BoardWrite.vue'
//notice
import NoticeList from '../components/notice/NoticeList.vue'
import NoticeView from '../components/notice/NoticeView.vue'
import NoticeWrite from '../components/notice/NoticeWrite.vue'
// user
import SignUp from '../components/user/SignUp.vue'
import Login from '../components/user/Login.vue'

/** vuex **/
import store from '../store'

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
        component: NoticeWrite,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/notice-modify/:nid',
        name: 'NoticeModify',
        component: NoticeWrite,
        props: true,
        meta: {
            requiresAuth: true
        }
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
        props: true,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/board/modify/:bid',
        name: 'BoardModify',
        component: BoardWrite,
        props: true,
        meta: {
            requiresAuth: true
        }
    },
    {
        path: '/guest/signup',
        name: 'SignUp',
        component: SignUp
    },
    {
        path: '/guest/login',
        name: 'Login',
        component: Login
    },
    // otherwise redirect to home
    {path: '*', redirect: '/'}
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiresAuth)) {
        // 이 라우트는 인증이 필요하며 로그인 한 경우 확인하십시오.
        // 그렇지 않은 경우 로그인 페이지로 리디렉션하십시오.
        if (to.name !== 'Login' && !store.getters.isLoggedIn) {
            next({
                name: 'Login',
                query: { redirect: to.fullPath } // TODO : 이거 뭐지
            })
        } else {
            next()
        }
    } else {
        next() // 반드시 next()를 호출하십시오!
    }
});

export default router
