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
              ></b-form-input>
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
            max:16
            }"
            v-slot="validationContext"
            :vid="user.pwd"
          >
            <b-form-group
              description="8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요."
            >
              <label>비밀번호</label>
              <b-form-input
                id="userPwd"
                type="password"
                v-model="user.pwd"
                :state="getValidationState(validationContext)"
                size="lg"
              ></b-form-input>
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
    <b-button
      variant="primary"
      v-if="isUploading"
      class="board-uploading"
      disabled
    >
      <b-spinner large></b-spinner>
      등록 중입니다.
    </b-button>

    <spinner v-if="isLoading"></spinner>
  </div>
</template>

<script>
import axios from 'axios'
import NabBar from '../NavBar'
import Spinner from '../Spinner'
import txt from './TermContent.txt'

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
      termContent: txt,
      checked: false,
      genderOptions: [
        { value: null, text: '성별' },
        { value: 'M', text: '남자' },
        { value: 'W', text: '여자' },
        { value: 'U', text: '선택안함' }
      ],
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
      pwdConfirmation: undefined
    }
  },
  methods: {
    getValidationState ({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null
    },
    async onSubmit () {
      if (this.checked === false) {
        alert('회원약관에 동의해주세요.\n' +
          '동의하지 않을 경우 회원가입을 하실 수 없습니다.')
        document.getElementById('checkbox').focus()
        return false
      }
      try {
        this.isUploading = true
        await axios.post('/api/user/saveUserInfo', this.user)
        alert('회원가입이 되었습니다.')
        await this.$router.push({ name: 'UserLogin' })
      } catch (err) {
        return alert('회원등록에 실패했습니다.')
      } finally {
        this.isUploading = false
      }
    }
  }
}
</script>

<style scoped>
.sign-up{
  background-color: #F5F6F7;
}

.content-container {
  margin-top: 50px;
  padding: 20px 106px 20px 106px;
}

.termContent{
  overflow-x: hidden;
  overflow-y: scroll;
  white-space: pre-line;
  padding: 30px;
  height: 250px;
  background-color: white;
  border: 1px solid darkgray;
}

.btn-submit{
  width: 100%;
}
</style>
