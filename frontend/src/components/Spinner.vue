<template functional>
  <div class="spinner">
    <b-container>
      <b-spinner variant="info" label="Spinning" />
    </b-container>
  </div>
</template>

<script>
export default {
  name: 'Spinner',
  functional: true
}
</script>

<style scoped>
.spinner {
  position: absolute;
  left: 0;
  right: 0;
  top: 50%;
  text-align: center;
}
</style>
