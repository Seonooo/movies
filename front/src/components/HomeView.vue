<template>
  <!-- 상단광고 -->
  <div class="con" style="background: black">
    <img src="../assets/home/hometopad.jpg" alt="" />
  </div>

  <navview-1></navview-1>

  <backcontainer></backcontainer>

  <movie-scroll v-bind:style="state.moviemove" class="movie"></movie-scroll>

  <!-- 슬라이드이벤트 -->
  <div class="adcon" style="margin-top: 100px">
    <div style="width: 980px">
      <h2 v-bind:style="state.styleObject6">EVENT</h2>
    </div>
  </div>
  <div class="adcon">
    <div class="adwrap">
      <div class="aditem">
        <img
          src="../assets/eventview/ad1.jpg"
          v-bind:style="state.styleObject"
          class="ad1"
        />
        <img
          src="../assets/eventview/ad2.jpg"
          v-bind:style="state.styleObject1"
          class="ad2"
        />
        <img
          src="../assets/eventview/ad3.jpg"
          v-bind:style="state.styleObject2"
          class="ad3"
        />
        <img
          src="../assets/eventview/ad7.jpg"
          v-bind:style="state.styleObject7"
          class="aa"
        />
        <img
          src="../assets/eventview/ad4.jpg"
          v-bind:style="state.styleObject3"
          class="ad4"
        />
        <img
          src="../assets/eventview/ad5.jpg"
          v-bind:style="state.styleObject4"
          class="ad5"
        />
        <img
          src="../assets/eventview/ad8.jpg"
          v-bind:style="state.styleObject8"
          class="aa"
        />
      </div>
      <div class="aditem1">
        <img
          src="../assets/eventview/ad6.jpg"
          v-bind:style="state.styleObject5"
          class="ad6"
        />
      </div>
    </div>
  </div>
  <!-- aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa -->
  <div class="hallviewcon hallm2" v-bind:style="state.hallmove2">
    <div class="hallviewwrap">
      <h2>상영관</h2>
    </div>
  </div>
  <div class="hallcontainer">
    <div class="hallitem hallm" v-bind:style="state.hallmove">
      <img
        src="../assets/hallview/hall2(1).png"
        alt=""
        class="hallimg"
        v-show="state.hall === 1"
      />
      <img
        src="../assets/hallview/hall3(1).png"
        alt=""
        class="hallimg"
        v-show="state.hall === 2"
      />
      <img
        src="../assets/hallview/hall1.png"
        alt=""
        class="hallimg"
        v-show="state.hall === 3"
      />
    </div>
    <div class="hallitem hallm1" v-bind:style="state.hallmove1">
      <div class="hallitem1" @mouseover="hallm(1)" @click="normalmove()">
        <h3>일반상영관</h3>

        <img src="../assets/arrow.png" class="arrow" />
      </div>
      <div class="hallitem1" @mouseover="hallm(2)" @click="couplemove()">
        <h3>커플상영관</h3>
        <img src="../assets/arrow.png" class="arrow" />
      </div>
      <div class="hallitem1" @mouseover="hallm(3)" @click="premiummove()">
        <h3>프리미엄관</h3>
        <img src="../assets/arrow.png" class="arrow" />
      </div>
    </div>
  </div>
</template>

