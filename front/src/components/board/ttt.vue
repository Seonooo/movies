<template>
  <div>
    <button @click="aa()">aaaaaaaaaaaaaaaaaaaa</button>
    {{ state.count }}
    <hr />
    {{ state.count.logged }}
  </div>
</template>

<script>
import { onMounted, reactive, toRefs } from "vue";

export default {
  setup() {
    const state = reactive({
      count: {},
    });

    function aa() {
      state.count = localStorage.getItem("vuex");
    }
    onMounted(() => {
      state.count = JSON.parse(localStorage.getItem("vuex"));
    });

    return { state, aa };
  },
};
</script>

<style lang="scss" scoped>
</style>