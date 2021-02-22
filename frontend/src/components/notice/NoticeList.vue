<template>
  <div>
    <navBar></navBar>
    <b-container class="content-container">
      <b-row>
        <b-col class="notice-title text-center">
          <span class="notice-title-label">공지사항</span>
        </b-col>
      </b-row>
      <b-table
        :fields="noticeFields"
        :items="noticeItems"
        head-variant="light"
      >
        <template v-slot:cell()="data">
          <div v-if="data.field.key === 'index'">
              {{ noticeCnt - (data.index + pagination.startIndex) }}
          </div>
          <router-link v-if="data.field.key === 'title'" :to="{ name: 'NoticeView', params: { nid: data.item.nid }}">
            <div class="table-cell-value">
              {{ data.value }}
            </div>
          </router-link>
          <div v-else>
            {{ data.value }}
          </div>
        </template>
      </b-table>
      <b-row>
        <b-col class="btn-row text-right">
          <router-link :to="{ name: 'NoticeWrite' }">
            <b-button variant="info" size="sm">글쓰기</b-button>
          </router-link>
        </b-col>
      </b-row>
      <nav>
        <ul class="pagination">
          <li
            class="page-item"
            :class="{'disabled': pagination.curPage === 1}"
          >
            <span
              @click="setNoticeListAndPaging(1)"
              class="page-link">
              &laquo;
            </span>
          </li>
          <li
            class="page-item"
            :class="{'disabled': pagination.curPage === 1}"
          >
            <span
              @click="setNoticeListAndPaging(pagination.prePage)"
              class="page-link">
              &lt;
            </span>
          </li>
          <li
            class="page-item"
            v-for="pageNum in pagination.visiblePages"
            :key="pageNum"
          >
            <span
              @click="setNoticeListAndPaging(pageNum)"
              class="page-link"
              :class="{'clicked': pageNum === pagination.curPage}"
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
              @click="setNoticeListAndPaging(pagination.nextPage)"
            >
              &gt;
            </span>
          </li>
          <li
            class="page-item"
            :class="{'disabled': pagination.curPage === pagination.pageCnt}"
          >
            <span
              @click="setNoticeListAndPaging(pagination.pageCnt)"
              class="page-link"
            >
              &raquo;
            </span>
          </li>
        </ul>
      </nav>
    </b-container>
    <spinner v-if="isLoading" />
  </div>
</template>

<script>
import axios from 'axios'
import NavBar from '../NavBar.vue'
import Spinner from '../Spinner.vue'
import * as DateUtil from '../../common/DateUtil.js'

export default {
  name: 'NoticeList',
  components: {
    'spinner': Spinner,
    'navBar': NavBar
  },
  data () {
    return {
      isLoading: false,
      noticeFields: undefined,
      noticeItems: undefined,
      noticeCnt: undefined,
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
        startIndex: undefined,
        visiblePages: undefined,
      }
    }
  },
  created () {
    this.noticeFields = [
      {
        key: 'index',
        label: '번호',
        thStyle: {width: '110px'}
      },
      { key: 'title', label: '제목' },
      {
        key: 'writer',
        label: '작성자',
        thStyle: {width: '110px'}
      },
      {
        key: 'regDate',
        label: '등록일자',
        formatter: (value) => DateUtil.dateToVisualDateStr(new Date(value)),
        thStyle: {width: '200px'}
      }
    ]
  },
  mounted () {
    this.setNoticeListAndPaging()
  },
  methods: {
    getVisiblePages () {
      this.pagination.visiblePages = [];
      for (let i = this.pagination.startPage; i <= this.pagination.endPage; i++) {
        this.pagination.visiblePages.push(i);
      }
    },
    async setNoticeListAndPaging (curPage) {
      this.isLoading = true
      try {
        this.pagination.curPage = curPage || this.pagination.curPage
        const result = await axios.get('/api/notice/getNoticeList', {
          params: {
            curPage: this.pagination.curPage
          }
        })
        this.noticeCnt = result.data.noticeCnt
        this.noticeItems = result.data.noticeList
        this.pagination = result.data.pagination
        this.getVisiblePages()
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
    padding: 30px;
  }
  .notice-title {
    padding: 30px;
  }
  .notice-title-label {
    font-size: 1.2rem;
  }
  .table-cell-value {
    text-decoration: none;
    color: black;
  }
  .table-cell-value:hover {
    color: #17a2b8;
  }
  .disabled {
    pointer-events: none;
    opacity: 0.6;
  }
  .clicked {
    color: white;
    background-color: #17a2b8;
  }
  nav {
    text-align: center;
  }
  nav ul {
    display: inline-block;
  }
  nav li {
    float: left;
  }
</style>