<script>
import EventView from "../components/home/EventView.vue";
// import HallView from "../components/home/HallView.vue";
import backcontainer from "../components/home/backcontainer.vue";
import MovieScroll from "../components/home/MovieScroll.vue";
import navview1 from "../../src/components/app/navview1.vue";
import { reactive } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  components: {
    EventView,
    backcontainer,
    navview1,
    MovieScroll,
  },
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      scrolledy: 0,
      styleObject: {},
      styleObject1: {},
      styleObject2: {},
      styleObject3: {},
      styleObject4: {},
      styleObject5: {},
      styleObject6: {},
      styleObject7: {},
      styleObject8: {},
      slideobject: {},
      hallmove: {},
      hallmove1: {},
      hallmove2: {},
      moviemove: {},
      rightclick: 0,
      leftclick: 0,
      hall: 1,
    });
    function hallm(i) {
      state.hall = i;
    }
    function normalmove() {
      router.push("/cinema2");
    }
    function couplemove() {
      router.push("/couple");
    }
    function premiummove() {
      router.push("/cinema1");
    }

    function sliderm(event) {
      state.slideobject.transform = `translateX(${state.rightclick}px)`;
    }
    function slidelm(event) {
      state.slideobject.transform = `translateX(${state.leftclick}px)`;
    }

    //네브바온
    store.commit("cgvnavm", false);

    //스크롤 정보
    window.addEventListener("scroll", handleScroll);

    function handleScroll(event) {
      state.scrolledy = window.scrollY;
    }

    //스크롤 위치이동
    window.addEventListener("scroll", slide);
    window.addEventListener("scroll", slide1);
    window.addEventListener("scroll", slide2);

    function slide() {
      if (state.scrolledy >= 800) {
        state.styleObject.transform = "translateX(0px)";
        state.styleObject.opacity = "1";
        state.styleObject1.transform = "translateX(10px)";
        state.styleObject1.opacity = "1";
        state.styleObject2.transform = "translateX(20px)";
        state.styleObject2.opacity = "1";
        state.styleObject3.transform = "translateX(0px)";
        state.styleObject3.opacity = "1";
        state.styleObject4.transform = "translateX(10px)";
        state.styleObject4.opacity = "1";
        state.styleObject5.transform = "translateX(0px)";
        state.styleObject5.opacity = "1";
        state.styleObject6.transform = "translateX(-90px)";
        state.styleObject6.opacity = "1";
        state.styleObject7.transform = "translateX(25px)";
        state.styleObject7.opacity = "1";
        state.styleObject8.transform = "translateX(15px)";
        state.styleObject8.opacity = "1";
      } else {
      }
    }
    function slide1() {
      if (state.scrolledy >= 1600) {
        state.hallmove.transform = "translateX(0px)";
        state.hallmove.opacity = "1";
        state.hallmove1.transform = "translateX(0px)";
        state.hallmove1.opacity = "1";
        state.hallmove2.transform = "translateX(0px)";
        state.hallmove2.opacity = "1";
      }
    }
    function slide2() {
      if (state.scrolledy >= 300) {
        state.moviemove.transform = "translateX(0px)";
        state.moviemove.opacity = "1";
      }
    }

    return {
      handleScroll,
      state,
      slide,
      sliderm,
      slidelm,
      hallm,
      normalmove,
      couplemove,
      slide1,
      premiummove,
      slide2,
    };
  },
};
</script>

<style scoped>
.hallm {
  transform: translateX(-300px);
  opacity: 0;
}
.hallm1 {
  transform: translateX(300px);
  opacity: 0;
}
.hallm2 {
  transform: translateX(-300px);
  opacity: 0;
}

.aa {
  width: 220px;
  height: 250px;
  opacity: 0;
  transform: translateX(300px);
}
.ad1 {
  opacity: 0;
  transform: translateX(-200px);
}
.ad2 {
  opacity: 0;
  transform: translateX(-400px);
}
.ad3 {
  opacity: 0;
  transform: translateX(-700px);
}
.ad4 {
  opacity: 0;
  transform: translateX(-500px);
}
.ad5 {
  opacity: 0;
  transform: translateX(500px);
}
.ad6 {
  opacity: 0;
  transform: translateX(300px);
}

.adcon h2 {
  transition: all 3s;
  opacity: 0;
  transform: translateX(300px);
  margin-left: -20px;
}

.con {
  display: flex;
  justify-content: center;
}
.wrap {
  width: 1000px;
}

.aditem img {
  transition: all 3s;
  margin-top: 5px;
}
.aditem1 img {
  transition: all 3s;
  margin-top: 3px;
}
.adwrap {
  display: flex;
  width: 1200px;
  background: white;
}
.adcon {
  display: flex;
  justify-content: center;
}
/* hall */
.arrow {
  width: 50px;
}
h2 {
  padding-left: 10px;
}
.hallcontainer {
  display: flex;
  justify-content: center;
  align-items: center;
}
.hallitem {
  width: 600px;
  padding: 10px;
  transition: all 3s;
}
.hallimg {
  width: 600px;
  height: 300px;
  border-radius: 10px;
}

.hallitem1 h3 {
  display: inline;
}
.itemment {
  display: inline;
  background: #f6f6f6;
  border-radius: 5px;
  padding: 3px;
  color: gray;
}

.hallitem1 {
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-top: 1px solid #eeeeee;
  border-bottom: 1px solid #eeeeee;
  padding: 5px;
  cursor: pointer;
  height: 85px;
}

.hallviewcon {
  display: flex;
  justify-content: center;
  margin-top: 150px;
  transition: all 3s;
}
.hallviewwrap {
  width: 1200px;
}
.movie {
  transition: all 3s;
  transform: translateX(500px);
  opacity: 0;
}
</style>