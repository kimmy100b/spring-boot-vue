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
            <b-avatar src="https://doozi316.github.io/assets/images/me.png" size="4rem"></b-avatar>
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
            v-html="boardView.content">
          </div>
          <div class="board-icon">
            <!--            TODO : 회원 기능 후-->
            <font-awesome-icon
              :icon="[ thumbIcon,'thumbs-up']"
              class="icon-thumb"
              @click="clickThumb"
            />
            <span>{{ boardView.thumbUp }}</span>
            <font-awesome-icon
              :icon="[ 'far','comment-dots']"
            />
            <span>{{ cntComment }}</span>
          </div>
          <hr>
          <div class="board-comment">
            <h3>댓글</h3>
            <ul class="comment-ul">
              <li
                v-for="commentItem in commentList"
                v-bind:key="commentItem.id"
                class="comment-li"
              >
                <b-avatar src="https://doozi316.github.io/assets/images/me.png" size="3rem"></b-avatar>
                <div class="comment-item">
                  <div class="comment-item-writer">
                    <span>{{ commentItem.writer }}</span>
                  </div>
                  <div
                    class="comment-item-content"
                    v-html="commentItem.content">
                  </div>
                  <div class="comment-item-date">
                    {{ dateFormatter(commentItem.regDate) }}
                    <a>답글쓰기</a>
                    <!--                    TODO : 권한자만 볼 수 있게(회원가입)-->
                    <div class="comment-item-btn">
                      <a>수정</a>
                      <span @click="deleteComment(commentItem.cid)">삭제</span>
                    </div>
                  </div>
                </div>
              </li>
            </ul>
            <b-form class="form-comment">
              <!--                TODO : 작성자 변경-->
              <span
              >신윤정</span>
              <!--                  v-model="comment.writer"-->
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
                  @click="onCommentSubmit"
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
            <b-col class="btn-row text-right">
              <router-link :to="{ name: 'BoardModify', params: { bid: bid } }">
                <b-button variant="info" size="sm">수정</b-button>
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
import * as DateUtil from '../../common/DateUtil'

const BOARD_GUBUN = 1

export default {
  name: 'boardView',
  components: {
    'navBar': NavBar,
    'spinner': Spinner
  },
  data () {
    return {
      isLoading: false,
      boardView: [],
      cntComment: 0,
      commentList: [],
      thumbIcon: 'far',
      comment: {
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
    await this.setCommentList()
    await this.setCntComment()
  },
  methods: {
    dateFormatter (date) {
      if (date) {
        return DateUtil.dateToVisualDateStr(new Date(date))
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
        await axios.get('api/board/increaseBoardViews', {
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
            gubun: BOARD_GUBUN,
            fkId: this.bid
          }
        })
        result.data.forEach(r => this.commentList.push({
          cid: r.cid,
          fkId: r.fkId,
          writer: r.writer,
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
            gubun: BOARD_GUBUN,
            fkId: this.bid
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
            fkId: this.bid
          }})
        await this.setCommentList()
        await this.setCntComment()
      } catch (err) {
        throw Error(err.message)
      } finally {
        this.isLoading = false
      }
    },
    async onCommentSubmit () {
      this.isLoading = true
      try {
        let data = {
          gubun: 1,
          fkId: this.bid,
          level: 0,
          parent_id: 0,
          // writer: this.comment.writer,
          writer: '신윤정',
          content: this.comment.content
        }
        await axios.post('/api/comments/addComment', data)
        await this.setCommentList()
        await this.setCntComment()
      } catch (err) {
        throw Error(err.message)
      } finally {
        this.comment.content = null
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

.board{
  border: 1px solid darkgray;
  margin-bottom: 10px;
  padding: 29px;
  border-radius: 6px;
}

.board-info{
  display: flex;
  align-items: center;
}

.board-info-text,
.comment-item{
  margin-left: 5px;
}
.info-date-view{
  display: flex;
  align-items: center;
  color: gray;
}

.board-date{
  margin-right: 8px;
}

.board-content {
  margin-bottom: 15px;
}

.board-icon{
  margin-top: 45px;
}

/* TODO : 엄지척 넣고 디자인 수정하기 */
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
  50%{
    transform: rotate(0turn);
  }
  75%{
    transform: rotate(0.1turn);
  }
  100% {
    transform: rotate(0turn);
  }
}

.icon-thumb:hover{
  animation: thumbMove 1s ease-in-out infinite;
}

.icon-thumb:active{
  animation: thumbSize 0.5s linear;
}

.comment-ul{
  padding: 0px;
}

.comment-li{
  display: flex;
  padding: 12px 23px 10px 0;
  border-top: 1px solid rgba(0, 0, 0, 0.1);
}

.comment-li:first-child{
  border-top: none;
}

.comment-item-writer{
  font-weight: 600;
}

.comment-item-date{
  font-size: 12px;
  color: gray;
}

.form-comment{
  margin: 12px 0 29px;
  padding: 16px 10px 10px 18px;
  border: 2px solid darkgray;
  border-radius: 6px;
}

.comment-content{
  border: none;
  width: 100%;
  margin: 11px 0px 0px 0px;
  resize: none;
}

.comment-content:focus{
  outline: none;
}

.board-btn{
  padding: 0px 15px;
}

.btn-row {
  padding-right: 0;
  padding-left: 0;
}
</style>
