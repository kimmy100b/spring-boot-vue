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
            {{ menu.title }}
          </b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto">
          <div v-if="!this.$store.getters.isLoggedIn">
            <b-nav-item-dropdown right>
              <template #button-content>
                <span>로그인하세요</span>
              </template>
              <b-dropdown-item
                  :to="{ name: 'Login' }"
              >
                로그인
              </b-dropdown-item>
              <b-dropdown-item
                  :to="{ name: 'SignUp'}"
              >
                회원가입
              </b-dropdown-item>
            </b-nav-item-dropdown>
          </div>

          <div v-else>
            <b-nav-item @click="logOut">
              로그아웃
            </b-nav-item>
          </div>

          <b-nav-form>
            <b-form-input size="sm" class="mr-sm-2" placeholder="Search"></b-form-input>
            <b-button size="sm" class="my-2 my-sm-0" type="submit">Search</b-button>
          </b-nav-form>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>
<script>
export default {
  name: 'NavBar',
  data() {
    return {
      menus: [],
      isLogedIn : this.$store.getters.isLoggedIn
    }
  },
  computed: {
  },
  created() {
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
    async logOut() {
      try {
        await this.$store.dispatch('logout')
        await this.$router.push({ name: 'Index' })
      } catch (err) {
        throw new Error('로그아웃 실패')
      }
    }
  }
}
</script>
<style scoped>
</style>