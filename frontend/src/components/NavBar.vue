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
          <b-nav-item :to="{ name: menu.name }">
            {{menu.title}}
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
        title: 'Home',
        name: 'Index'
      },
      {
        title: '하루공부',
        name: 'BoardList'
      },
      {
        title: '공지사항',
        name: 'NoticeList'
      }
    )
  },
  methods: {
  }
}
</script>

<style scoped>
</style>
