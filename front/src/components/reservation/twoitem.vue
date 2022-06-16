<template>
  <div class="cicon">
    <div class="ciitem" @click="$store.commit('cibackm', 1)">
      <h4>cinema1</h4>
    </div>
    <div class="ciitem" @click="$store.commit('cibackm', 2)">
      <h4>cinema2</h4>
    </div>
    <div class="ciitem" @click="$store.commit('cibackm', 3)">
      <h4>cinema3</h4>
    </div>
    <div class="ciitem" @click="$store.commit('cibackm', 4)">
      <h4>cinema4</h4>
    </div>
    <div class="ciitem" @click="$store.commit('cibackm', 5)">
      <h4>cinema5</h4>
    </div>
    <div class="ciitem" @click="$store.commit('cibackm', 6)">
      <h4>cinema6</h4>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.cicon {
  display: flex;
  justify-content: center;
}
.ciitem {
  flex: 0 auto;
  width: 200px;
}
</style>