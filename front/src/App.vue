<template>
  <div>
    <Scrollnav v-show="state.scrolledy >= 200"></Scrollnav>

    <div v-if="cgvnav">
      <imgcon></imgcon>

      <Dropnav @mouseenter="mouseenter()"></Dropnav>

      <!-- <guidecon @mouseenter="$store.commit('touchm',true)"></guidecon>
        <transition name="fade" v-show="$store.state.touch === true"> <hidencon
        ></hidencon> </transition> -->
    </div>
    <router-view></router-view>
    <!-- <div class="underadcontainer">
      <div class="underaditem">
        <img src="./assets/underad3.jpg" alt="" />
      </div>
    </div> -->
    <div class="undercontainer"></div>
  </div>
</template>

<script>
import undercontainer from "./components/app/undercontainer.vue";
import guidecon from "../src/components/app/guidecon.vue";
import hidencon from "../src/components/app/hidencon.vue";
import imgcon from "../src/components/app/imgcon.vue";
import Scrollnav from "./components/app/scrollnav.vue";
import Hidencon2 from "./components/app/hidencon2.vue";
import Dropnav from "./components/app/dropnav.vue";
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
// import {ref} from 'vue'

export default {
  name: "App",
  components: {
    undercontainer,
    guidecon,
    hidencon,
    imgcon,
    Scrollnav,
    Hidencon2,
    Dropnav,
  },

  // setup() {          let scrolledy = ref([0])     const handleScroll = () => {
  // scrolledy = window.scrollY;     }    window.addEventListener('scroll',
  // handleScroll);     return {handleScroll, scrolledy}     },

  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      scrolledy: 0,
    });

    const cgvnav = computed(() => store.getters.getcgvnav);

    function handleScroll() {
      state.scrolledy = window.scrollY;
    }
    function mouseenter() {
      store.commit("touchm", true);
    }
    // function mouseenter2() {
    //   store.commit("touch2m", true);
    // }
    window.addEventListener("scroll", handleScroll);

    return { state, handleScroll, mouseenter, cgvnav };
  },
};
</script>
<style scoped="scoped">
.topadcontainer {
  display: flex;
  justify-content: center;
  height: 80px;
  background: #fef8ea;
}
.topaditem {
  flex: 0 auto;
  background: #fef8ea;
  width: 991px;
}
.imgcontainer {
  background: white;
  display: flex;
  justify-content: center;
  height: 113px;
  position: relative;
  width: 100%;
  z-index: 3;
}
.imgitem {
  flex: 0 auto;
  background: white;
  width: 991px;
  box-sizing: border-box;
}
.guidecontainer {
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
}
.underadcontainer {
  display: flex;
  justify-content: center;
  height: 180px;
  margin-top: 150px;
}
.undercontainer {
  display: flex;
  justify-content: center;
  height: 200px;
}
.underitem {
  flex: 0 auto;
  width: 991px;
  padding: 10px;
  box-sizing: border-box;
}
.container2 {
  display: flex;
  justify-content: center;
  height: 300px;
}
.item2 {
  flex: 0 auto;
  width: 991px;
  padding: 10px;
  box-sizing: border-box;
}
.fade-enter-from {
  transform: translateY(-300px);
  /* top: -165px; */
}
.fade-enter-to {
  /* top: 165px; */
  transform: translateY(0px);
}
.fade-enter-active {
  transition: 0.8s;
}
.fade-leave-from {
  /* top: 165px; */
  transform: translateY(0px);
}
.fade-leave-to {
  /* top: -165px; */
  transform: translateY(-300px);
}
.fade-leave-active {
  transition: 0.8s;
}
</style>