// !! npm i vuex --save : vuex 설치
// !! npm i es6-promise --save : promise를 IE브라우저에서 사용하기 위해서는 polyfill라이브러리 필요
// !! npm i --save vuex-persistedstate : vuex의 새로고침 문제 해결
import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        // Vue 컨포넌트에서 data로 볼 수 있다. 원본 소스 역할, View와 직접적으로 연결되어있는 Model
        // 직접적인 변경은 불가능하고 mutation을 통해서만 변경이 가능하다.
        // mutation을 통해 state가 변경이 일어나면 반응적으로 view가 업데이트 된다.
        loginSuccess: false,
        loginError: false,
        logoutError: false,
        user: {
            id: undefined,
            name: undefined,
            img: undefined,
            role: undefined
        }
    },
    plugins: [createPersistedState ()],
    mutations: {
        // state를 변경하는 유일한 방법이고 이벤트와 유사
        // 함수로 구현되며 첫번째 인자는 state, 두번째 인자는 payload를 받을 수 있다.
        // payload는 여러 필드를 포함할 수 있는 객체형태로 가능
        loginSuccess(state, user) {
            state.loginSuccess = true
            state.user = user
        },
        loginError(state, userId) {
            state.loginError = true
            state.user.id = userId
        },
        logoutSuccess(state) {
            state.loginSuccess = false
            state.user.id = ''
        },
        logoutError(state) {
            state.logoutError = true
        }
    },
    actions: {
        async login({commit}, user) {
            try {
                const result = await axios.post('/api/guest/login', user);
                
                if (result.data !== null && result.data !== '') {
                    commit('loginSuccess', {
                        id: result.data.id,
                        name: result.data.name,
                        img: result.data.imgUrl,
                        role: result.data.roleName
                    });
                }
            } catch (err) {
                commit('loginError', {
                    userName: user.id
                });
                throw new Error(err)
            }
        },

        async logout({commit}) {
            try {
                // const result = await axios.get('/api/guest/logout');
                commit('logoutSuccess')
            } catch (err) {
                commit('logoutError')
                throw new Error(err)
            }
        }
    },
    getters: {
        // Vue 컴포넌트에서 Computed로 볼 수 있따.
        isLoggedIn: state => state.loginSuccess,
        hasLoginErrored: state => state.loginError,
        getUserId: state => state.user.id,
        getUserName: state => state.user.name,
        getUserImage: state => state.user.imgUrl,
        getUserRole: state => state.user.role
    },
    modules: {}
})