<template>
  <vueper-slides
    :touchable="false"
    :bullets="false"
    :arrows="true"
    arrows-outside
    class="no-shadow"
    ref="first"
    fixed-height="344px"
  >
    <vueper-slide :key="1">
      <template v-slot:content>
        <div class="container1">
          <div class="item1" v-for="(a, i) in 3" :key="i">
            <img
              :src="require(`../../assets/상영관${i + 1}.png`)"
              alt=""
              class="chartimg"
            />
          </div>
        </div>
      </template>
    </vueper-slide>
    <vueper-slide :key="2">
      <template v-slot:content>
        <div class="container1">
          <div class="item1" v-for="(a, i) in 3" :key="i">
            <img
              :src="require(`../../assets/상영관${i + 4}.png`)"
              alt=""
              class="chartimg"
            />
          </div>
        </div>
      </template>
    </vueper-slide>
    <!-- <vueper-slide :key="3" :style="'background-color: #42b983'">
      <template v-slot:content> </template>
    </vueper-slide> -->
  </vueper-slides>
</template>

<script>
import { VueperSlides, VueperSlide } from "vueperslides";
import "vueperslides/dist/vueperslides.css";

export default {
  components: { VueperSlides, VueperSlide },
};
</script>

<style scoped>
.chartimg {
  width: 170px;
  border-radius: 10px;
}
.container1 {
  display: flex;
  justify-content: space-around;
  background-color: rgba(255, 255, 255, 0);
}
</style>