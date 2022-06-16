<template>
  <div class="imgcontainer2">
    <div class="imgitem2">
      <h4>영화</h4>
    </div>
    <div class="imgitem2">
      <h4>극장</h4>
    </div>
    <div class="imgitem2">
      <h4>예매</h4>
    </div>
    <div class="imgitem2">
      <h4>이벤트</h4>
    </div>
    <div class="imgitem2">
      <h4>게시판</h4>
    </div>
    <div class="imgitem2" style="width: 500px"><input type="text" /></div>
    <div class="imgitem2" style="width: 50px; border-right: 1px solid #6666">
      <img src="../../assets/guidecon/wat2.png" alt="" class="img" />
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    reservationmove() {
      this.$router.push("/reservation");
    },
  },
};
</script>

<style scoped="scoped">
.imgcontainer2 {
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 10;
  border-top: 1px solid #eeeeee;
  border-bottom: 1px solid #eeee;
  height: 50px;
  position: relative;
  background: white;
}
.imgitem2 {
  flex: 0 auto;
  width: 80px;
}
h4 {
  display: inline;
}
input {
  border: none;
  margin-left: 200px;
  border-left: 1px solid #6666;
  display: block;
  margin-left: auto;
  height: 25px;
}
.img {
  width: 25px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
/* .guidecontainer {
        background: white;
        display: flex;
        justify-content: center;
        height: 50px;
        border-top: 1px solid #eeeeee;
        border-bottom: 1px solid #fb4357;
        position: relative;
        width: 100%;
        z-index: 3;
    }

    .guideitem {
        flex: 0 auto;
        background: white;
        width: 991px;
        padding: 10px;
        box-sizing: border-box;
    } */
</style>