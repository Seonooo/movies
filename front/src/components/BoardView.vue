<template>
  <div class="con">
    <div class="item">
      <h2>게시판</h2>
      <el-button @click="handleInsert()">글 등록</el-button>
    </div>
  </div>
  <div class="cpt" style="margin-bottom: 30px">
    <div class="item1">
      <button
        @click="
          $store.commit('boardm', 0);
          aa();
        "
        v-bind:style="styleObject"
      >
        공지게시판
      </button>
      <button
        @click="
          $store.commit('boardm', 1);
          aa();
        "
        v-bind:style="styleObject1"
      >
        문의게시판
      </button>
      <button
        @click="
          $store.commit('boardm', 2);
          aa();
        "
        v-bind:style="styleObject2"
      >
        자유게시판
      </button>
    </div>
  </div>
  <div class="cpt">
    <div v-show="$store.state.board == 0" class="cptitem">
      <anboard></anboard>
    </div>
    <div v-show="$store.state.board == 1" class="cptitem">
      <inquboard></inquboard>
    </div>
    <div v-show="$store.state.board == 2" class="cptitem">
      <frboard></frboard>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import anboard from "../components/board/anboard.vue";
import frboard from "../components/board/frboard.vue";
import inquboard from "../components/board/inquboard.vue";
import ttt from "../components/board/ttt.vue";
import { ElButton } from "element-plus";

export default {
  methods: {
    handleInsert() {
      this.$router.push("/BoardWrite");
    },

    writemov() {
      sessionStorage.setItem("boardnum", this.$store.state.board);
      this.$router.push("/boardwrite");
    },

    aa() {
      if (this.$store.state.board == 0) {
        // axios.get("/TEAMMOVIE/api/board/selectlist?type=0").then((e)=>{console.log(e);})
        this.styleObject.background = "black";
        this.styleObject.color = "white";
        this.styleObject1.background = "white";
        this.styleObject1.color = "black";
        this.styleObject2.background = "white";
        this.styleObject2.color = "black";
        this.styleObject3.background = "white";
        this.styleObject3.color = "black";
      }
      if (this.$store.state.board == 1) {
        // axios.get("/TEAMMOVIE/api/board/selectlist?type=1").then((e)=>{console.log(e);})
        this.styleObject.background = "white";
        this.styleObject.color = "black";
        this.styleObject1.background = "black";
        this.styleObject1.color = "white";
        this.styleObject2.background = "white";
        this.styleObject2.color = "black";
        this.styleObject3.background = "white";
        this.styleObject3.color = "black";
      }
      if (this.$store.state.board == 2) {
        axios.get("/TEAMMOVIE/api/board/selectlist?type=2").then((e) => {
          console.log(e);
        });
        this.styleObject.background = "white";
        this.styleObject.color = "black";
        this.styleObject1.background = "white";
        this.styleObject1.color = "black";
        this.styleObject2.background = "black";
        this.styleObject2.color = "white";
        this.styleObject3.background = "white";
        this.styleObject3.color = "black";
      }
    },
  },

  data() {
    return {
      styleObject: {
        background: "",
        color: "",
      },
      styleObject1: {
        background: "",
        color: "",
      },
      styleObject2: {
        background: "",
        color: "",
      },
      styleObject3: {
        background: "",
        color: "",
      },
    };
  },

  components: {
    anboard,
    frboard,
    inquboard,
    ttt,
    ElButton,
  },

  created() {
    this.$store.commit("cgvnavm", true);

    axios.get("/TEAMMOVIE/api/board/selectlist?type=0").then((e) => {
      console.log(e);
    });

    if (this.$store.state.board == 0) {
      this.styleObject.background = "black";
      this.styleObject.color = "white";
      this.styleObject1.background = "white";
      this.styleObject1.color = "black";
      this.styleObject2.background = "white";
      this.styleObject2.color = "black";
      this.styleObject3.background = "white";
      this.styleObject3.color = "black";
    }
    if (this.$store.state.board == 1) {
      this.styleObject.background = "white";
      this.styleObject.color = "black";
      this.styleObject1.background = "black";
      this.styleObject1.color = "white";
      this.styleObject2.background = "white";
      this.styleObject2.color = "black";
      this.styleObject3.background = "white";
      this.styleObject3.color = "black";
    }
    if (this.$store.state.board == 2) {
      this.styleObject.background = "white";
      this.styleObject.color = "black";
      this.styleObject1.background = "white";
      this.styleObject1.color = "black";
      this.styleObject2.background = "black";
      this.styleObject2.color = "white";
      this.styleObject3.background = "white";
      this.styleObject3.color = "black";
    }
  },
};
</script>

<style scoped>
.cptitem {
  width: 900px;
}

.cpt {
  display: flex;
  justify-content: center;
}
.item1 {
  width: 900px;
  border-bottom: 1px solid #6666;
  /* min-width: 850px */
}

.con {
  display: flex;
  justify-content: center;
  height: 100px;
  align-items: center;
}
.item {
  width: 900px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  min-width: 850px;
}
.item1 button {
  width: 166px;
  height: 34px;
  background: white;
  color: black;
  border: 1px solid #6666;
  border-bottom: none;
  cursor: pointer;
}
.item button {
  width: 90px;
  height: 35px;
  background: white;
  border: 1px solid #6666;
  border-radius: 5px;
  cursor: pointer;
  color: #909399;
}
.item button:hover {
  background: #eee;
}
</style>