<template>
  <img src="http://img.cgv.co.kr/R2014/images/common/img_join_hd.jpg" alt="" />
  <button @click="$store.state.joincli = 1"><h3>회원가입</h3></button>
</template>

<script>
export default {
  created() {
  },
};
</script>

<style scoped>
button {
  display: block;
  margin-left: auto;
  margin-right: auto;
  color: white;
  background: #fb4357;
  width: 300px;
  border: 1px solid #f07469;
  margin-top: 70px;
  margin-bottom: 120px;
  cursor: pointer;
}
img {
  margin-top: 100px;
}
</style>