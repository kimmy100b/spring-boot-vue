<template>
  <div>
    <navBar></navBar>
    <b-container class="content-container">
      <h1>하루공부 글쓰기</h1>
      <hr>
      <validation-observer ref="observer" v-slot="{ handleSubmit }">
        <b-form
            enctype="multipart/form-data"
            @submit.stop.prevent="handleSubmit(onSubmit)"
        >
          <validation-provider
            name="작성자"
            :rules="{ required: true }"
            v-slot="validationContext"
          >
            <b-form-group id="board-writer" label="작성자 : " label-for="input-writer">
              <b-form-input
                id="board-writer"
                v-model="board.writer"
                :state="getValidationState(validationContext)"
                placeholder="이름을 입력해 주세요."
                disabled
              >{{ board.writer }}
              </b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <validation-provider
            name="제목"
            :rules="{ required: true, max: 25 }"
            v-slot="validationContext"
          >
            <b-form-group id="board-title" label="제목 : ">
              <b-form-input
                id="board-title"
                name="title"
                v-model="board.title"
                placeholder="제목을 입력해 주세요."
                :state="getValidationState(validationContext)"
              >{{ board.title }}
              </b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <b-form-group id="board-content" label="내용 : ">
              <span v-if="! isContentValid" class="content-feedback">
                내용 항목은 필수 정보입니다
              </span>
            <tiptapEditor
              :class="! isContentValid ? 'tiptap-editor-validation' : 'tiptap-editor'"
              ref="tiptapEditor"
              v-model="board.content"
              placeholder="내용을 입력하세요."
            />
          </b-form-group>

          <b-form-group>
            <b-form-file
                v-model="board.files"
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
              v-if="board.files || originFiles"
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
                v-for="(file, idx) in board.files"
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

          <div class="board-btn text-right">
            <b-button
              variant="primary"
              size="sm"
              type="submit"
              v-show="!isLoading"
            >
              등록
            </b-button>
          </div>
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

    <spinner v-if="isLoading"></spinner>
  </div>
</template>

<script>
import axios from 'axios'
import NabBar from '../NavBar'
import Spinner from '../Spinner'
import TiptapEditor from '../TiptapEditor.vue'
import * as FileUtil from '../../common/FileUtil.js'
import store from "@/store";

const POST_TYPE = 'board'
const MAX_FILE_SIZE = 10 * 1024 * 1024

export default {
  name: 'BoardWrite',
  components: {
    'navBar': NabBar,
    'spinner': Spinner,
    'tiptapEditor': TiptapEditor
  },
  props: {
    bid: {type: [String, Number]}
  },
  data () {
    return {
      isModify: false,
      isLoading: false,
      isUploading: false,
      isContentValid: false,
      board: {
        writer: undefined,
        title: undefined,
        content: undefined,
        files: undefined // 추가할 첨부파일
      },
      originFiles: [] // 기존에 있는 첨부파일
    }
  },
  created() {
    this.board.writer = store.getters.getUserId
  },
  computed: {
    showModal: {
      get () {
        if (! this.board.files)
          return;
        return this.board.files.some(file => file.size >= MAX_FILE_SIZE)
      },
      set () {
        const idx = this.board.files.findIndex(file => file.size >= 0)
        this.board.files.splice(idx, 1)
      }
    }
  },
  mounted () {
    this.$watch(() => {
      if (this.$refs.tiptapEditor.content === undefined) {
        this.isContentValid = true
      } else {
        const contentStr = this.$refs.tiptapEditor.content
          .replace(/<(\/?)p>/gi, '')
          .replace(/<(\/?)h1>/gi, '')
          .replace(/<(\/?)h2>/gi, '')
          .replace(/<(\/?)h3>/gi, '')
          .replace(/<(\/?)pre>/gi, '')
          .replace(/<(\/?)code>/gi, '')
        this.isContentValid = (contentStr.length !== 0)
      }
    })
    if (this.bid) {
      this.isModify = true
      this.getBoardView()
    }
  },
  methods: {
    getValidationState ({ dirty, validated, valid = null }) {
      return dirty || validated ? valid : null
    },
    async getBoardView () { // 데이터 받기
      this.isLoading = true
      try {
        // 게시물 데이터 받기
        const result = await axios.get('/api/board/getBoardInfo', {
          params: {
            bid: this.bid
          }
        })
        this.board = result.data
        this.$refs.tiptapEditor.setContent(this.board.content)
        this.$refs.tiptapEditor.content = this.board.content

        // 첨부파일 데이터 받기
        const fileResult = await axios.get('/api/files/getFileList', {
          params: {
            type: POST_TYPE,
            id: this.bid
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
    deleteFile (isNew, idx) { // 삭제할 첨부 파일
      if (isNew) {
        this.notice.files.splice(idx, 1)
      } else {
        this.originFiles[idx].isDelete = true
      }
    },
    getFileSize (size) { // 첨부 파일 사이즈
      if (size) {
        return FileUtil.getFileSize(size)
      }
    },
    getParams () { // 전달할 데이터
      let formData = new FormData()
      formData.append('writer', this.board.writer) // TODO : 로그인 기능 후
      formData.append('title', this.board.title)
      formData.append('content', this.$refs.tiptapEditor.content)
      if (this.board.files) {
        for (const file of this.board.files) {
          formData.append('files', file)
        }
      }
      if (this.isModify) {
        formData.append('bid', this.bid)
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
    async onSubmit () { // 데이터 전송(저장)
      // tiptap content 유효성 검사
      // tiptap은 input 형식이 아니라 vee-validate 로 거를 수 없어 따로 추가함
      if (this.isContentValid) {
        if (this.$refs.tiptapEditor.content === undefined) {
          this.isContentValid = false
          return
        }
      } else {
        return
      }
      this.isUploading = true
      try {
        const apiUrl = this.isModify ? '/api/board/modifyBoard' : '/api/board/addBoard'
        await axios.post(apiUrl, this.getParams(),
            { headers: { 'Content-Type': false } })
        await this.$router.push({ name: 'BoardList' })
      } catch (err) {
        return alert('등록에 실패했습니다.')
      } finally {
        this.isUploading = false
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
  font-size: 12.8px;
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

>>> .ProseMirror {
  border-top: 1px solid rgba(0, 0, 0, 0.3);
}

.board-btn {
  margin-top: 20px;
}

.board-uploading {
  position: absolute;
  top: 40%;
  left: 40%;
  opacity: 100;
  font-size: x-large;
}
</style>
