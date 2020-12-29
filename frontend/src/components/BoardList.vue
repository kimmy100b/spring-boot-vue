<template>
  <div>
    <div class="post">
      <div class="loading" v-if="loading">
        <div class="d-flex justify-content-center mb-3">
          <b-spinner label="Loading..."></b-spinner>
        </div>
      </div>

      <div v-if="error" class="error">
        {{ error }}
      </div>

      <div v-if="boardItems" class="content">
        <navBar></navBar>
        <div class="bbs-list">
        <b-table hover :fields="boardFields" :items="boardItems" >
          <template v-slot:cell()="data">
            <span v-if="data.field.key === 'index'">
              {{ data.index + 1 }}
            </span>
            <span v-if="data.field.key === 'title'">
              <router-link :to="{ name: 'BoardView', params: { id: `${ data.item.sid }` }}">{{ data.value }}</router-link>
            </span>
              <span v-else>
              {{ data.value }}
            </span>
          </template>
        </b-table>

        <b-col class="btn-row text-right">
          <b-button variant="secondary" href="#/board/list">글쓰기</b-button>
        </b-col>
        </div>
<!--      </div class="bbs-list">-->
      </div>
<!--  </div class="content>    -->
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import NabBar from '../components/NavBar.vue'

export default {
  name: 'BoardList',
  components: {
    'navBar': NabBar
  },
  data () {
    return {
      loading: false,
      error: null,
      boardItems: [],
      boardFields: undefined
    }
  },
  created () {
    this.boardFields = [
      { key: 'index', label: '번호', thStyle: {width: '5%'} },
      { key: 'writer', label: '작성자', thStyle: {width: '15%'} },
      { key: 'title', label: '제목', thStyle: {width: '20%'} },
      { key: 'content', label: '내용', thStyle: {width: '35%'} },
      { key: 'reg_date', label: '등록일자', thStyle: {width: '20%'} }
    ]
    this.getBoardList()
  },
  methods: {
    async getBoardList () {
      const result = await axios.post('/api/board/list')
      result.data.forEach(r => this.boardItems.push({ sid: r.sid, writer: r.writer, title: r.title, content: r.content, reg_date: r.reg_date }))
    }
  }
}
</script>

<style scoped>
.bbs-list {
  margin: 5%;
}
</style>
