<template>
  <div class="twocon">
    <div class="twoitem"><twoitem></twoitem></div>
  </div>
  <div class="ciback">
    <div class="ciitem"><ciback></ciback></div>
  </div>
  <div class="datecon">
    <div class="dateitem"><cidate></cidate></div>
  </div>
  <img
    src="http://img.cgv.co.kr/event/develop/2204_fm5/0359_bg_event.jpg"
    alt=""
  />
</template>

<script>
import twoitem from "../components/reservation/twoitem.vue";
import ciback from "../components/reservation/ciback.vue";
import cidate from "../components/reservation/cidate.vue";

export default {
  created() {
  
     },

  components: {
    twoitem,
    ciback,
    cidate,
  },
};
</script>

<style scoped>
.twocon {
  display: flex;
  /* background: skyblue; */
  text-align: center;
  justify-content: center;
  height: 100px;
  align-items: center;
}
.twoitem {
  float: 0 auto;
  /* background: pink; */
  width: auto;
}
.ciback {
  display: flex;
}
.ciitem {
  flex: 0 auto;
}
</style>