<template>
  <div class="con">
    <div class="wrap">
      <div class="item" v-for="(a, i) in 5" :key="i">
        <h4>제목</h4>
        <div class="h5div">
          <h5>rank</h5>
          <h5>별점</h5>
          <h5>관람가</h5>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.wrap {
  display: flex;
  width: 1300px;
  justify-content: space-between;
}
.con {
  display: flex;
  justify-content: center;
}
.item {
  width: 208px;
  text-align: center;
}
h5 {
  margin: 0px;
  width: 45px;
}
.h5div {
  display: flex;
  justify-content: space-evenly;
}
</style>