<template>
  <div>
    <navBar></navBar>
    <b-container
      class="content-container"
      v-if="noticeView"
    >
      <b-row>
        <b-col class="notice-title text-center">
          <span class="notice-title-label">공지사항</span>
        </b-col>
      </b-row>
      <b-row>
        <b-col class="col-label col-2 border-top">등록일</b-col>
        <b-col class="col border-top">{{dateFormatter(noticeView.regDate)}}</b-col>
        <b-col class="col-label col-2 border-top">조회수</b-col>
        <b-col class="col border-top">{{noticeView.views}}</b-col>
      </b-row>
      <b-row>
        <b-col class="col-label col-2 border-top">작성자</b-col>
        <b-col class="col border-top">{{noticeView.writer}}</b-col>
      </b-row>
      <b-row>
        <b-col class="col-label col-2 border-top">제목</b-col>
        <b-col class="col border-top">{{noticeView.title}}</b-col>
      </b-row>
      <b-row>
        <b-col class="col-label col-2 border-top border-bottom">본문</b-col>
        <b-col class="col border-top border-bottom">
          <div v-html="noticeView.content"></div>
        </b-col>
      </b-row>
      <b-row v-if="fileList.length !== 0">
        <b-col class="col-label col-2 border-top border-bottom">첨부파일</b-col>
        <b-col class="col border-top border-bottom">
          <div
            v-for="(file,idx) in fileList"
            :key="idx"
            @click="fileDownload(file.fid, file.fileName)"
            class="notice-file-name"
          >
            {{ file.fileName }}({{ getFileSize(file.fileSize) }})
          </div>
        </b-col>
      </b-row>
      <b-row align-h="between">
        <b-col class="btn-row">
          <router-link :to="{ name: 'NoticeList' }">
            <b-button variant="secondary" size="sm">목록</b-button>
          </router-link>
        </b-col>
        <b-col class="btn-row text-right">
          <b-button
            variant="info"
            size="sm"
            @click="editNotice()"
          >
            수정
          </b-button>
          <b-button
            variant="danger"
            size="sm"
            @click="showModal('delete-check-modal')"
          >
            삭제
          </b-button>
        </b-col>
      </b-row>
      <b-modal
        id="delete-check-modal"
        title="삭제 확인"
        button-size="sm"
        header-text-variant="white"
        header-bg-variant="primary"
        hide-header-close
        @ok="deleteNotice()"
      >
        <div>정말 삭제하시겠습니까?</div>
      </b-modal>
    </b-container>
    <spinner v-if="isLoading" />
  </div>
</template>

<script>
import axios from 'axios'
import * as DateUtil from '../../common/DateUtil.js'
import * as FileUtil from '../../common/FileUtil.js'
import NavBar from '../NavBar.vue'
import Spinner from '../Spinner.vue'

const POST_TYPE = 'notice'

export default {
  name: 'NoticeView',
  components: {
    'spinner': Spinner,
    'navBar': NavBar
  },
  data () {
    return {
      isLoading: false,
      isOwner: false, // TODO 로그인 기능 구현 후, 수정, 삭제 버튼 작성자에게만 보이게 하기
      noticeView: [],
      fileList: []
    }
  },
  props: {
    nid: { type: [String, Number] },
    index: { type: Number }
  },
  created () {
    this.getNoticeView()
    this.getNoticeFiles()
  },
  methods: {
    dateFormatter (date) {
      if (date) {
        return DateUtil.dateToVisualDateStr(new Date(date))
      }
    },
    getFileSize (size) {
      if (size) {
        return FileUtil.getFileSize(size)
      }
    },
    showModal (modalId) {
      this.$bvModal.show(modalId)
    },
    async getNoticeView () {
      this.isLoading = true
      try {
        const result = await axios.get('/api/notice/getNoticeView', {
          params: {
            id: this.nid
          }})
        this.noticeView = result.data
      } catch (err) {
        throw Error(err.message)
      } finally {
        this.isLoading = false
      }
    },
    async getNoticeFiles () {
      const file = await axios.get('/api/files/getFileList', {
        params: {
          type: POST_TYPE,
          id: this.nid
        }})
      this.fileList = file.data
    },
    async fileDownload (fid, fileName) {
      const fileResult = await axios.get('/api/files/download', {
        params: {
          id: fid
        },
        responseType: 'blob'
      })

      const url = window.URL.createObjectURL(fileResult.data)
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', fileName)
      document.body.appendChild(link)
      link.click()
    },
    async deleteNotice () {
      this.isLoading = true
      try {
        await axios.delete('/api/notice/deleteNotice', {
          params: {
            id: this.nid
          }})
        await this.$router.push({ name: 'NoticeList' })
      } catch (err) {
        throw Error(err.message)
      } finally {
        this.isLoading = false
      }
    },
    editNotice () {
      this.$router.push({ name: 'NoticeModify', params: { id: this.nid } })
    }
  }
}
</script>

<style scoped>
  .content-container {
    padding: 30px;
  }
  .notice-title {
    padding: 30px !important;
  }
  .notice-title-label {
    font-size: 1.2rem;
  }
  .col {
    padding: 10px;
  }
  .col-label {
    background-color: #afdbe6;
  }
  .btn-row {
    padding-right: 0;
    padding-left: 0;
  }

  >>> .modal-header {
    padding: 7px;
  }

  >>> .modal-footer {
    padding: 3px;
  }

  >>> .modal-title {
    font-size: medium;
  }

  .notice-file-name{
    color: #1a0dab;
    cursor: pointer;
  }

  .notice-file-name:hover{
    text-decoration: underline;
  }
</style>
