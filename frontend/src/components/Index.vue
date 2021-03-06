<template>
  <div>
    <navBar></navBar>
    <img
        class="main-img"
        src="@/assets/images/main/main.jpg"
    >
  </div>
</template>

<script>
import NabBar from '../components/NavBar.vue'

export default {
  name: 'Index',
  components: {
    'navBar': NabBar
  },
  data () {
    return {

    }
  },
  methods: {

  }
}
</script>

<style scoped>
.main-img {
  width: 100%;
}
</style>
