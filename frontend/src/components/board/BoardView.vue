<template>
  <div>
    <div class="post">
      <navBar></navBar>
      <b-container v-if="boardView" class="content-container">
        <div class="board">
          <div class="board-title">
            <h2>{{ boardView.title }}</h2>
          </div>

          <div class="board-info">
            <b-avatar
                :src="boardView.imgUrl"
                variant="secondary"
                size="4rem"
            ></b-avatar>
            <div class="board-info-text">
              <h5>{{ boardView.writer }}</h5>
              <div class="info-date-view">
                <span class="board-date">
                  {{ boardView.modDate ? dateFormatter(boardView.modDate) : dateFormatter(boardView.regDate) }}
                </span>
                <span class="board-count">
                  조회 {{ boardView.views }}
                </span>
              </div>
            </div>
          </div>
          <!--end board-info-->
          <hr>

          <div
              class="board-content"
              v-html="boardView.content"
          >
          </div>
          <div class="board-icons">
            <font-awesome-icon
                :icon="[ thumbIcon,'thumbs-up']"
                class="icon-thumb board-icon"
                @click="clickThumb"
            />
            <span>{{ boardView.thumbUp }}</span>
            <font-awesome-icon
                :icon="[ 'far','comment-dots']"
                class="icon-comment board-icon"
            />
            <span>{{ cntComment }}</span>
          </div>
          <hr>
          <div
              v-if="fileList.length !== 0"
              class="board-file"
          >
            <span
                v-for="(file,idx) in fileList"
                :key="idx"
                @click="fileDownload(file.fid, file.fileName)"
                class="board-file-info"
            >
              {{ file.fileName }}({{ getFileSize(file.fileSize) }})
            </span>
          </div>

          <div class="board-comment">
            <h3>댓글</h3>
            <ul class="comment-ul">
              <li
                  v-for="(commentItem, idx) in commentList"
                  :key="idx"
                  class="comment-li"
              >
                <b-avatar
                    :src="commentItem.imgUrl"
                    variant="secondary"
                    size="3rem"></b-avatar>
                <div class="comment-item">
                  <div class="comment-item-writer">
                    <span>{{ commentItem.writer }}</span>
                  </div>
                  <div
                      class="comment-area"
                      v-if="!commentItem.showEditor"
                  >
                    <span
                        class="comment-item-content"
                        v-html="commentItem.content">
                    </span>
                    <div class="comment-item-info">
                      {{ dateFormatter(commentItem.regDate) }}
                      <span class="comment-item-info-modify">답글쓰기</span>

                      <div
                          v-if="commentItem.writter === loggedUser.id "
                          class="comment-item-btn"
                      >
                        <span
                            @click="openEditor(idx)"
                            class="comment-item-btn-modify"
                        >
                          수정
                        </span>
                        <span
                            @click="deleteComment(commentItem.cid)"
                            class="comment-item-btn-delete"
                        >
                          삭제
                        </span>
                      </div>
                    </div>
                    <!-- end comment-item-info -->
                  </div>
                  <!-- end comment-area -->
                  <b-form
                      class="form-modify-comment"
                      v-if="commentItem.showEditor"
                  >
                    <textarea
                        placeholder="댓글을 입력하세요"
                        class="comment-modify-content"
                        v-model="modifyComment.content"
                    ></textarea>

                    <div class="text-right">
                      <b-button
                          class="comment-btn-modify"
                          variant="secondary"
                          size="sm"
                          @click="onCommentSubmit(commentItem.cid)"
                      >
                        수정
                      </b-button>
                      <b-button
                          variant="secondary"
                          size="sm"
                          @click="closeEditor(idx)"
                      >
                        취소
                      </b-button>
                    </div>
                  </b-form>
                </div>
                <!--  end comment-item -->
              </li>
            </ul>
            <b-form
                v-if="loggedUser.id !== '' && loggedUser.id !== null"
                class="form-comment"
            >
              <span>{{ comment.writer }}</span>
              <textarea
                  placeholder="댓글을 입력하세요"
                  rows="1"
                  class="comment-content"
                  v-model="comment.content"
              ></textarea>

              <div class="text-right">
                <b-button
                    variant="secondary"
                    size="sm"
                    @click="onCommentSubmit(0)"
                >
                  등록
                </b-button>
              </div>
            </b-form>
          </div>
          <!-- end board-comment-->
        </div>
        <div class="board-btn">
          <b-row align-h="between">
            <b-col class="btn-row">
              <router-link :to="{ name: 'BoardList'}">
                <b-button variant="secondary" size="sm">목록</b-button>
              </router-link>
            </b-col>
            <b-col
                v-if="boardView.writer === loggedUser.id "
                class="btn-row text-right"
            >
              <router-link :to="{ name: 'BoardModify', params: { bid: bid } }">
                <b-button
                    class="board-btn-modify"
                    variant="info"
                    size="sm"
                >
                  수정
                </b-button>
              </router-link>
              <b-button
                  variant="danger"
                  size="sm"
                  @click="showModal('delete-check-modal')"
              >
                삭제
              </b-button>
            </b-col>
          </b-row>
        </div>
        <!-- end board-btn-->
        <b-modal
            id="delete-check-modal"
            title="삭제 확인"
            button-size="sm"
            header-text-variant="white"
            header-bg-variant="primary"
            hide-header-close
            @ok="deleteBoard()"
        >
          <div>삭제하시겠습니까?</div>
        </b-modal>
      </b-container>
    </div>
    <spinner v-if="isLoading"></spinner>
  </div>
