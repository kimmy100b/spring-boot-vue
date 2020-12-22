<template>
  <div>
    <div class="post">
      <div class="loading" v-if="loading">
        Loading...
      </div>

      <div v-if="error" class="error">
        {{ error }}
      </div>

      <div v-if="boardList" class="content">
        <b-table striped hover :items="boardList" class="bbs-list">
        </b-table>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
export default {
  data () {
    return {
      loading: false,
      boardList: [],
      error: null
    }
  },
  created () {
    this.getBoardList()
  },
  methods: {
    async getBoardList () {
      const result = await axios.post('/api/board/list')
      result.data.forEach(r => this.boardList.push({ writer: r.writer, title: r.title, content: r.content, reg_date: r.reg_date }))
    }
  }
}
</script>

<style scoped>
.bbs-list {
  margin: 50px;
}
</style>
