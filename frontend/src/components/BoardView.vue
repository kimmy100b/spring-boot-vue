<template>
  <div>
      <div class="loading" v-if="loading">
        <div class="d-flex justify-content-center mb-3">
          <b-spinner label="Loading..."></b-spinner>
        </div>
      </div>

      <div v-if="error" class="error">
        {{ error }}
      </div>

      <div v-if="boardView" class="content">
        <navBar></navBar>

        <b-container class="content-container">
          <b-row>
            <b-col class="col-label col-2 border-top">게시글 번호</b-col>
            <b-col class="col border-top">{{boardView.sid}}</b-col>
            <b-col class="col-label col-2 border-top">조회수</b-col>
            <b-col class="col border-top">{{boardView.views}}</b-col>
          </b-row>
          <b-row>
            <b-col class="col-label col-2 border-top">등록일</b-col>

<!--            <b-col class="col border-top">{{ date | formatDate('YY-MM-DD', boardView.reg_date) }}</b-col>-->
            <b-col class="col border-top">{{boardView.regDate}}</b-col>
<!--            <b-col class="col border-top">{{dateFormatter(boardView.regDate)}}</b-col>-->
            <b-col class="col-label col-2 border-top">수정일</b-col>
<!--            <b-col class="col border-top">{{dateFormatter(boardView.modDate)}}</b-col>-->
<!--            <b-col class="col border-top">{{dateFormatter(boardView.modDate)}}</b-col>-->
          </b-row>
          <b-row>
            <b-col class="col-label col-2 border-top">작성자</b-col>
            <b-col class="col border-top">{{boardView.writer}}</b-col>
          </b-row>
          <b-row>
            <b-col class="col-label col-2 border-top">제목</b-col>
            <b-col class="col border-top">{{boardView.title}}</b-col>
          </b-row>
          <b-row>
            <b-col class="col-label col-2 border-top border-bottom">본문</b-col>
            <b-col class="col border-top border-bottom">{{boardView.content}}</b-col>
          </b-row>
          <b-row align-h="between">
            <b-col class="btn-row">
              <b-button variant="secondary" href="#/board/list">목록</b-button>
            </b-col>
            <b-col class="btn-row text-right">
              <b-button variant="info">수정</b-button>
              <b-button variant="danger">삭제</b-button>
            </b-col>
          </b-row>
        </b-container>
      </div>
    </div>
</template>

<script>
import axios from 'axios'
import NavBar from './NavBar'

export default {
  name: 'boardView',
  components: {
    'navBar': NavBar
  },
  data () {
    return {
      loading: false,
      error: null,
      boardView: []
    }
  },
  props: {
    id: { type: Number }
  },
  created () {
    this.getBoardView()
  },
  methods: {
    async getBoardView () {
      const result = await axios.get('/api/board/view', {
        params: {
          id: this.id
        }})
      this.boardView = result.data
    }
  }
}
</script>

<style scoped>
.content-container {
  margin: 50px;
}
</style>
