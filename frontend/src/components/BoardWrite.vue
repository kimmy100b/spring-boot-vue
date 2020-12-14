<template>
  <div class="bbs-write">
    <b-form @submit="onSubmit" @reset="onReset" v-if="show">
<!--      <b-form-group id="input-group-4">-->
<!--        <span>공지여부 : </span>-->
<!--        <b-form-checkbox-group v-model="form.checked" id="checkboxes-4">-->
<!--          <b-form-checkbox value="notice">공지</b-form-checkbox>-->
<!--          <b-form-checkbox value="">일반글</b-form-checkbox>-->
<!--        </b-form-checkbox-group>-->
<!--      </b-form-group>-->

      <b-form-group id="bbs-write-name" label="작성자 :" label-for="input-name">
        <b-form-input
          id="bbs-name"
          v-model="form.name"
          required
          placeholder="이름을 입력하세요"
        ></b-form-input>
      </b-form-group>

      <b-form-group id="bbs-write-content" label="내용 : ">
        <b-form-textarea
          id="bbs-content"
          v-model="form.text"
          placeholder="내용을 입력하세요"
          rows="7"
          max-rows="10"
        ></b-form-textarea>
      </b-form-group>

      <b-btn-group class="bbs-write-btn">
        <b-button type="submit" variant="primary">Submit</b-button>
        <b-button type="reset" variant="danger">Reset</b-button>
      </b-btn-group>
    </b-form>
  </div>
</template>

<script>
export default {
  name: 'BoardWrite',
  data () {
    return {
      form: {
        name: '',
        text: ''
        // checked: []
      },
      show: true
    }
  },
  methods: {
    onSubmit (evt) {
      // 제출했을 떄 이벤트
      evt.preventDefault()
      alert(JSON.stringify(this.form))
    },
    onReset (evt) {
      evt.preventDefault()
      // Reset our form values
      this.form.name = ''
      this.form.text = ''
      // this.form.checked = []
      // Trick to reset/clear native browser form validation state
      this.show = false
      this.$nextTick(() => {
        this.show = true
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.bbs-write{
  margin: 50px;
}
</style>
