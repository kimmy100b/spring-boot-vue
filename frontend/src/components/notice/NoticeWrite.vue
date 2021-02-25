<template>
  <div>
    <navBar></navBar>
    <b-container class="content-container">
      <h1>공지 사항</h1>
      <hr>
      <validation-observer ref="observer" v-slot="{ handleSubmit }">
        <b-form @submit.stop.prevent="handleSubmit(save)">
          <validation-provider
            :rules="{ required: true }"
            v-slot="validationContext"
          >
            <b-form-group label="제목 : ">
              <b-form-input
                class="title-input"
                v-model="notice.title"
                placeholder="제목을 입력해 주세요."
                :state="getValidationState(validationContext)"
              >
                {{ notice.title }}
              </b-form-input>
              <b-form-invalid-feedback>
                제목을 입력해주세요
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>
          <b-form-group label="내용 : ">
            <tiptapEditor
              :class="! isContentValid ? 'tiptap-editor-validation' : 'tiptap-editor'"
              ref="tiptapEditor"
            />
            <span v-if="! isContentValid" class="content-feedback">
              내용 항목은 필수 정보입니다
            </span>
          </b-form-group>
          <b-form-group>
            <b-form-file
              v-model="notice.files"
              multiple
            >
              <template slot="file-name" slot-scope="{ names }">
                <b-badge variant="dark">{{ names[0] }}</b-badge>
                <b-badge v-if="names.length > 1" variant="dark" class="ml-1">
                  + {{ names.length - 1 }} More files
                </b-badge>
              </template>
            </b-form-file>
          </b-form-group>

          <b-form-group
            class="border rounded file-list"
            v-if="notice.files || originFiles"
          >
            <div
              class="file-label"
              v-for="(file, idx) in originFiles"
              :key="`o-${idx}`"
            >
              <div v-if="!file.isDelete">
                {{ file.name }} ({{ getFileSize(file.size) }})
                <b-icon
                  icon="x"
                  aria-hidden="true"
                  @click="deleteFile(false, idx)"
                ></b-icon>
              </div>
            </div>
            <div
              class="file-label"
              v-for="(file, idx) in notice.files"
              :key="idx"
            >
              {{ file.name }} ({{ getFileSize(file.size) }})
              <b-icon
                icon="x"
                aria-hidden="true"
                @click="deleteFile(true, idx)"
              ></b-icon>
            </div>
          </b-form-group>

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
                type="submit"
                v-show="!isLoading"
              >
                저장
              </b-button>
            </b-col>
          </b-row>
        </b-form>
      </validation-observer>
    </b-container>
    <b-modal
      v-model="showModal"
      title="용량 초과"
      button-size="sm"
      header-text-variant="white"
      header-bg-variant="warning"
      hide-header-close
      ok-only
    >
      첨부파일의 최대 용량을 초과했습니다. 최대 용량은 10MB입니다.
    </b-modal>
    <spinner v-if="isLoading"/>
  </div>
</template>

<script>
import NavBar from '../NavBar.vue'
import Spinner from '../Spinner.vue'
import TiptapEditor from '../TiptapEditor.vue'
import axios from 'axios'
import * as FileUtil from '../../common/FileUtil.js'

const POST_TYPE = 'notice'
const MAX_FILE_SIZE = 10 * 1024 * 1024

