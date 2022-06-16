<template>
  <div class="con">
    <div class="nav2con">
      <div class="nav2item">
        <h5 @mouseenter="$store.commit('navm', 1)">예매</h5>
      </div>
      <div class="nav2item">
        <h5 @mouseenter="$store.commit('navm', 2)">영화</h5>
      </div>
      <div class="nav2item">
        <h5 @mouseenter="$store.commit('navm', 3)">영화관</h5>
      </div>
      <!-- <div class="nav2item">
      <h5 @mouseenter="$store.commit('navm', 4)">이벤트</h5>
    </div> -->
      <div class="nav2item">
        <h5 @mouseenter="$store.commit('navm', 5)">게시판</h5>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.nav2con {
  display: flex;
  align-items: center;
  height: 50px;
  justify-content: center;
  width: 1000px;
}
.nav2item {
  text-align: center;
  width: 100px;
  color: black;
}
.con {
  display: flex;
  justify-content: center;
}
</style>
