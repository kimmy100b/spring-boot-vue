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
        id: 'test2',
        name: '테스트2',
        path: '/test2'
      }
    )
  },
  methods: {
  }
}
</script>

<style scoped>
</style>
