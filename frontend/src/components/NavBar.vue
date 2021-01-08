<template>
  <div>
    <b-navbar toggleable="lg" type="dark" variant="info">
      <b-navbar-brand href="#">NavBar</b-navbar-brand>
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav
          v-for="menu in menus"
          :key="menu.id"
        >
          <b-nav-item :to="menu.path">
            {{menu.name}}
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>

<script>

export default {
  name: 'NavBar',
  data () {
    return {
      menus: []
    }
  },
  created () {
    this.menus.push(
      {
        id: 'index',
        name: 'Home',
        path: '/'
      },
      {
        id: 'boardList',
        name: 'BoardList',
        path: '/board/list'
      },
      {
        id: 'boardView',
        name: 'BoardView',
        path: '/board/list/:id'
      },
      {
        id: 'test2',
        name: '테스트2',
        path: '/test2'
      },
      {
        id: 'noticeList',
        name: '공지사항',
        path: '/notice-list'
      }
    )
  },
  methods: {
  }
}
</script>

<style scoped>
</style>
