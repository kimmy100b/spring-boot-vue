<template>
  <div>
    <navBar></navBar>
    <b-container class="content-container">
      <b-table
        :fields="noticeFields"
        :items="noticeItems"
        head-variant="light"
      >
        <template v-slot:cell()="data">
          <div v-if="data.field.key === 'index'">
              {{ noticeItems.length - data.index }}
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
    </b-container>
    <spinner v-if="isLoading" />
  </div>
</template>

<script>
import axios from 'axios'
import NavBar from './NavBar.vue'
import Spinner from './Spinner.vue'
import * as DateUtil from '../common/DateUtil.js'

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
      noticeItems: undefined
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
      },
      {
        key: 'modDate',
        label: '수정일자',
        formatter: (value) => DateUtil.dateToVisualDateStr(new Date(value)),
        thStyle: {width: '200px'}
      }
    ]
  },
  mounted () {
    this.getNoticeList()
  },
  methods: {
    async getNoticeList () {
      this.isLoading = true
      try {
        const result = await axios.get('/api/notice/getNoticeList')
        this.noticeItems = result.data
        console.log(this.noticeItems)
      } catch (err) {
        throw err
      } finally {
        this.isLoading = false
      }
    }
  }
}
</script>

<style scoped>
  .content-container {
    padding: 50px;
  }
  .table-cell-value {
    text-decoration: none;
    color: black;
  }
  .table-cell-value:hover {
    color: #17a2b8;
  }
</style>
