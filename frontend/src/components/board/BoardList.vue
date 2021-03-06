<template>
  <div>
    <div class="post">
      <navBar></navBar>
      <b-container v-if="boardItems" class="content-container">
        <h3 class="text-center">하루 공부</h3>
        <b-table
          hover
          :fields="boardFields"
          :items="boardItems"
        >

          <template v-slot:cell()="data">
            <div
              v-if="data.field.key === 'title'"
              class="board-title"
            >
              <router-link
                :to="{ name: 'BoardView', params: { bid: data.item.bid }}"
                class="inner-title"
              >
                {{ data.value }}
              </router-link>
              <div class="inner-cnt-comment">
                [{{ data.item.cntComment }}]
              </div>
            </div>
            <span v-else>
              {{ data.value }}
            </span>
          </template>
        </b-table>

        <!-- 게시판 페이징 -->
        <nav class="board-nav" aria-label="Page navigation">
          <div></div>
          <ul class="pagination">
            <li
                class="page-item"
                :class="{'disabled': pagination.curPage === 1}"
            >
              <span
                class="page-link"
                @click="setBoardList(1)"
                aria-label="First"
              >
                <font-awesome-icon
                    :icon="[ 'fas','angle-double-left']"
                />
              </span>
            </li>
            <li
                class="page-item"
                :class="{'disabled': pagination.prePage === 0}"
            >
              <span
                class="page-link"
                @click="setBoardList(pagination.prePage)"
                aria-label="Previous"
              >
                <font-awesome-icon
                    :icon="[ 'fas','angle-left']"
                />
              </span>
            </li>
            <li
              v-for="(pageNum, i) in pagination.endPage"
              class="page-item"
              :id="'page-item['+i+']'"
              :key="i"
            >
              <span
                  v-if="pageNum >= pagination.startPage"
                  class="page-link"
                  :class="{'active': pageNum === pagination.curPage}"
                  @click="setBoardList(pageNum)"
              >
                {{ pageNum }}
              </span>
            </li>
            <li
                class="page-item"
                :class="{'disabled': pagination.curPage === pagination.pageCnt}"
            >
              <span
                class="page-link"
                @click="setBoardList(pagination.nextPage)"
                aria-label="Next"
              >
                <font-awesome-icon
                    :icon="[ 'fas','angle-right']"
                />
              </span>
            </li>
            <li
                class="page-item"
                :class="{'disabled': pagination.curPage === pagination.pageCnt}"
            >
              <span
                class="page-link"
                v-if="pagination.curRange!==pagination.rangeCnt"
                @click="setBoardList(pagination.pageCnt)"
                aria-label="End"
              >
                <font-awesome-icon
                    :icon="[ 'fas','angle-double-right']"
                />
              </span>
            </li>
          </ul>
          <router-link :to="{ name: 'BoardWrite' }">
            <b-button variant="secondary">글쓰기</b-button>
          </router-link>
        </nav>

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
      boardFields: undefined,
      pagination: {
        curPage: 1,
        curRange: undefined,
        pageSize: undefined,
        startPage: undefined,
        endPage: undefined,
        prePage: undefined,
        nextPage: undefined,
        rangeSize: undefined,
        listCnt: undefined,
        rangeCnt: undefined,
        pageCnt: undefined,
        startIndex: undefined
      }
    }
  },
  async created () {
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
    await this.setBoardList(1)
  },
  methods: {
    async setBoardList (curPage) {
      this.boardItems = []
      this.pagination.curPage = curPage
      try {
        this.isLoading = true
        await this.setBoardPaging()
        const result = await axios.get('/api/board/getBoardList', {
          params: {
            curPage: curPage
          }
        })
        result.data.forEach(r => this.boardItems.push({
          index: this.pagination.listCnt - this.pagination.startIndex - r.rowNum + 1,
          bid: r.bid,
          writer: r.writer,
          title: r.title,
          cntComment: r.cntComment,
          views: r.views,
          thumbUp: r.thumbUp,
          date: (r.modDate == null ? r.regDate : r.modDate)
        }))
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    },
    async setBoardPaging () {
      try {
        this.isLoading = true
        const result = await axios.get('/api/board/getBoardPaging', {
          params: {
            curPage: this.pagination.curPage
          }
        })
        this.pagination = result.data
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

.board-title {
  display: flex;
}

.inner-title {
  text-decoration: none;
  color: black;
}

.inner-title:hover {
  text-decoration: underline;
}

.inner-cnt-comment {
  padding-left: 7px;
  color: red;
  FONT-WEIGHT: 600;
}

.board-nav {
  display: flex;
  justify-content: space-between;
}

.active {
  background-color: #007bff;
  color: white;
}
</style>
