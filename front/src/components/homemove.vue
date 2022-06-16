<template>
  <div></div>
</template>

<script>
import { reactive, toRefs } from "vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      count: 0,
    });
    store.commit("setLogged", false);
    router.push("/");

    return {
      ...toRefs(state),
    };
  },
};
</script>

<style lang="scss" scoped>
</style>