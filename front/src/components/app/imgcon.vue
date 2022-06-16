<template>
  <div class="con" style="background: black">
    <img src="../../assets/home/hometopad.jpg" alt="" />
  </div>
  <div class="aa">
    <div class="imgcontainer1">
      <div class="imgitemlogo">
        <img
          src="../../assets/imgcon/team_movie3.png"
          alt=""
          class="logo"
          @click="mainmove()"
        />
        <h5 class="cult">C U L T U R E P L E X</h5>
      </div>

      <div class="imgitem1" @click="mycgvmove()" style="cursor: pointer">
        <!-- <img src="../../assets/imgcon/mycgv2.png" alt="" class="img1" /> -->
        <h5>마이 페이지</h5>
      </div>
      <!-- <div class="imgitem1" style="cursor: pointer"> -->
      <!-- <img src="../../assets/imgcon/center.png" alt="" class="img1" /> -->
      <!-- <h5>고객센터</h5>
      </div> -->

      <!-- <div class="imgitem1" style="width: 200px">
      <img src="../../assets/imgcon/hynca.png" alt="" class="card" />
    </div> -->
      <div
        class="imgitem1"
        @click="loginmove()"
        style="cursor: pointer"
        v-show="!logged"
      >
        <!-- <img src="../../assets/imgcon/lock.png" alt="" class="img1" /> -->
        <h5>로그인</h5>
      </div>
      <div
        class="imgitem1"
        @click="logout()"
        style="cursor: pointer"
        v-show="logged"
      >
        <!-- <img src="../../assets/imgcon/lockout.png" alt="" class="img1" /> -->
        <h5>로그아웃</h5>
      </div>
      <div
        class="imgitem1"
        @click="joinmove()"
        style="cursor: pointer"
        v-show="!logged"
      >
        <!-- <img src="../../assets/imgcon/join.png" alt="" class="img1" /> -->
        <h5>회원가입</h5>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  setup(props) {
    const store = useStore();
    const logged = computed(() => store.getters.getLogged);
    const router = useRouter();
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
    });

    function joinmove() {
      router.push("/join");
    }
    function logout() {
      if (confirm("로그아웃하시겠습니까?")) {
        sessionStorage.removeItem("TOKEN");
        sessionStorage.removeItem("RTOKEN");
        sessionStorage.removeItem("ID");
        store.commit("setLogged", false);
        router.push("/");
      } else {
        return false;
      }
    }
    function mainmove() {
      router.push("/");
    }
    function loginmove() {
      router.push("/login");
    }
    function mycgvmove() {
      if (logged) {
        router.push("/login");
      } else {
        router.push("/mycgv");
      }
    }

    return { mycgvmove, logout, loginmove, mainmove, joinmove, state, logged };
  },
};
</script>

<style scoped>
h5 {
  color: black;
}

.cult {
  display: inline;
  margin-left: 10px;
  color: black;
}

.imgitemlogo {
  width: 80%;
  cursor: pointer;
  display: flex;
}

.aa {
  background: white;
  position: relative;
  z-index: 30;
  display: flex;
  justify-content: center;
}
.imgcontainer1 {
  display: flex;
  align-items: center;
  height: 70px;
  color: white;
  width: 1030px;
}
.imgitem1 {
  flex: 0 auto;
  width: 85px;
  text-align: center;
}
.logo {
  width: 105px;
  display: block;
  margin-left: 40px;
  margin-right: 20px;
  height: 40px;
  margin-top: 7px;
}
.img1 {
  width: 25px;
  height: 25px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
.card {
  width: 136px;
  height: 39px;
}
small {
  display: block;
  text-align: center;
  margin-top: 5px;
}
.con {
  display: flex;
  justify-content: center;
  position: relative;
  z-index: 100;
}
</style>