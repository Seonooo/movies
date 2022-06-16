<template>
  <div>
    <div class="adcon">
      <div class="adwrap">
        <div class="aditem">
          <img
            src="../../assets/eventview/ad1.jpg"
            v-bind:style="state.styleObject"
          />
          <img
            src="../../assets/eventview/ad2.jpg"
            v-bind:style="state.styleObject1"
          />
          <img
            src="../../assets/eventview/ad3.jpg"
            v-bind:style="state.styleObject2"
          />
          <img
            src="../../assets/eventview/ad4.jpg"
            v-bind:style="state.styleObject3"
          />
          <img
            src="../../assets/eventview/ad5.jpg"
            v-bind:style="state.styleObject4"
          />
        </div>
        <div class="aditem1">
          <img
            src="../../assets/eventview/ad6.jpg"
            v-bind:style="state.styleObject5"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";

export default {
  setup() {
    const state = reactive({
      scrolledy: 0,
      styleObject: {},
      styleObject1: {},
      styleObject2: {},
      styleObject3: {},
      styleObject4: {},
      styleObject5: {},
      styleObject6: {},
    });

    window.addEventListener("scroll", handleScroll);
    window.addEventListener("scroll", slide);

    function handleScroll(event) {
      state.scrolledy = window.scrollY;
    }
    function slide() {
      if (state.scrolledy >= 1000) {
        state.styleObject.transform = "translateX(0px)";
        state.styleObject.opacity = "1";
        state.styleObject1.transform = "translateX(10px)";
        state.styleObject1.opacity = "1";
        state.styleObject2.transform = "translateX(20px)";
        state.styleObject2.opacity = "1";
        state.styleObject3.transform = "translateX(0px)";
        state.styleObject3.opacity = "1";
        state.styleObject4.transform = "translateX(10px)";
        state.styleObject4.opacity = "1";
        state.styleObject5.transform = "translateX(0px)";
        state.styleObject5.opacity = "1";
        // state.styleObject6.transform = "translateX(0px)";
        // state.styleObject6.opacity = "1";
      } else {
        state.styleObject.transform = "translateX(-200px)";
        state.styleObject.opacity = "0";
        state.styleObject1.transform = "translateX(-400px)";
        state.styleObject1.opacity = "0";
        state.styleObject2.transform = "translateX(-700px)";
        state.styleObject2.opacity = "0";
        state.styleObject3.transform = "translateX(-500px)";
        state.styleObject3.opacity = "0";
        state.styleObject4.transform = "translateX(500px)";
        state.styleObject4.opacity = "0";
        state.styleObject5.transform = "translateX(300px)";
        state.styleObject5.opacity = "0";
        // state.styleObject6.transform = "translateX(300px)";
        // state.styleObject6.opacity = "0";
      }
    }

    return { state, slide, handleScroll };
  },
};
</script>

<style scoped>
</style>