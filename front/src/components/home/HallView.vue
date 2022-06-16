<template>
  <div class="hallviewcon">
    <div class="hallviewwrap">
      <h2>상영관</h2>
    </div>
  </div>
  <div class="hallcontainer">
    <div class="hallitem">
      <img
        src="../../assets/hallview/hall2(1).png"
        alt=""
        class="hallimg"
        v-show="state.hall === 1"
      />
      <img
        src="../../assets/hallview/hall3(1).png"
        alt=""
        class="hallimg"
        v-show="state.hall === 2"
      />
      <img
        src="../../assets/hallview/hall1.png"
        alt=""
        class="hallimg"
        v-show="state.hall === 3"
      />
    </div>
    <div class="hallitem">
      <div class="hallitem1" @mouseover="hallm(1)" @click="normalmove()">
        <h3>일반상영관</h3>

        <img src="../../assets/arrow.png" class="arrow" />
      </div>
      <div class="hallitem1" @mouseover="hallm(2)" @click="couplemove()">
        <h3>커플상영관</h3>
        <img src="../../assets/arrow.png" class="arrow" />
      </div>
      <div class="hallitem1" @mouseover="hallm(3)" @click="premiummove()">
        <h3>프리미엄관</h3>
        <img src="../../assets/arrow.png" class="arrow" />
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { useRouter } from "vue-router";
export default {
  setup() {
    const router = useRouter();
    const state = reactive({
      hall: 1,
    });

    function hallm(i) {
      state.hall = i;
    }

    function normalmove() {
      router.push("/cinema2");
    }
    function couplemove() {
      router.push("/couple");
    }
    function premiummove() {
      router.push("/ciname1");
    }

    return { state, hallm, normalmove, couplemove, premiummove };
  },
};
</script>

<style scoped>
.arrow {
  width: 50px;
}
h2 {
  padding-left: 10px;
}
.hallcontainer {
  display: flex;
  justify-content: center;
  align-items: center;
}
.hallitem {
  width: 600px;
  padding: 10px;
}
.hallimg {
  width: 600px;
  height: 300px;
  border-radius: 10px;
}

.hallitem1 h3 {
  display: inline;
}
.itemment {
  display: inline;
  background: #f6f6f6;
  border-radius: 5px;
  padding: 3px;
  color: gray;
}

.hallitem1 {
  display: flex;
  justify-content: space-around;
  align-items: center;
  border-top: 1px solid #eeeeee;
  border-bottom: 1px solid #eeeeee;
  padding: 5px;
  cursor: pointer;
  height: 85px;
}

.hallviewcon {
  display: flex;
  justify-content: center;
  margin-top: 150px;
}
.hallviewwrap {
  width: 1200px;
}
</style>