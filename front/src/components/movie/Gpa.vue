<template>
  <div class="con">
    <div class="wrap">
      <h3>평점</h3>

      <div v-if="state.gpa" class="item">
        <img src="../../assets/movie/star.png" class="star" />
        <h4>언론 평점 : {{ state.gpa }}</h4>
      </div>
      <div v-if="state.userGpa" class="item">
        <img src="../../assets/movie/star.png" class="star" />
        <h4>관객 평점 : {{ state.userGpa }}</h4>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { useRoute } from "vue-router";
import { onMounted } from "@vue/runtime-core";
import axios from "axios";
export default {
  setup() {
    const route = useRoute();
    const state = reactive({
      gpa: route.query.mgpa,
      mcode: route.query.mcode,
      page: 1,
      size: 10,
    });

    // 관람객 평점
    const handleDataGpa = async () => {
      const url = `/TEAMMOVIE/api/movie/get_gpa?mcode=${state.mcode}`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        console.log(response.data);
        state.userGpa = response.data.gpa.ggpa;
      }
    };

    onMounted(() => {
      handleDataGpa();
    });

    return { state };
  },
};
</script>

<style scoped>
.con {
  display: flex;
  justify-content: center;
}
.wrap {
  width: 1000px;
}
.star {
  width: 15px;
  height: 15px;
  margin-right: 10px;
}
.item {
  display: flex;
  align-items: center;
}
</style>