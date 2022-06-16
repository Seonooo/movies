<template>
  <div class="scrollwrap">
    <div class="scrollwrap_div" @mouseenter="settouch2m()">
      <div class="scrollnav">
        <div class="imgitemlogo">
          <img
            src="../../assets/imgcon/team_movie2.png"
            alt=""
            class="logo"
            @click="homemove()"
          />
          <h5 class="cult">C U L T U R E P L E X</h5>
        </div>
        <div class="h5div">
          <h5 @click="mypage()">마이 페이지</h5>
          <h5 v-show="!logged" @click="login()">로그인</h5>
          <h5 v-show="logged" @click="logout()">로그아웃</h5>
          <h5 v-show="!logged" @click="join()">회원가입</h5>
        </div>
        <!-- <div ><input type="text"/></div>
                <div class="imgitem2" style="width: 50px; border-right: 1px solid #6666">
                    <img src="../../assets/guidecon/wat2.png" alt="" class="img"/>
                </div> -->
      </div>
    </div>
    <transition name="fade" v-show="state.touch === 1">
      <div class="dropcon" @mouseleave="touchoff()">
        <div class="dropitem" style="border: none">
          <h5 style="color: black">예매</h5>
          <h6 @click="sche1move()">일반상영관</h6>
          <h6 @click="sche2move()">커플상영관</h6>
          <h6 @click="sche3move()">프리미엄관</h6>
        </div>
        <div class="dropitem">
          <h5 style="color: black">영화</h5>
          <h6 @click="moviemove()">영화검색</h6>
          <h6 @click="aa()">영화문의</h6>
        </div>
        <div class="dropitem">
          <h5 style="color: black">영화관</h5>
          <h6 @click="cinema2move()">일반상영관</h6>
          <h6 @click="fourdmove()">커플상영관</h6>
          <h6 @click="charmove()">프리미엄관</h6>
        </div>

        <div class="dropitem">
          <h5 style="color: black">게시판</h5>
          <h6
            v-for="(a, i) in 4"
            :key="i"
            @click="
              boardmove();
              $store.state.board = i;
            "
          >
            {{ review[i] }}
          </h6>
        </div>
      </div>
    </transition>
  </div>
</template>

    <script>
import { onMounted, reactive, computed } from "vue";

import { useRouter } from "vue-router";
import { useStore } from "vuex";
export default {
  components: {},

  setup() {
    const router = useRouter();
    const store = useStore();
    const logged = computed(() => store.getters.getLogged);
    const gettouch2 = computed(() => store.getters.gettouch2);
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
      touch: 0,
    });

    console.log(gettouch2.value);

    function logout() {
      if (confirm("로그아웃하시겠습니까?")) {
        sessionStorage.removeItem("TOKEN");
        store.commit("setLogged", false);
        router.push("/");
      } else {
        return false;
      }
    }
    function settouch2m() {
      state.touch = 1;
    }

    function homemove() {
      router.push("/");
    }

    function login() {
      router.push("/login");
    }

    function join() {
      router.push("/join");
    }
    function aa() {
      router.push("/moviechat");
    }

    function mypage() {
      if (state.token == null) {
        router.push("/login");
      } else {
        router.push("/mycgv");
      }
    }

    function ifaa() {
      if (gettouch2.value === true) {
        store.commit("touchm", false);
      }
    }

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
      state.touch = 0;
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
    onMounted(() => {
      console.log(gettouch2.value);
      ifaa();
    });
    return {
      state,
      logout,
      login,
      join,
      mypage,
      homemove,
      logged,
      gettouch2,
      settouch2m,
      ifaa,
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
      aa,
    };
  },
};
</script>
<style scoped="scoped">
h6 {
  cursor: pointer;
}

h5 {
  color: white;
}

.imgitemlogo {
  width: 80%;

  display: flex;
}

.logo {
  width: 55px;
  display: block;
  margin-left: 40px;
  margin-right: 20px;
  height: 30px;

  cursor: pointer;
}

.fade-enter-from {
  transform: translateY(-200px);
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
  transform: translateY(-200px);
}
.fade-leave-active {
  transition: 0.8s;
}
.cult {
  display: inline;
  margin-left: 20px;
  color: #d6d1cc;
  width: 150px;
  margin-bottom: 0px;
  margin-top: 10px;
}
.scrollwrap {
  position: fixed;
  top: 0;
  z-index: 20;
  width: 100%;
  min-width: 991px;
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
  margin-left: 150px;
  background: #f15438;
}

.h5div {
  color: white;
  width: 100px;
  text-align: center;
  display: flex;
  justify-content: right;
  width: 300px;
  margin-right: 10px;
}

.h5div h5 {
  margin-right: 30px;
  cursor: pointer;
  margin-bottom: 0px;
}

.scrollnav {
  width: 1030px;
  display: flex;
  justify-content: space-between;
  height: 60px;
  align-items: center;
}

.scrollwrap_div {
  display: flex;
  justify-content: center;
  background: black;
  position: relative;
  z-index: 20;
}
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