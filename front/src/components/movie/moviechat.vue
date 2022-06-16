<template>
  <div class="con">
    <div class="wrap">
      <img src="https://www.sgu.ac.kr/_res/sgu_mobile/img/common/prepare.jpg" />
    </div>
  </div>
  <div class="con">
    <div class="wrap">
      <button @click="homemove()">홈으로</button>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";

import { useRouter } from "vue-router";

export default {
  setup(props) {
    const router = useRouter();
    const state = reactive({});

    function homemove() {
      router.push("/");
    }

    return { homemove, state };
  },
};
</script>

<style scoped>
.con {
  display: flex;
  justify-content: center;
  margin-top: 100px;
}
.wrap {
  width: 800px;
}
button {
  display: block;
  margin-left: auto;
  margin-right: auto;
  width: 200px;
  background: white;
  border: 1px solid #6666;
  height: 50px;
  cursor: pointer;
}
</style>