<template>
  <!-- <div class="backwrap" v-show="state.wide === 1" :style="state.aaobj">
    <img :src="state.bb" />
    <button @click="(state.wide = 0), scrollunlock()" class="xx">X</button>
  </div> -->

  <!-- <div class="backcon">
    <div class="backwrap">
      <div class="con2">
        <img
          :src="tmp.pimage"
          :style="state.slideobj2"
          class="poster2"
          v-for="tmp in state.imageurl"
          :key="tmp"
          @click="
            state.wide = 1;
            scrolllock();
          "
        />
      </div>
    </div>
  </div> -->

  <div class="con">
    <div class="wrap">
      <vueper-slides
        class="no-shadow slideswidth"
        :visible-slides="4"
        :slide-ratio="1 / 4"
        :dragging-distance="500"
        :bullets="false"
        :gap="5"
        slide-multiple
        @click="state.wide = 1"
      >
        <vueper-slide
          v-for="(tmp, i) in state.imageurl"
          :key="i"
          :image="tmp.pimage"
          class="imgwidth"
          @click="aa(tmp.pimage)"
        />
      </vueper-slides>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { VueperSlides, VueperSlide } from "vueperslides";
import "vueperslides/dist/vueperslides.css";
import axios from "axios";
export default {
  components: { VueperSlides, VueperSlide },
  setup() {
    const route = useRoute();
    const state = reactive({
      wide: 0,
      slideobj: {},
      slideobj2: {},
      move: 0,
      move2: 0,
      bb: "",
      aaobj: {},
      mcode: route.query.mcode,
      slides: [
        {
          image: require("../../assets/backcontainer/back1.jpg"),
        },
      ],
    });
    function slide2() {
      if (state.move2 > 0) {
        state.move2 = 0;
      }
      if (state.move2 < -3 * 400) {
        state.move2 = -3 * 400;
      }

      state.slideobj2.transform = `translateX(${state.move2}px)`;
    }

    function aa(temp) {
      state.bb = temp;
    }

    function slide() {
      if (state.move > 0) {
        state.move = 0;
      }
      if (state.move < -6 * 1040) {
        state.move = -6 * 1040;
      }

      state.slideobj.transform = `translateX(${state.move}px)`;
    }

    function scrolllock() {
      window.addEventListener("scroll", scrolllock);
      window.scrollTo(0, 350);
    }
    function scrollunlock() {
      window.removeEventListener("scroll", scrolllock);
    }

    // 포스터 리스트
    const handleData = async () => {
      const url = `/TEAMMOVIE/api/movie/get_poster?mcode=${state.mcode}`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        state.imageurl = response.data.posterlist;
        state.images = response.data.images;
        console.log(state.imageurl);
      }
    };

    onMounted(() => {
      handleData();
    });

    return { state, handleData, aa, slide, slide2 };
  },
};
</script>

<style scoped>
.xx {
  position: absolute;
  top: 500px;
  left: 990px;
  z-index: 6;
  border: 1px solid #6666;
  border-radius: 50%;
  background: white;
  color: gray;
  cursor: pointer;
}

.backwrap img {
  position: absolute;
  top: 500px;
  left: 600px;
  z-index: 6;
  width: 400px;
  height: 500px;
}

.backwrap {
  width: 100%;
  height: 200vh;
  background-color: black;
  background-color: rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 0px;
  left: 0px;
  z-index: 5;
}

.arrow {
  position: absolute;
  top: 50px;
  left: -250px;
  font-size: 100px;
  cursor: pointer;
  color: #f7f8f9;
}
.arrow1 {
  position: absolute;
  top: 50px;
  right: 250px;
  font-size: 100px;
  cursor: pointer;
  color: #f7f8f9;
}

.imgwrap_img {
  width: 500px;
  height: 500px;
  margin-right: 530px;
  transition: all 3s;
}

.imgwrap {
  display: flex;
  flex: 0 0 500px;
  overflow: hidden;
}

.exit {
  z-index: 100;
  position: absolute;
  top: 1px;
  left: 485px;
  cursor: pointer;
  margin: 0px;
  text-align: center;
  color: white;
  font-size: 30px;
}

.posterback {
  background-color: black;
  background-color: rgba(0, 0, 0, 0.5);
  position: absolute;
  width: 100%;
  height: 200vh;
  top: 0px;
  left: 0px;
  z-index: 7;
}

.posterwrap {
  width: 1050px;
  position: absolute;
  top: -400px;
  left: 500px;
  z-index: 11;
}

.slideswidth {
  width: 1050px;
}
.postercon {
  display: flex;
  justify-content: center;
  position: relative;
}
.con {
  display: flex;
  justify-content: center;
}

.wrap {
  display: flex;
  width: 1050px;
}
img {
  width: 200px;
  height: 300px;
  margin-left: 10px;
}
.imgwidth {
  background-size: contain;
  background-repeat: no-repeat;
}

/* aassssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss */
</style>