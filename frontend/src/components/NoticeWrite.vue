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
            v-if="notice.files"
          >
            <div
              class="file-label"
              v-for="(file, idx) in notice.files"
              :key="idx"
            >
              {{ file.name }} ({{ file.size }})
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
              >
                저장
              </b-button>
            </b-col>
          </b-row>
        </b-form>
      </validation-observer>
    </b-container>
    <spinner v-if="isLoading"/>
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
      isContentValid: false,
      notice: {
        title: undefined,
        files: undefined
      }
    }
  },
  mounted () {
    this.$watch(() => {
      if (this.$refs.tiptapEditor.content === undefined) {
        this.isContentValid = true
      } else {
        const contentStr = this.$refs.tiptapEditor.content.replace(/<(\/?)p>/gi, '')
        this.isContentValid = contentStr.length !== 0
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
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    },
    async save () {
      if (this.isContentValid) {
        if (this.$refs.tiptapEditor.content === undefined) {
          this.isContentValid = false
          return
        }
      } else {
        return
      }
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
