<template>
  <div class="scrollwrap">
    <div class="scrollnav">
      <h5 @mouseenter="mouseenter2()">예매</h5>
      <h5 @mouseenter="mouseenter2()">영화</h5>
      <h5 @mouseenter="mouseenter2()">영화관</h5>
      <h5 @mouseenter="mouseenter2()">게시판</h5>
    </div>
    <transition name="fade" v-show="drop === 1">
      <Hidencon></Hidencon>
    </transition>
  </div>
</template>

    <script>
import { reactive, computed } from "vue";
import Hidencon from "./hidencon.vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    const drop = computed(() => store.getters.getdrop);
    const state = reactive({
      over: 0,
      page: 1,
      size: 5,
      sort: 1,
      genre: 0,
      mscode: 1,
      title: "",
      imageurl: [],
      url: [],
      movies: [],
      search: "",
    });

    function searchm() {
      store.commit("searchm", state.search);
      store.commit("touchm", false);
      router.push({ name: "movie", query: { title: state.search } });
    }
    function mouseenter2() {
      store.commit("setdrop", 1);
    }

    return { state, searchm, drop, mouseenter2 };
  },

  methods: {},

  components: {
    Hidencon,
  },
};
</script>

<style scoped="scoped">
.fade-enter-from {
  transform: translateY(-300px);
}
.fade-enter-to {
  transform: translateY(0px);
}
.fade-enter-active {
  transition: 0.8s;
}
.fade-leave-from {
  transform: translateY(0px);
}
.fade-leave-to {
  transform: translateY(-300px);
}
.fade-leave-active {
  transition: 0.8s;
}

.scrollwrap {
  z-index: 20;
  width: 100%;
  min-width: 991px;
  display: flex;
  justify-content: center;
  position: relative;
}

.img {
  width: 25px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

input {
  border: none;
  border-left: 1px solid #6666;
  height: 25px;
  margin-left: 230px;
  background: white;
}

h5 {
  color: black;
  width: 150px;
  text-align: center;
  margin: 0px;
}

.scrollnav {
  background: white;
  width: 100%;
  display: flex;
  justify-content: center;
  height: 50px;
  align-items: center;
  z-index: 50;
  border-bottom: 1px solid #b2b2b2;
  border-top: 1px solid #b2b2b2;
}

.logo {
  width: 70px;
  height: 33px;
  margin-right: 10px;
  margin-bottom: 10px;
}
</style>