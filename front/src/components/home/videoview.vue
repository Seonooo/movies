<template>
  <div class="container">
    <div style="position: relative">
      <div class="gra1"></div>
      <video class="videobox" autoplay muted>
        <source
          src="http://caching.lottecinema.co.kr//Media/MovieFile/MovieMedia/202205/18538_301_3.mp4"
          type="video/mp4"
        />
      </video>
      <div class="gra2"></div>
    </div>
  </div>
  <!-- <div class="mentcontainer">
    <div class="mentitem">
      <h1>안내멘트</h1>
      <h2>소개멘트</h2>
      <button>이동버튼</button>
    </div>
  </div> -->
</template>

<script>
export default {
  methods: {},
};
</script>

<style scoped>
.gra1 {
  width: 350px;
  height: 600px;
  position: absolute;
  z-index: 10;
  background: linear-gradient(to right, rgb(0, 0, 0, 1), rgba(0, 0, 0, 0));
  left: 0px;
  top: 0px;
}
.gra2 {
  width: 350px;
  height: 600px;
  position: absolute;
  z-index: 10;
  background: linear-gradient(to left, rgb(0, 0, 0, 1), rgba(0, 0, 0, 0));
  right: 0px;
  top: 0px;
}

.videobox {
  display: block;
  position: relative;
  width: 1200px;
}
.container {
  z-index: 3;
  display: flex;
  justify-content: center;
  background: black;
  height: 680px;
}
/* // .mentcontainer
//   position: absolute
//   top: 38rem
//   color: white
//   left: 20rem */
</style> 