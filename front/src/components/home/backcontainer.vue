<template>
  <div class="backcon">
    <div class="backwrap">
      <div class="gra1"></div>
      <div class="gra2"></div>

      <div
        class="radibtn"
        style="right: 30px"
        @click="(state.move -= 1000), slide()"
      >
        <h1>&#62;</h1>
      </div>
      <div
        class="radibtn"
        style="left: 30px"
        @click="(state.move += 1000), slide()"
      >
        <h1>&#60;</h1>
      </div>
      <div class="con">
        <div
          class="wrap"
          :style="state.styleObjectwrap"
          v-for="(a, i) in 3"
          :key="i"
        >
          <img
            :src="require(`../../assets/backcontainer/bback${a}.jpg`)"
            class="poster"
            @click="modalm(i), scrolllock()"
          />
        </div>
      </div>
    </div>
  </div>
  <div class="modal" v-show="videoi === 0">
    <div class="modalwrap">
      <div class="item">
        <h2 @click="modalm(-1), scrollunlock()" class="exit">X</h2>
        <video class="videobox" autoplay muted>
          <source src="../../assets/video/marvel.mp4" type="video/mp4" />
        </video>
      </div>
    </div>
  </div>
  <div class="modal" v-show="videoi === 1">
    <div class="modalwrap">
      <div class="item">
        <h2 @click="modalm(-1), scrollunlock()" class="exit">X</h2>
        <video class="videobox" autoplay muted>
          <source src="../../assets/video/witch2.mp4" type="video/mp4" />
        </video>
      </div>
    </div>
  </div>
  <div class="modal" v-show="videoi === 2">
    <div class="modalwrap">
      <div class="item">
        <h2 @click="modalm(-1), scrollunlock()" class="exit">X</h2>
        <video class="videobox" autoplay muted>
          <source src="../../assets/video/springday.mp4" type="video/mp4" />
        </video>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { VueperSlides, VueperSlide } from "vueperslides";
import "vueperslides/dist/vueperslides.css";
import { useStore } from "vuex";

export default {
  components: { VueperSlides, VueperSlide },

  setup() {
    const store = useStore();
    const videoi = computed(() => store.getters.getvideoi);
    const state = reactive({
      styleObject: {},
      styleObjectwrap: {},
      scrolledy: 0,
      modal: -1,
      move: 0,
    });

    function slide() {
      if (state.move > 0) {
        state.move = 0;
      }
      if (state.move < -2000) {
        state.move = -2000;
      }
      state.styleObjectwrap.transform = `translateX(${state.move}px)`;
    }
    window.addEventListener("scroll", handleScroll);

    function modalm(i) {
      store.commit("videoim", i);
    }

    function handleScroll(event) {
      state.scrolledy = window.scrollY;
    }

    function scrolllock() {
      window.addEventListener("scroll", scrolllock);
      window.scrollTo(0, 100);
    }
    function scrollunlock() {
      window.removeEventListener("scroll", scrolllock);
    }

    return {
      state,
      modalm,
      handleScroll,
      scrolllock,
      scrollunlock,
      slide,
      videoi,
    };
  },
};
</script>

<style scoped>
.exit {
  color: gray;
  cursor: pointer;
  text-align: right;
  margin: 0px;
  margin-right: 10px;
}

.radibtn {
  width: 40px;
  height: 40px;
  text-align: center;
  cursor: pointer;
  position: absolute;
  top: 180px;
  z-index: 11;
  color: gray;
}
.gra1 {
  width: 350px;
  height: 403px;
  position: absolute;
  z-index: 10;
  background: linear-gradient(to right, rgb(0, 0, 0, 1), rgba(0, 0, 0, 0));
  left: 0px;
  top: 0px;
}
.gra2 {
  width: 350px;
  height: 403px;
  position: absolute;
  z-index: 10;
  background: linear-gradient(to left, rgb(0, 0, 0, 1), rgba(0, 0, 0, 0));
  right: 0px;
  top: 0px;
}
.con {
  display: flex;
  justify-content: center;
  position: absolute;
}
.wrap {
  width: 1000px;
  flex: 0 0 1000px;
  transition: all 1s;
}
.poster {
  width: 100%;
}
.backcon {
  display: flex;
  justify-content: center;
  width: 100%;
  height: 403px;
  background: black;
}
.backwrap {
  position: relative;
  width: 1000px;
  overflow: hidden;
}
.modal {
  width: 100%;
  /* background-color: black;
  background-color: rgba(0, 0, 0, 0.5); */
  position: absolute;
  top: -170px;
  right: 0px;
  z-index: 30;
  display: flex;
  justify-content: center;
}
.modalwrap {
  padding-top: 150px;
  background-color: rgba(0, 0, 0, 0.5);
  width: 100%;
  height: 120vh;
}
.videobox {
  width: 920px;
  height: 520px;
  border-radius: 15px;
}
.item {
  background: black;
  width: 920px;
  margin-left: auto;
  margin-right: auto;
  border-radius: 15px;
  position: relative;
  top: 180px;
}
</style>