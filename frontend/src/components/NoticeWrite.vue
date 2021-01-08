<template>
  <div>
    <navBar></navBar>
    <b-container class="content-container">
      <b-row>
        <b-col class="notice-title text-center">
          <span class="notice-title-label">공지사항</span>
        </b-col>
      </b-row>
      <b-form>
        <b-form-group>
          <b-form-input
            class="title-input"
            v-model="notice.title"
            placeholder="TITLE"
            required
          >
            {{notice.title}}
          </b-form-input>
        </b-form-group>
        <hr>
        <b-form-group>
          <tiptapEditor
            class="tiptap-editor"
            ref="tiptapEditor"
          />
        </b-form-group>
        <b-form-group>
          <b-form-file
            v-model="notice.files"
            placeholder="파일을 선택해주세요."
            drop-placeholder="파일을 드래그 & 드롭 해주세요"
            multiple
          >
          </b-form-file>
        </b-form-group>
        <b-form-group
          class="border rounded file-list"
          v-if="notice.files"
        >
          <div
            class="file-label"
            v-for="(file, idx) in notice.files"
            :key="idx"
          >
            {{file.name}} ({{file.size}})
          </div>
        </b-form-group>
      </b-form>
      <b-row align-h="between">
        <b-col>
          <router-link :to="{ name: 'NoticeList' }">
            <b-button variant="secondary" size="sm">목록</b-button>
          </router-link>
        </b-col>
        <b-col class="text-right">
          <b-button
            variant="info"
            size="sm"
            @click="save"
          >
            저장
          </b-button>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>

<script>
import NavBar from './NavBar.vue'
import Spinner from './Spinner.vue'
import TiptapEditor from './TiptapEditor.vue'
import axios from 'axios'

export default {
  name: 'NoticeWrite',
  components: {
    'spinner': Spinner,
    'navBar': NavBar,
    'tiptapEditor': TiptapEditor
  },
  props: {
    nid: { type: Number }
  },
  data () {
    return {
      isModify: false,
      isLoading: false,
      notice: {
        title: undefined,
        files: undefined
      }
    }
  },
  mounted () {
    if (this.nid) {
      this.isModify = true
      this.setNotice()
    }
  },
  methods: {
    async setNotice () {
      this.isLoading = true
      try {
        const result = await axios.get('/api/notice/getNoticeView', {
          params: {
            id: this.nid
          }
        })
        this.notice = result.data
        this.$refs.tiptapEditor.setContent(this.notice.content)
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    },
    async save () {
      this.isLoading = true
      try {
        const apiUrl = this.isModify ? '/api/notice/editNotice' : '/api/notice/addNotice'
        let params = {
          title: this.notice.title,
          content: this.$refs.tiptapEditor.content
        }
        if (this.isModify) {
          params = Object.assign(params, {nid: this.nid})
        }
        await axios.post(apiUrl, params)
        await this.$router.push({ name: 'NoticeList' }) // TODO 리스트 말고 View 로 보내는게 좋을듯?
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
  .title-input {
    font-size: 2rem;
    font-weight: bold;
    height: 50px;
    border: none;
    color: #686868;
  }
  .title-input:focus {
    outline: none 0 !important;
    box-shadow: none;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
  }
  .title-input::placeholder {
    font-size: 2rem;
    font-style: italic;
    color: lightgray;
  }
  .notice-title-label {
    font-size: 1.2rem;
  }
  .file-list {
    padding: 5px;
    height: 60px;
    overflow-y: scroll;
  }
  .file-label {
    width: 100%;
  }
  .tiptap-editor {
    height: 400px;
    overflow-y: auto;
    border-radius: 4px;
    box-shadow: rgba(0, 0, 0, 0.04) 0 4px 16px 0;
    transition: box-shadow 0.25s ease-in 0s, transform 0.25s ease-in 0s;
  }
  .tiptap-editor:focus-within {
    /*transform: translateY(-8px);*/
    box-shadow: rgba(0, 0, 0, 0.08) 0 12px 20px 0;
  }
  >>> .custom-file-label:focus-within {
    outline: none 0 !important;
    box-shadow: none;
    -moz-box-shadow: none;
    -webkit-box-shadow: none;
  }
  >>> .custom-file-label {
    border: none;
  }
  >>> .custom-file-label::after {
    border-radius: 0.25rem;
  }
</style>
