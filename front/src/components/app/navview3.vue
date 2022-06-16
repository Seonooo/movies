<template>
  <div class="con">
    <div v-if="$store.state.navstate == 1" class="flexbetween">
      <h5 @click="sche1move()">일반상영관</h5>
      <h5 @click="sche2move()">커플상영관</h5>
      <h5 @click="sche3move()">프리미엄관</h5>
    </div>
    <div v-if="$store.state.navstate == 2" class="flexbetween">
      <h5 @click="moviemove()">영화검색</h5>
      <h5 @click="aa()">영화문의</h5>
    </div>
    <div v-if="$store.state.navstate == 3" class="flexbetween">
      <h5 @click="cinema2move()">일반상영관</h5>
      <h5 @click="fourdmove()">커플상영관</h5>
      <h5 @click="charmove()">프리미엄관</h5>
    </div>
    <div v-if="$store.state.navstate == 4"><p>444444</p></div>
    <div v-if="$store.state.navstate == 5" class="flexbetween">
      <h5
        v-for="(a, i) in 3"
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
import { reactive } from "@vue/reactivity";
import { useRouter } from "vue-router";
export default {
  setup() {
    const review = reactive(["공지게시판", "문의게시판", "자유게시판"]);
    const router = useRouter();

    function sche1move() {
      router.push("/sche1");
    }
    function sche2move() {
      router.push("/sche2");
    }
    function sche3move() {
      router.push("/sche3");
    }
    function boardmove() {
      router.push("/boardview");
    }
    function charmove() {
      router.push("/cinema1");
    }
    function cinema2move() {
      router.push("/cinema2");
    }
    function fourdmove() {
      router.push("/couple");
    }
    const moviemove = () => {
      router.push("/movie");
    };

    function aa() {
      router.push("/moviechat");
    }
    return {
      review,
      sche1move,
      sche2move,
      sche3move,
      boardmove,
      charmove,
      fourdmove,
      moviemove,
      cinema2move,
      aa,
    };
  },
};
</script>

<style scoped>
.con {
  display: flex;
  justify-content: center;
  background: black;
  opacity: 0.8;
  height: 50px;
  align-items: center;
}

h5 {
  color: white;
  cursor: pointer;
  width: 130px;
  text-align: center;
}
p {
  color: black;
}
.flexbetween {
  display: flex;
}
.vueperslides__arrow {
  color: yellow;
}
</style>