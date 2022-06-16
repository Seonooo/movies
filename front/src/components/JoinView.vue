<template>
  <!-- <div class="con1" v-show="$store.state.joincli == 0">
    <div class="item1">
      <joinimg></joinimg>
    </div>
  </div> -->
  <div class="con2">
    <div class="item2">
      <joininp></joininp>
    </div>
  </div>
</template>

<script>
import joinimg from "../../src/components/join/joinimg.vue";
import joininp from "../../src/components/join/joininp.vue";

export default {
  created() {
    this.$store.commit("cgvnavm", true);
  },

  components: {
    joinimg,
    joininp,
  },

  methods: {},
};
</script>

<style scoped>
.con1 {
  display: flex;
  justify-content: center;
}
.item1 {
}
.con2 {
  display: flex;
  justify-content: center;
  margin-bottom: 100px;
}
</style>