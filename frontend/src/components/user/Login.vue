<template>
  <div>
    <navBar></navBar>
    <b-container class="content-container">
      <h2>로그인</h2>
      <hr>
      <b-form class="form-login">
        <b-form-group>
<!--          for="userId"-->
          <label class="sr-only">아이디</label>
          <b-form-input
            id="userId"
            v-model="user.id"
            name="userId"
            type="text"
            class="input-id"
            placeholder="아이디">
          </b-form-input>
        </b-form-group>

        <b-form-group>
          <label class="sr-only">비밀번호</label>
          <b-form-input
            type="password"
            v-model="user.pwd"
            id="userPwd"
            class="input-pwd"
            placeholder="비밀번호"
          >
          </b-form-input>
        </b-form-group>

          <!--          <b-button type="submit" variant="primary" size="sm">등록</b-button>-->
          <b-button
            variant="primary"
            @click="onSubmit"
            class="btn-block btn-lg"
          >
            로그인
          </b-button>
      </b-form>
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
      }
    }
  },
  // computed: {
  //   validation() {
  //     return this.userId.length > 4 && this.userId.length < 13
  //   }
  // },
  methods: {
    async onSubmit () {
      this.isUploading = true
      try {
        // let data = {
        //   id: this.user.id, // TODO : 회원가입 후 삭제
        //   title: this.board.title,
        // }
        await axios.post('/api/user/login', this.user)
        await this.$router.push({ name: 'Index' })
      } catch (err) {
        return alert('로그인을 실패했습니다.')
      } finally {
        this.isUploading = false
      }
    }
  }
}
</script>

<style scoped>
.content-container {
  margin-top: 50px;
  padding: 20px;
}

.form-login{
  max-width: 460px;
}

.input-id, .input-pwd{
  font-size: 16px;
  height: 47px;
}
</style>
