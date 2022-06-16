<template>
  <div style="position: relative">
    <div class="con">
      <img src="../../assets/home/homead.jpg" />
      <img src="../../assets/home/homead1.png" />
      <img src="../../assets/home/homead2.jpg" />
      <img src="../../assets/home/homead3.jpg" />
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.con {
  position: absolute;
  background: black;
  height: 1150px;
  width: 200px;
  display: flex;
  flex-direction: column;
  z-index: 2;
  top: 150px;
}
.con img {
  width: 200px;
  height: 200px;
  margin-top: 100px;
}
</style>