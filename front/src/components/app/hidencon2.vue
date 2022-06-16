<template>
  <div
    class="dropcon"
    @mouseleave="touchoff()"
    v-show="gettouch2.value === true"
  >
    <div class="dropitem" style="border: none">
      <h5>예매</h5>
      <small @click="sche1move()">일반상영관</small>
      <small @click="sche2move()">커플상영관</small>
      <small @click="sche3move()">프리미엄관</small>
    </div>
    <div class="dropitem">
      <h5>영화</h5>
      <small @click="moviemove()">영화검색</small>
      <small>영화문의</small>
    </div>
    <div class="dropitem">
      <h5>영화관</h5>
      <small @click="cinema2move()">일반상영관</small>
      <small @click="fourdmove()">커플상영관</small>
      <small @click="charmove()">프리미엄관</small>
    </div>

    <div class="dropitem">
      <h5>게시판</h5>
      <small
        v-for="(a, i) in 4"
        :key="i"
        @click="
          boardmove();
          $store.state.board = i;
        "
      >
        {{ review[i] }}
      </small>
    </div>
  </div>
</template>

<script>
import { reactive, ref, computed } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const gettouch2 = computed(() => store.getters.gettouch2);
    let review = reactive(["공지게시판", "문의게시판", "자유게시판"]);

    const boardmove = () => {
      router.push("/boardview");
    };
    const charmove = () => {
      router.push("/cinema1");
    };
    const cinema2move = () => {
      router.push("/cinema2");
    };
    const fourdmove = () => {
      router.push("/couple");
    };
    const moviepagemove = () => {
      router.push("/MoviePage");
    };
    const moviemove = () => {
      router.push("/movie");
    };
    function touchoff() {
      store.commit("touch2m", false);
    }
    function sche1move() {
      router.push("/sche1");
    }
    function sche2move() {
      router.push("/sche2");
    }
    function sche3move() {
      router.push("/sche3");
    }

    return {
      review,
      boardmove,
      charmove,
      fourdmove,
      moviepagemove,
      touchoff,
      moviemove,
      sche1move,
      sche2move,
      sche3move,
      cinema2move,
      gettouch2,
    };
  },

  // methods: {

  //     touchoff() {
  //         this
  //             .$store
  //             .commit("touch2m", false);
  //     },
  // }
};
</script>

<style scoped="scoped">
p {
  cursor: pointer;
}
.dropcon {
  display: flex;
  height: 200px;
  background: white;
  border-bottom: 1px solid #eeeeee;
  position: absolute;
  z-index: 2;
  width: 100%;
  justify-content: center;
  min-width: 1280px;
  transition: transform 0.4s ease-in-out;
}
.dropitem {
  flex: 0 auto;
  width: 200px;
  border-left: 1px solid #eeeeee;
  padding-left: 20px;
  margin: 30px 0;
}
.dropitem small {
  cursor: pointer;
  display: block;
  margin: 15px 0;
  width: 100px;
}
h5 {
  margin-top: 0;
}
</style>