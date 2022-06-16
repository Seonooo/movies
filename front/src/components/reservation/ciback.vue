<template>
  <div v-for="(a, i) in 6" :key="i">
    <div v-if="$store.state.ciback == a">
      <img :src="require(`../../assets/ciback${a}.png`)" alt="" />
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
img {
  height: 550px;
  width: 1920px;
  background-position: center;
}
</style>