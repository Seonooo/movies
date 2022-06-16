<template>
  <div>
    <div class="profilecon">
      <div class="profilecon_div">
        <Mycgvprofile></Mycgvprofile>
      </div>
    </div>
    <div class="mycgvcon">
      <div class="mycgvcon_div-side">
        <Mycgvside></Mycgvside>
      </div>
      <div class="mycgvcon_div-center">
        <div v-show="$store.state.mycgv === 1">
          <Mycgvreser></Mycgvreser>
        </div>
        <div v-show="$store.state.mycgv === 2">
          <Mycgvcoupon></Mycgvcoupon>
        </div>
        <div v-show="$store.state.mycgv === 3">
          <Mycgvupdate></Mycgvupdate>
        </div>
        <div v-show="$store.state.mycgv === 4">
          <Mycgvprofileupdate></Mycgvprofileupdate>
        </div>
        <div v-show="$store.state.mycgv === 5">
          <Mycgvdele></Mycgvdele>
        </div>
        <div v-show="$store.state.mycgv === 6">
          <mycgvconfirm></mycgvconfirm>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Mycgvprofile from "../../components/mycgv/mycgvprofile.vue";
import Mycgvside from "../../components/mycgv/mycgvside.vue";
import Mycgvreser from "./mycgvreser.vue";
import Mycgvdele from "./mycgvdele.vue";
import Mycgvupdate from "./mycgvupdate.vue";
import Mycgvprofileupdate from "./mycgvprofileupdate.vue";
import mycgvconfirm from "./mycgvconfirm.vue";
import Mycgvcoupon from "./mycgvcoupon.vue";
import { onMounted } from "@vue/runtime-core";
import store from "@/vuex/vuex";

export default {
  components: {
    Mycgvprofile,
    Mycgvside,
    Mycgvreser,
    Mycgvdele,
    Mycgvupdate,
    Mycgvprofileupdate,
    Mycgvcoupon,
    mycgvconfirm,
  },

  setup() {
    onMounted(() => {
      store.commit("cgvnavm", true);
    });
  },
};
</script>

<style scoped="scoped">
* {
  box-sizing: border-box;
}

.mycgvcon_div-center {
  width: 800px;
  padding-left: 20px;
}

.mycgvcon_div-side {
  width: 200px;
}

.mycgvcon {
  display: flex;
  justify-content: center;
  margin-bottom: 100px;
}

.profilecon {
  display: flex;
  height: 500px;
  justify-content: center;
  margin-bottom: 40px;
  margin-top: 100px;
}
.profilecon_div {
  background: #f7f7f7;
  width: 1000px;
  border-bottom: 1px solid #6666;
  border-top: 1px solid black;
}
</style>