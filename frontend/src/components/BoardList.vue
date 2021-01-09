<template>
  <div>
    <div class="post">
      <navBar></navBar>
      <b-container v-if="boardItems" class="content-container">
        <b-table hover :fields="boardFields" :items="boardItems">
          <template v-slot:cell()="data">
            <span v-if="data.field.key === 'index'">
              {{ boardItems.length - data.index }}
            </span>
            <router-link
              v-if="data.field.key === 'title'"
             :to="{ name: 'BoardView', params: { bid: data.item.bid, index: boardItems.length - data.index }}"
            >
              <div>
                {{ data.value }}
              </div>
              </router-link>
              <span v-else>
              {{ data.value }}
            </span>
          </template>
        </b-table>
        <b-col class="btn-row text-right">
          <router-link :to="{ name: 'BoardWrite' }">
            <b-button variant="secondary" >글쓰기</b-button>
          </router-link>
        </b-col>
    </b-container>
    </div>
    <spinner v-if="isLoading"></spinner>
  </div>
</template>

<script>
import axios from 'axios'
import NabBar from '../components/NavBar.vue'
import Spinner from './Spinner.vue'
import * as DateUtil from '../common/DateUtil.js'

export default {
  name: 'BoardList',
  components: {
    'navBar': NabBar,
    'spinner': Spinner
  },
  data () {
    return {
      isLoading: false,
      error: null,
      boardItems: [],
      boardFields: undefined
    }
  },
  created () {
    this.boardFields = [
      {
        key: 'index',
        label: '번호',
        thStyle: {width: '5%'}
      },
      {
        key: 'writer',
        label: '작성자',
        thStyle: {width: '15%'}
      },
      {
        key: 'title',
        label: '제목',
        thStyle: {width: '20%'}
      },
      {
        key: 'content',
        label: '내용',
        thStyle: {width: '35%'}
      },
      {
        key: 'regDate',
        label: '등록일자',
        thStyle: {width: '20%'},
        formatter: (value) => DateUtil.dateToVisualDateStr(new Date(value))
      }
    ]
    this.getBoardList()
  },
  methods: {
    async getBoardList () {
      try {
        this.isLoading = true
        const result = await axios.post('/api/board/getBoardList')
        this.boardItems = result.data
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    }

  }
}
</script>

<style scoped>
.content-container {
  padding-top: 50px;
}
</style>
