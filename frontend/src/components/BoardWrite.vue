<template>
  <div>
    <navBar></navBar>
    <b-container class="content-container">
      <h1>하루공부 글쓰기</h1>
      <hr>
      <b-form>

<!--        TODO : 삭제(회원가입기능)-->
        <b-form-group id="board-writer" label="작성자 : " label-for="input-writer">
          <b-form-input
            id="board-writer"
            v-model="board.writer"
            required
            placeholder="이름을 입력해 주세요."
          >{{ board.writer }}</b-form-input>
        </b-form-group>

<!--        TODO : 라벨제외-->
        <b-form-group id="board-title" label="제목 : " label-for="input-title">
          <b-form-input
            id="board-title"
            name="title"
            v-model="board.title"
            placeholder="제목을 입력해 주세요."
          >{{ board.title }}</b-form-input>
        </b-form-group>

        <b-form-group id="board-content" label="내용 : " label-for="input-content">
          <tiptapEditor
            class="tiptap-editor"
            ref="tiptapEditor"
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
<!--          <b-button type="submit" variant="primary" size="sm">등록</b-button>-->
          <b-button
            variant="primary"
            size="sm"
            @click="onSubmit"
          >
            등록
          </b-button>
        </div>
      </b-form>
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
import NabBar from './NavBar'
import Spinner from './Spinner'
import TiptapEditor from './TiptapEditor.vue'

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
      form: {
        writer: '',
        title: '',
        content: ''
      },
      board: {
        writer: undefined,
        title: undefined,
        content: undefined
      }
    }
  },
  mounted () {
    if (this.bid) {
      this.isModify = true
      this.getBoardView()
    }
  },
  methods: {
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

>>> .ProseMirror{
  border-top: 1px solid rgba(0, 0, 0, 0.3);
}

.board-uploading{
  position: absolute;
  top: 40%;
  left: 40%;
  opacity: 100;
  font-size: x-large;
}
</style>
