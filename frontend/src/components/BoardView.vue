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

          <div class="board-content">
            {{ boardView.content }}
          </div>

          <hr>

          <div class="board-comment">
            <h3>댓글</h3>
<!--            댓글내용 들어가기<hr>-->
            <div class="comment-writer">
              <div>신윤정</div>
              <textarea
                placeholder="댓글을 입력하세요"
                rows="1"
                class="comment-content"
              ></textarea>
              <div class="text-right">
                <b-button variant="secondary" size="sm">등록</b-button>
              </div>
            </div>
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
              <b-button variant="info" size="sm">수정</b-button>
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
import NavBar from './NavBar'
import Spinner from './Spinner.vue'
import * as DateUtil from '../common/DateUtil'

export default {
  name: 'boardView',
  components: {
    'navBar': NavBar,
    'spinner': Spinner
  },
  data () {
    return {
      isLoading: false,
      boardView: []
    }
  },
  props: {
    bid: {type: Number}
  },
  created () {
    this.getBoardView()
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
    async getBoardView () {
      try {
        this.isLoading = true
        const result = await axios.get('/api/board/getBoardView', {
          params: {
            id: this.bid
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
        await axios.get('/api/board/deleteBoard', {
          params: {
            id: this.bid
          }})
        await this.$router.push('/board/list')
      } catch (err) {
        throw Error(err.message)
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

.board-info-text{
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

.board-content{
  margin-bottom: 15px;
}

.comment-writer{
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