</template>
<script>
import axios from 'axios'
import NavBar from '../NavBar'
import Spinner from '../Spinner.vue'
import * as DateUtil from '../../common/DateUtil.js'
import * as FileUtil from '../../common/FileUtil.js'
import store from "@/store";

const POST_TYPE = 'board'

export default {
  name: 'boardView',
  components: {
    'navBar': NavBar,
    'spinner': Spinner
  },
  data () {
    return {
      isLoading: false,
      loggedUser: {
        id: undefined
      },
      boardView: [],
      cntComment: 0,
      isModifyComment: false,
      commentList: [],
      thumbIcon: 'far',
      fileList: [],
      comment: {
        writer: undefined,
        imgUrl: undefined,
        content: undefined
      },
      modifyComment: {
        content: undefined
      }
    }
  },
  props: {
    bid: {type: [String, Number]}
  },
  async created () {
    await this.increaseBoardViews()
    await this.setBoardInfo()
    await this.setBoardFiles()
    await this.setCommentList()
    await this.setCntComment()
    this.loggedUser.id = store.getters.getUserId
    this.comment.writer = this.loggedUser.id // 댓글 작성자 = 현재 로그인
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
    clickThumb () {
      this.thumbIcon = 'fas'
    },
    async increaseBoardViews () {
      try {
        await axios.get('/api/board/increaseBoardViews', {
          params: {
            bid: this.bid
          }
        })
      } catch (err) {
        throw new Error(err)
      }
    },
    async setBoardInfo () {
      try {
        this.isLoading = true
        const result = await axios.get('/api/board/getBoardInfo', {
          params: {
            bid: this.bid
          }
        })
        this.boardView = result.data
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    },
    async setBoardFiles () {
      const file = await axios.get('/api/files/getFileList', {
        params: {
          type: POST_TYPE,
          id: this.bid
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
    async deleteBoard () {
      this.isLoading = true
      try {
        await axios.delete('/api/board/deleteBoard', {
          params: {
            bid: this.bid
          }})
        await this.$router.push({ name: 'BoardList' })
      } catch (err) {
        throw Error(err.message)
      } finally {
        this.isLoading = false
      }
    },
    async setCommentList () {
      try {
        this.isLoading = true
        this.commentList = []
        const result = await axios.get('/api/comments/getCommentList', {
          params: {
            postType: POST_TYPE,
            postId: this.bid
          }
        })
        result.data.forEach(r => this.commentList.push({
          cid: r.cid,
          postId: r.postId,
          writer: r.writer,
          imgUrl: r.imgUrl,
          content: r.content.replace(/(?:\r\n|\r|\n)/g, '<br />'),
          regDate: r.regDate
        }))
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    },
    async setCntComment () {
      try {
        this.isLoading = true
        const result = await axios.get('/api/comments/getCntComment', {
          params: {
            postType: POST_TYPE,
            postId: this.bid
          }
        })
        this.cntComment = result.data
      } catch (err) {
        throw new Error(err)
      } finally {
        this.isLoading = false
      }
    },
    async deleteComment (cid) {
      this.isLoading = true
      try {
        await axios.delete('/api/comments/deleteComment', {
          params: {
            cid: cid,
            postId: this.bid
          }})
        await this.setCommentList()
        await this.setCntComment()
      } catch (err) {
        throw Error(err.message)
      } finally {
        this.isLoading = false
      }
    },
    async onCommentSubmit (cid) {
      if (cid) {
        if (this.modifyComment.content === '') {
          alert('댓글 내용을 입력해주세요.')
          return false
        }
        this.isModifyComment = true
      } else {
        if (this.comment.content === '' ||
          this.comment.content === undefined) {
          alert('댓글 내용을 입력해주세요.')
          return false
        }
      }
      this.isLoading = true
      try {
        const apiUrl = this.isModifyComment ? '/api/comments/modifyComment' : '/api/comments/addComment'
        let data = {
          postType: POST_TYPE,
          postId: this.bid,
          writer: this.comment.writer
        }
        if (this.isModifyComment) {
          data = Object.assign(data, {
            cid: cid,
            content: this.modifyComment.content
          })
        } else {
          data = Object.assign(data, {
            level: 0,
            parent_id: 0,
            content: this.comment.content
          })
        }
        await axios.post(apiUrl, data)
        await this.setCommentList()
        await this.setCntComment()
      } catch (err) {
        throw Error(err.message)
      } finally {
        this.comment.content = null
        this.isLoading = false
        this.isModifyComment = false
      }
    },
    openEditor (idx) {
      this.$set(this.commentList[idx], 'showEditor', true)
      this.modifyComment.content = this.commentList[idx].content
    },
    closeEditor (idx) {
      this.$set(this.commentList[idx], 'showEditor', false)
    }
  }
}
</script>

<style scoped>
.content-container {
  margin-top: 50px;
  padding: 20px;
}

.board {
  border: 1px solid darkgray;
  margin-bottom: 10px;
  padding: 29px;
  border-radius: 6px;
}

.board-info {
  display: flex;
  align-items: center;
}

.board-info-text,
.comment-item {
  margin-left: 5px;
}

.info-date-view {
  display: flex;
  align-items: center;
  color: gray;
}

.board-date {
  margin-right: 8px;
}

.board-content {
  margin-bottom: 15px;
}

.board-icons {
  margin-top: 45px;
}

.board-icon,
.board-btn-modify,
.comment-item-btn-modify {
  margin-right: 5px;
}

.icon-comment {
  margin-left: 10px;
}

@keyframes thumbSize {
  0% {
    transform: scale(1, 1);
  }
  50% {
    transform: scale(2, 2);
  }
  100% {
    transform: scale(1, 1);
  }
}

@keyframes thumbMove {
  0% {
    transform: rotate(0turn);
  }
  25% {
    transform: rotate(-0.1turn);
  }
  50% {
    transform: rotate(0turn);
  }
  75% {
    transform: rotate(0.1turn);
  }
  100% {
    transform: rotate(0turn);
  }
}

.icon-thumb:hover {
  animation: thumbMove 1s ease-in-out infinite;
}

.icon-thumb:active {
  animation: thumbSize 0.5s linear;
}

.board-file {
  margin-bottom: 2%;
}

.board-file-info {
  padding: 5px;
  margin: 0px 5px 0px 5px;
  font-size: small;
  border: solid 1.5px gray;
  border-radius: 6px;
  background-color: #F5F6F7;
}

.board-file-info:hover {
  color: #007bff;
  cursor: pointer;
}

.comment-ul {
  padding: 0px;
}

.comment-li {
  display: flex;
  padding: 12px 23px 10px 0;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
}

.comment-li:first-child {
  border-top: none;
}

.comment-item {
  width: 100%;
}

.comment-item-writer {
  font-weight: 600;
}

.comment-item-content {
  word-break: break-all;
}

.comment-item-info,
.comment-item-info-modify {
  font-size: 12px;
  color: gray;
}

.comment-item-info-modify:hover,
.comment-item-btn-modify:hover,
.comment-item-btn-delete:hover {
  color: #dc3545;
  cursor: pointer;
}

.form-comment,
.form-modify-comment {
  margin: 12px 0 29px;
  padding: 16px 10px 10px 18px;
  border: 2px solid darkgray;
  border-radius: 6px;
}

.comment-content,
.comment-modify-content {
  border: none;
  width: 100%;
  margin: 11px 0px 0px 0px;
  resize: none;
}

.comment-content:focus,
.comment-modify-content:focus {
  outline: none;
}

.board-btn {
  padding: 0px 15px;
}

.btn-row {
  padding-right: 0;
  padding-left: 0;
}
</style>
