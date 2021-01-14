<template>
  <div>
    <div class="post">
      <navBar></navBar>
      <b-container v-if="boardItems" class="content-container">
        <h3 class="text-center">하루 공부</h3>
        <b-table hover :fields="boardFields" :items="boardItems">
          <template v-slot:cell()="data">
            <span v-if="data.field.key === 'index'">
              {{ boardItems.length - data.index }}
            </span>
            <router-link
              v-if="data.field.key === 'title'"
             :to="{ name: 'BoardView', params: { bid: data.item.bid }}"
              class="board-title"
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
import NabBar from '../NavBar.vue'
import Spinner from '../Spinner.vue'
import * as DateUtil from '../../common/DateUtil.js'

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
        class: 'text-center',
        thStyle: {
          width: '10%',
          textAlign: 'center'
        }
      },
      {
        key: 'title',
        label: '제목',
        thStyle: {
          width: '40%',
          textAlign: 'center'
        }
      },
      {
        key: 'writer',
        label: '작성자',
        class: 'text-center',
        thStyle: {
          width: '15%',
          textAlign: 'center'
        }
      },
      {
        key: 'date',
        label: '날짜',
        thStyle: {
          width: '17%',
          textAlign: 'center'
        },
        formatter: (value) => DateUtil.dateToVisualDateStr(new Date(value))
      },
      {
        key: 'views',
        label: '조회',
        class: 'text-center',
        thStyle: {
          width: '9%',
          textAlign: 'center'
        }
      },
      {
        key: 'thumbUp',
        label: '엄지척',
        class: 'text-center',
        thStyle: {
          width: '9%',
          textAlign: 'center'
        }
      }
    ]
    this.getBoardList()
  },
  methods: {
    async getBoardList () {
      try {
        this.isLoading = true
        const result = await axios.post('/api/board/getBoardList')
        result.data.forEach(r => this.boardItems.push({
          bid: r.bid,
          writer: r.writer,
          title: r.title,
          views: r.views,
          thumbUp: r.thumbUp,
          date: (r.modDate == null ? r.regDate : r.modDate)
        }))
        // this.boardItems = result.data
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

.board-title{
  text-decoration: none;
  color: black;
}

.board-title:hover{
  text-decoration: underline;
}
</style>
