<template>
  <div class="dropcon" @mouseleave="touchoff()">
    <div class="dropitem" style="border: none">
      <h5 @click="sche1move()">일반상영관</h5>
      <h5 @click="sche2move()">커플상영관</h5>
      <h5 @click="sche3move()">프리미엄관</h5>
    </div>
    <div class="dropitem">
      <h5 @click="moviemove()">영화검색</h5>
      <h5 @click="chatmove()">영화문의</h5>
    </div>
    <div class="dropitem">
      <h5 @click="cinema2move()">일반상영관</h5>
      <h5 @click="fourdmove()">커플상영관</h5>
      <h5 @click="charmove()">프리미엄관</h5>
    </div>

    <!-- <div class="dropitem">
      <h5>이벤트</h5>
      <small @click="eventmove()">진행중인 이벤트</small>
      <p>slot2</p>
    </div> -->
    <div class="dropitem">
      <h5
        v-for="(a, i) in 4"
        :key="i"
        @click="
          boardmove();
          $store.state.board = i;
        "
      >
        {{ review[i] }}
      </h5>
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
export default {
  setup() {
    let store = useStore();
    const router = useRouter();
    let review = reactive(["공지게시판", "문의게시판", "자유게시판"]);

    const boardmove = () => {
      router.push("/boardview");
    };
    const chatmove = () => {
      router.push("/moviechat");
    };
    const eventmove = () => {
      router.push("/eventview");
    };
    const charmove = () => {
      router.push("/cinema1");
    };
    function cinema2move() {
      router.push("/cinema2");
    }
    const fourdmove = () => {
      router.push("/couple");
    };
    const moviepagemove = () => {
      router.push("/MoviePage");
    };
    const moviemove = () => {
      router.push("/movie");
    };
    const touchoff = () => {
      store.commit("setdrop", 0);
    };

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
      eventmove,
      moviemove,
      sche1move,
      sche2move,
      sche3move,
      cinema2move,
      chatmove,
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
  height: 110px;
  background: black;
  opacity: 0.8;
  position: absolute;
  z-index: 2;
  width: 100%;
  justify-content: center;
  min-width: 1280px;

  top: 52px;
}
.dropitem {
  flex: 0 auto;
  width: 130px;
  padding-left: 20px;
  margin: 0;
  text-align: center;
}
.dropitem h5 {
  cursor: pointer;
  display: block;
  margin: 15px 0;
  width: 100px;
}
h5 {
  margin-top: 0;
  color: white;
}
</style>