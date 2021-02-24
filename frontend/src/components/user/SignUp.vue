<template>
  <div class="sign-up">
    <navBar></navBar>
    <b-container class="content-container">
      <h2 class="text-center">회원약관 동의</h2>
      <hr>
      <pre
          class="termContent"
      >{{ termContent }}</pre>
      <input type="checkbox" id="checkbox" v-model="checked">
      <label for="checkbox">회원약관에 동의합니다.</label>

      <h2 class="text-center">기본정보 입력</h2>
      <hr>
      <validation-observer ref="observer" v-slot="{ handleSubmit }">
        <b-form @submit.stop.prevent="handleSubmit(onSubmit)">
          <validation-provider
              name="아이디"
              :rules="{ required: true, alpha_num: true }"
              v-slot="validationContext"
          >
            <b-form-group>
              <label>아이디</label>
              <b-form-input
                  v-model="user.id"
                  :state="getValidationState(validationContext)"
                  size="lg"
                  @blur="chkDuplicatedId()"
              ></b-form-input>
              <div
                  class="id-duplication"
                  v-if="isDuplicatedId !== null
                && getValidationState(validationContext) === true"
              >
                {{ isDuplicatedId === true ? "중복된 아이디입니다." : "사용할 수 있는 아이디입니다." }}
              </div>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              name="비밀번호"
              :rules="{
            required: true,
            min:8,
            max:16,
            alpha_dash: true
            }"
              v-slot="validationContext"
              :vid="user.pwd"
          >
            <b-form-group
                description="8~16자  영문자, 숫자와 특수기호(-),(_)만 사용하세요."
            >
              <label>비밀번호</label>
              <b-form-input
                  id="userPwd"
                  type="password"
                  v-model="user.pwd"
                  :state="getValidationState(validationContext)"
                  size="lg"
              ></b-form-input>
              <span
                  v-if="chkPwdFeedback"
                  class="pwd-feedback"
              >
                {{ chkPwdFeedback }}
              </span>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              name="비밀번호확인"
              :rules="{
            required: true,
            confirmed: user.pwd }"
              v-slot="validationContext"
          >
            <b-form-group>
              <label>비밀번호 재확인</label>
              <b-form-input
                  type="password"
                  v-model="pwdConfirmation"
                  :state="getValidationState(validationContext)"
                  size="lg"></b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              name="이름"
              :rules="{ required: true }"
              v-slot="validationContext"
          >
            <b-form-group>
              <label>이름</label>
              <b-form-input
                  v-model="user.name"
                  :state="getValidationState(validationContext)"
                  size="lg"
              ></b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              name="생년월일"
              :rules="{ required: true }"
              v-slot="validationContext"
          >
            <b-form-group>
              <label>생년월일</label>
              <b-form-input
                  v-model="user.birthDate"
                  type="date"
                  :state="getValidationState(validationContext)"
                  size="lg"
              ></b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              name="성별"
              :rules="{ required: true }"
              v-slot="validationContext"
          >
            <b-form-group>
              <label>성별</label>
              <b-form-select
                  v-model="user.gender"
                  :options="genderOptions"
                  :state="getValidationState(validationContext)"
                  size="lg"
              ></b-form-select>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              name="전화번호"
              :rules="{ required: true, digits: 11 }"
              v-slot="validationContext"
          >
            <b-form-group
                description="'-'를 제외한 전화번호 11자리를 입력하세요."
            >
              <label>전화번호</label>
              <b-form-input
                  v-model="user.tel"
                  :state="getValidationState(validationContext)"
                  size="lg"
              ></b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
              name="이메일"
              :rules="{ email: true }"
              v-slot="validationContext"
          >
            <b-form-group>
              <label>이메일(선택)</label>
              <b-form-input
                  name="userEmail"
                  v-model="user.email"
                  :state="getValidationState(validationContext)"
                  size="lg"></b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <div class="board-btn">
            <b-button
                v-if="!isLoading"
                variant="primary"
                type="submit"
                class="btn-submit"
                size="lg"
            >
              가입하기
            </b-button>
          </div>
        </b-form>
      </validation-observer>
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
import axios from 'axios'
import NabBar from '../NavBar'
import Spinner from '../Spinner'
import TermContent from 'raw-loader!./TermContent.txt'

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
      termContent: TermContent,
      checked: false,
      user: {
        id: undefined,
        pwd: undefined,
        name: undefined,
        birthDate: undefined,
        gender: null,
        tel: undefined,
        email: null,
        roleName: 'USER'
      },
      genderOptions: undefined,
      chkPwdFeedback: undefined,
      pwdConfirmation: undefined,
      modal: {
        isShow: false,
        message: undefined,
        isSuccessSignUp: false
      },
      isDuplicatedId: null
    }
  },
  created() {
    this.genderOptions = [
      { value: null, text: '성별' },
      { value: 'M', text: '남자' },
      { value: 'W', text: '여자' },
      { value: 'U', text: '선택안함' }
    ]
  },
  methods: {
    getValidationState ({ dirty, validated, valid = null }) {

      return dirty || validated ? valid : null
    },
    async chkDuplicatedId () {
      const result = await axios.get('/api/guest/chkDuplicatedId', {
        params: {
          userId: this.user.id
        }
      })
      this.isDuplicatedId = result.data
    },
    handleOk () {
      this.modal.isShow = false
      if(this.modal.isSuccessSignUp)
        this.$router.push({ name: 'Login' })
    },
    async onSubmit () {
      if (this.checked === false) {
        this.modal.isShow = true
        this.modal.message = '회원약관에 동의해주세요. 동의하지 않을 경우 회원가입을 하실 수 없습니다.'
        return false
      }
      if (this.isDuplicatedId === true) {
        this.modal.isShow = true
        this.modal.message = '중복된 아이디입니다. 아이디를 다시 입력해주세요.'
        return false
      }
      this.isUploading = true
      try {
        await axios.post('/api/guest/setUserInfo', this.user)
        this.modal.isShow = true
        this.modal.message = '회원가입이 되었습니다.'
        this.modal.isSuccessSignUp = true
      } catch (err) {
        this.modal.isShow = true
        this.modal.message = '회원등록에 실패했습니다.'
      } finally {
        this.isUploading = false
      }
    }
  }
}
</script>

<style scoped>
.sign-up {
  background-color: #F5F6F7;
}

.content-container {
  margin-top: 50px;
  padding: 20px 106px 20px 106px;
}

.termContent {
  overflow-x: hidden;
  overflow-y: scroll;
  white-space: pre-line;
  padding: 30px;
  height: 250px;
  background-color: white;
  border: 1px solid darkgray;
}

.pwd-feedback,
.id-duplication {
  color: #dc3545;
  font-size: 12.8px;
}

.btn-submit {
  width: 100%;
}
</style>
