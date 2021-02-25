<template>
  <div class="login">
    <navBar></navBar>
    <b-container class="content-container">
      <h2>로그인</h2>
      <hr>
      <validation-observer ref="observer" v-slot="{ handleSubmit }">
        <b-form
            class="form-login"
            @submit.stop.prevent="handleSubmit(onSubmit)"
        >
          <validation-provider
              name="아이디"
              :rules="{ required: true }"
              v-slot="validationContext"
          >
            <b-form-group>
              <label class="sr-only">아이디</label>
              <b-form-input
                  id="userId"
                  v-model="user.id"
                  :state="getValidationState(validationContext)"
                  name="userId"
                  type="text"
                  class="input-id"
                  placeholder="아이디">
              </b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              name="비밀번호"
              :rules="{ required: true }"
              v-slot="validationContext"
          >
            <b-form-group>
              <label class="sr-only">비밀번호</label>
              <b-form-input
                  type="password"
                  v-model="user.pwd"
                  :state="getValidationState(validationContext)"
                  id="userPwd"
                  class="input-pwd"
                  placeholder="비밀번호"
              >
              </b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <div v-if="modal.message" class="login-fail-msg">
            {{ modal.message }}
          </div>

          <!--          <b-button type="submit" variant="primary" size="sm">등록</b-button>-->
          <b-button
              variant="primary"
              class="btn-block btn-lg"
              type="submit"
          >
            로그인
          </b-button>
          <hr>
        </b-form>
      </validation-observer>

      <div class="find-info">
        <router-link
            :to="{ name: 'BoardList'}"
            class="find-info-link"
        >
          아이디 찾기
        </router-link>
        <!--        <span class="bar" aria-hidden="true">|</span>-->
        <!--        <router-link-->
        <!--          :to="{ name: 'BoardList'}"-->
        <!--          class="find-info-link"-->
        <!--        >-->
        <!--          비밀번호 찾기-->
        <!--        </router-link>-->
        <span class="bar" aria-hidden="true">|</span>
        <router-link
            :to="{ name: 'SignUp' }"
            class="find-info-link"
        >
          회원가입
        </router-link>
      </div>
    </b-container>

    <!-- modal -->
    <template v-if="modal.isShow">
      <div>
        <b-modal
            v-model="modal.isShow"
            @ok="handleOk()"
        >
          {{ modal.message }}
        </b-modal>
      </div>
    </template>

    <!-- 로딩 -->
    <spinner v-if="isLoading"></spinner>
  </div>
</template>

<script>
import NabBar from '../NavBar'
import Spinner from '../Spinner'
export default {
  name: 'BoardWrite',
  components: {
    'navBar': NabBar,
    'spinner': Spinner
  },
  props: {
    bid: {type: [String, Number]}
  },
  data () {
    return {
      isModify: false,
      isLoading: false,
      isUploading: false,
      user: {
        id: undefined,
        pwd: undefined
      },
      modal: {
        isShow: false,
        message: undefined,
        isSuccessLogin: undefined
      }
    }
  },
  methods: {
    getValidationState ({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null
    },
    handleOk () {
      this.modal.isShow = false
      if(this.modal.isSuccessLogin === 1)
        this.$router.push({ name: 'SignUp' })
    },
    async onSubmit () {
      this.isUploading = true
      try {
        await this.$store.dispatch('login', this.user)
        if(this.$store.getters.isLoggedIn){
          await this.$router.replace( '/')
        } else {
          this.modal.isShow = true
          this.modal.message = '가입하지 않은 아이디이거나, 잘못된 비밀번호입니다.'
          return
        }
      } catch (err) {
        this.modal.isShow = true
        this.modal.message = '로그인을 실패했습니다.'
        throw new Error(err)
      } finally {
        this.isUploading = false
      }
    }
  }
}
</script>

<style scoped>
.login {
  height: 100vh;
  background-color: #f5f6f7;
}

.content-container {
  margin-top: 50px;
  padding: 20px;
}

.form-login {
  max-width: 460px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: auto;
  margin-bottom: auto;
}

.input-id, .input-pwd {
  font-size: 16px;
  height: 47px;
}

.find-info {
  text-align: center;
}

.find-info-link {
  color: grey;
}

.find-info span {
  color: darkgray;
}

.login-fail-msg {
  margin: 0px 0px 8px 8px;
  color: red;
}
</style>