export default {
  name: 'NoticeWrite',
  components: {
    'spinner': Spinner,
    'navBar': NavBar,
    'tiptapEditor': TiptapEditor
  },
  props: {
    nid: { type: [String, Number] }
  },
  data () {
    return {
      isModify: false,
      isLoading: false,
      isContentValid: false,
      isDoubleSubmitChk: false,
      notice: {
        title: undefined,
        content: undefined,
        files: undefined
      },
      originFiles: []
    }
  },
  computed: {
    showModal: {
      get () {
        if (! this.notice.files)
          return;
        return this.notice.files.some(file => file.size >= MAX_FILE_SIZE)
      },
      set () {
        const idx = this.notice.files.findIndex(file => file.size >= 0)
        this.notice.files.splice(idx, 1)
      }
    }
  },
  mounted () {
    this.$watch(() => {
      if (this.$refs.tiptapEditor.content === undefined) {
        this.isContentValid = true
      } else {
        // 정규식 설명: 문자열이 아래와 같은 태그로 둘러쌓여 있다면 해당 태그를 공백('')으로 대체한다.
        // <p>~</p>
        // <h1>~</h1>, <h2>~</h2>, <h3>~</h3>
        // <pre>~<pre>
        // <code>~<code>
        const contentStr = this.$refs.tiptapEditor.content.replace(/<(\/?)p>|<(\/?)h1>|<(\/?)h2>|<(\/?)h3>|<(\/?)pre>|<(\/?)code>/gi, '')
        this.isContentValid = (contentStr.length !== 0)
      }
    })
    if (this.nid) {
      this.isModify = true
      this.setNotice()
    }
  },
  methods: {
    getValidationState ({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null
    },
    async setNotice () {
      this.isLoading = true
      try {
        const result = await axios.get('/api/notice/getNoticeInfo', {
          params: {
            id: this.nid
          }
        })
        this.notice = result.data
        this.$refs.tiptapEditor.setContent(this.notice.content)
        this.$refs.tiptapEditor.content = this.notice.content

        const fileResult = await axios.get('/api/files/getFileList', {
          params: {
            type: POST_TYPE,
            id: this.nid
          }
        })
        this.originFiles = fileResult.data.map(result => ({
          fid: result.fid,
          name: result.fileName,
          size: result.fileSize,
          isDelete: false
        }))
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    },
    getParams () {
      let formData = new FormData()
      formData.append('title', this.notice.title)
      formData.append('content', this.$refs.tiptapEditor.content)

      if (this.notice.files) {
        for (const file of this.notice.files) {
          formData.append('files', file)
        }
      }

      if (this.isModify) {
        formData.append('nid', this.nid)

        if (this.originFiles) {
          for (const file of this.originFiles) {
            if (file.isDelete) {
              formData.append('delFids', file.fid)
            }
          }
        }
      }

      return formData
    },
    deleteFile (isNew, idx) {
      if (isNew) {
        this.notice.files.splice(idx, 1)
      } else {
        this.originFiles[idx].isDelete = true
      }
    },
    getFileSize (size) {
      if (size) {
        return FileUtil.getFileSize(size)
      }    },
    async save () {
      // tiptap content 유효성 검사
      // tiptap은 input 형식이 아니라 vee-validate 로 거를 수 없어 따로 추가함
      if (this.isContentValid) {
        if (this.$refs.tiptapEditor.content === undefined) {
          this.isContentValid = false
          return false
        }
      } else {
        return false
      }

      this.isLoading = true
      try {
        const apiUrl = this.isModify ? '/api/notice/editNotice' : '/api/notice/addNotice'
        await axios.post(apiUrl, this.getParams(),
          { headers: { 'Content-Type': false } })
        await this.$router.push({ name: 'NoticeList' })
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
    margin-top: 50px;
    padding: 20px;
  }
  .file-list {
    padding: 5px;
    height: 60px;
    overflow-y: scroll;
  }
  .file-label {
    width: 100%;
    padding: 0 10px;
  }
  .content-feedback{
    color: #dc3545;
    font-size: small;
  }
  .tiptap-editor {
    height: 400px;
    overflow-y: auto;
    border-radius: 4px;
    box-shadow: rgba(0, 0, 0, 0.04) 0 4px 16px 0;
    transition: box-shadow 0.25s ease-in 0s, transform 0.25s ease-in 0s;
    border: 1px solid rgba(0, 0, 0, 0.3);
  }
  .tiptap-editor:focus-within {
    /*transform: translateY(-8px);*/
    box-shadow: rgba(0, 0, 0, 0.08) 0 12px 20px 0;
  }
  .tiptap-editor-validation {
    height: 400px;
    overflow-y: auto;
    border-radius: 4px;
    box-shadow: rgba(0, 0, 0, 0.04) 0 4px 16px 0;
    transition: box-shadow 0.25s ease-in 0s, transform 0.25s ease-in 0s;
    border: 1px solid #dc3545;
  }
  >>> .ProseMirror{
    border-top: 1px solid rgba(0, 0, 0, 0.3);
  }
</style>
