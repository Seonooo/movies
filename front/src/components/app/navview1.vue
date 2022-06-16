<template>
  <div class="navcon">
    <div class="navwrap">
      <div class="navitem">
        &nbsp;
        <h5 @click="mypage()">마이 페이지</h5>
      </div>
      <div class="navimgitem">
        <img
          src="../../assets/imgcon/team_movie3.png"
          alt=""
          class="logo"
          @click="homemove()"
        />
      </div>
      <div class="navitem1">
        <h5 @click="join()" v-show="!logged">회원가입</h5>
        &nbsp;
        <h5 @click="login()" v-show="!logged">로그인</h5>
        <h5 @click="logout()" v-show="logged">로그아웃</h5>
      </div>
    </div>
  </div>
  <div style="position: relative">
    <div class="navviewcon">
      <navview-2></navview-2>
    </div>
    <div
      class="navview1con"
      v-if="$store.state.navstate > 0"
      @mouseleave="$store.state.navstate = 0"
    >
      <navview-3></navview-3>
    </div>
  </div>
</template>

<script>
import { reactive, computed } from "vue";
import navview2 from "../app/navview2.vue";
import navview3 from "../app/navview3.vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  components: {
    navview2,
    navview3,
  },
  setup() {
    const store = useStore();
    const router = useRouter();
    const logged = computed(() => store.getters.getLogged);
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
    });

    function logout() {
      if (confirm("로그아웃하시겠습니까?")) {
        sessionStorage.removeItem("TOKEN");
        sessionStorage.removeItem("ID");
        store.commit("setLogged", false);

        router.push("/");
        router.go();
      } else {
        return false;
      }
    }

    function login() {
      router.push("/login");
    }

    function join() {
      router.push("/join");
    }

    function mypage() {
      if (state.token == null) {
        router.push("/login");
      } else {
        router.push("/mycgv");
      }
    }
    return { state, logout, login, join, mypage, logged };
  },
};
</script>

<style scoped>
.navviewcon {
  width: 100%;
}
.navview1con {
  position: absolute;
  width: 100%;
  top: 50px;
  z-index: 20;
  min-width: 1280px;
}

.navwrap {
  display: flex;
  width: 1030px;
  align-items: center;
  border-bottom: 1px solid #b2b2b2;
}

h5 {
  cursor: pointer;
  display: inline;
  color: black;
}

.navcon {
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
  z-index: 20;
  width: 100%;
  min-width: 1280px;
}
.navimgitem {
  width: 300px;
  text-align: center;
}
.logo {
  width: 105px;
  margin-bottom: 20px;
  height: 40px;
  margin-top: 20px;
}
.navitem1 {
  width: 400px;
  text-align: right;
}
.navitem {
  width: 400px;
}
</style>