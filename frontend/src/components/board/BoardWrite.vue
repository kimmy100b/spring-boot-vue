<template>
  <div>
    <navBar></navBar>
    <b-container class="content-container">
      <h1>하루공부 글쓰기</h1>
      <hr>
      <validation-observer ref="observer" v-slot="{ handleSubmit }">
        <b-form @submit.stop.prevent="handleSubmit(onSubmit)">
          <!--        TODO : 삭제(회원가입기능)-->
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
              >{{ board.writer }}
              </b-form-input>
              <b-form-invalid-feedback>
                {{ validationContext.errors[0] }}
              </b-form-invalid-feedback>
            </b-form-group>
          </validation-provider>

          <!--        TODO : 라벨제외-->
          <validation-provider
            name="제목"
            :rules="{ required: true, max: 25 }"
            v-slot="validationContext"
          >
            <b-form-group id="board-title" label="제목 : " label-for="input-title">
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

          <b-form-group id="board-content" label="내용 : " label-for="input-content">
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

          <b-form-file multiple>
            <template slot="file-name" slot-scope="{ names }">
              <b-badge variant="dark">{{ names[0] }}</b-badge>
              <b-badge v-if="names.length > 1" variant="dark" class="ml-1">
                + {{ names.length - 1 }} More files
              </b-badge>
            </template>
          </b-form-file>

          <div class="board-btn text-right">
            <b-button
              variant="primary"
              size="sm"
              type="submit"
            >
              등록
            </b-button>
          </div>
        </b-form>
      </validation-observer>
    </b-container>
    <b-button
      variant="primary"
      v-if="isUploading"
      class="board-uploading"
      disabled
    >
      <b-spinner large></b-spinner>
      등록 중입니다.
    </b-button>

    <spinner v-if="isLoading"></spinner>
  </div>
</template>

<script>
import axios from 'axios'
import NabBar from '../NavBar'
import Spinner from '../Spinner'
import TiptapEditor from '../TiptapEditor.vue'

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
        writer: null,
        title: null,
        content: null
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
    async getBoardView () {
      try {
        this.isLoading = true
        const result = await axios.get('/api/board/getBoardInfo', {
          params: {
            bid: this.bid
          }
        })
        this.board = result.data
        this.$refs.tiptapEditor.setContent(this.board.content)
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    },
    async onSubmit () {
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
        let data = {
          writer: this.board.writer, // TODO : 회원가입 후 삭제
          title: this.board.title,
          content: this.$refs.tiptapEditor.content
        }
        if (this.isModify) {
          data = Object.assign(data, { bid: this.bid })
        }
        await axios.post(apiUrl, data)
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

>>> .ProseMirror{
  border-top: 1px solid rgba(0, 0, 0, 0.3);
}

.board-btn{
  margin-top: 20px;
}

.board-uploading{
  position: absolute;
  top: 40%;
  left: 40%;
  opacity: 100;
  font-size: x-large;
}
</style>
