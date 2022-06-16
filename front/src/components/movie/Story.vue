<template>
  <div>
    <div class="con">
      <div class="item" v-bind:style="state.styleObject1">
        <div>
          <div v-if="state.mshot">
            <h4 v-html="state.mshot"></h4>
          </div>
          <div v-if="state.mlong">
            <h4 v-html="state.mlong"></h4>
          </div>
        </div>
      </div>
    </div>
    <div class="con">
      <div
        class="item"
        width="auto"
        style="justify-content: center; height: 50px; margin-top: 30px"
      >
        <h3 @click="moreviewm()" style="cursor: pointer">{{ state.button }}</h3>
      </div>
    </div>

    <!-- <button>{{ state.button }}</button> -->
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
      mlong: "",
      mshot: "",
      mcode: route.query.mcode,
      styleObject1: {},
      button: "더 보기",
    });

    function moreviewm() {
      if (state.button === "더 보기") {
        state.styleObject1.height = "auto";
        state.button = "접기";
      } else if (state.button === "접기") {
        state.styleObject1.height = "325px";
        state.button = "더 보기";
      }
    }

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/movie/selectOne?mcode=${state.mcode}`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, { headers });

      if (response.data.status === 200) {
        state.movie = response.data.movie;
        console.log(response.data.movie);
        state.genre = response.data.category;
        state.regdate = response.data.release;
        console.log(state.movie);
        1;
        if (state.movie.mshot != "") {
          state.mshot = state.movie.mshot;
          state.mshot = state.mshot.split(".").join(". <br /><br />");
          state.mshot = state.mshot.split(",").join(", <br />");
          state.mshot = state.mshot.split("!").join("! <br />");
        }
        if (state.movie.mlong != "") {
          state.mlong = state.movie.mlong;
          state.mlong = state.mlong.split(".").join(". <br /><br />");
          state.mlong = state.mlong.split(",").join(", <br />");
          state.mlong = state.mlong.split("!").join("! <br />");
        }
      }
    };

    onMounted(() => {
      handleData();
    });

    return { state, moreviewm };
  },
};
</script>

<style lang="scss" scoped>
.con {
  display: flex;
  justify-content: center;
}
.item {
  width: 1000px;
  display: flex;
  height: 325px;
  overflow: hidden;
}
</style>