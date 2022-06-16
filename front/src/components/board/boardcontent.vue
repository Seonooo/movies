<template>
  <div>
    <br />
    <div
      style="
        border: 1px solid #aaaaaa;
        border-radius: 0.5em;
        padding: 30px;
        width: 700px;
        margin-left: auto;
        margin-right: auto;
        margin-top: 50px;
      "
    >
      <el-row style="justify-content: right">
        <el-col :span="24"
          ><div class="grid-content bg-purple" />
          <h1>{{ state.item.btitle }}</h1></el-col
        >
        <el-col :span="7" style="overflow: hidden; white-space: nowrap"
          ><div class="grid-content bg-purple-light" />
          <h5 style="margin-top: 0px">
            글쓴이 : {{ state.item.memberEntityMid }}
            &nbsp;조회수 :
            {{ state.item.bhit }}
          </h5>
          <h5>글쓴 날짜 : {{ state.item.bregdate }}</h5>
          <h5 style="text-align: right" @click="selectmove()">목록으로</h5>

          <h5 style="text-align: right">
            <span
              v-if="state.item.memberEntityMid == state.mid"
              @click="handleUpdate()"
              style="cursor: pointer"
              >수정</span
            ><span
              v-if="state.item.memberEntityMid == state.mid"
              @click="handleDelete()"
              style="cursor: pointer"
              >삭제</span
            >
          </h5>
        </el-col>
      </el-row>

      <hr />

      <div v-if="this.state.img">
        <div v-for="tmp in this.state.img.length" :key="tmp">
          <img :src="this.state.img[tmp - 1]" class="img" />
        </div>
      </div>
      <h3>{{ state.item.bcontent }}</h3>
      <br />
      <div style="text-align: center">
        <button
          style="
            background-color: transparent !important;
            background-image: none !important;
            border-color: transparent;
            border: none;
            color: #ffffff;
          "
          @click="handleLike()"
        >
          <img
            :src="likeicon"
            style="width: 50px; height: 50px; cursor: pointer"
          />
        </button>
        <h4 style="margin-bottom: 0px">좋아요 : {{ state.item.blike }}</h4>
      </div>
      <div v-if="state.prevNext">
        <h5
          @click="handlePrev()"
          v-if="state.prevNext.Prev"
          style="cursor: pointer; float: left"
        >
          이전
        </h5>

        <h5
          style="float: right; cursor: pointer"
          @click="handleNext()"
          v-if="state.prevNext.Next"
        >
          다음
        </h5>
      </div>

      <!-- <el-button
        v-if="state.item.memberEntityMid == state.mid"
        @click="handleDelete()"
        style="margin-right: 10px; float: right"
        type="danger"
        >삭제</el-button
      >

      <el-button
        v-if="state.item.memberEntityMid == state.mid"
        @click="handleUpdate()"
        style="margin-right: 10px; float: right"
        type="success"
        >수정</el-button
      > -->

      <br />
      <br />
      <el-input
        v-model="this.state.comment"
        placeholder="댓글을 남겨보세요"
        style="margin-right: 10px; margin-top: 50px"
        @keyup.enter="handleComment()"
      />

      <div v-for="tmp in this.state.comments" :key="tmp">
        <h3>{{ tmp.memberEntityMid }}</h3>
        <div v-show="tmp.update == false">
          <h4>{{ tmp.ccontent }}</h4>
          <br />
          <el-row>
            <el-col :span="21"
              ><div class="grid-content bg-purple" />
              <h5 style="margin: 0">{{ tmp.cregdate }}</h5></el-col
            >
            <el-col :span="3"
              ><div class="grid-content bg-purple-light" />
              <div v-if="tmp.memberEntityMid == state.mid">
                <el-link style="margin-right: 5px" @click="tmp.update = true"
                  ><h5 style="margin: 0">수정</h5>
                </el-link>
                <el-link @click="handleDeleteComment(tmp.cno)"
                  ><h5 style="margin: 0">삭제</h5>
                </el-link>
              </div>
            </el-col>
          </el-row>
        </div>
        <div v-show="tmp.update == true" style="height: 100px">
          <el-input
            v-model="tmp.ccontent"
            placeholder="댓글을 남겨보세요"
            style="width: 100%; margin-bottom: 10px"
          /><br />
          <el-button
            style="margin-right: 10px; float: right"
            @click="handleUpdateComment(tmp.cno, tmp.ccontent)"
            >등록</el-button
          >
          <el-button
            type="danger"
            style="margin-right: 10px; float: right"
            @click="tmp.update = false"
            >취소</el-button
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted, computed } from "vue";
import { useStore } from "vuex";
import { useRoute, useRouter } from "vue-router";
import moment from "moment-timezone";
import axios from "axios";
import {
  ElSelect,
  ElOption,
  ElPagination,
  ElTableColumn,
  ElButton,
  ElTable,
  ElInput,
  ElForm,
  ElFormItem,
  ElIcon,
  ElRow,
  ElCol,
  ElLink,
  ElUpload,
} from "element-plus";
export default {
  components: {
    ElSelect,
    ElOption,
    ElPagination,
    ElTableColumn,
    ElButton,
    ElTable,
    ElInput,
    ElForm,
    ElFormItem,
    ElIcon,
    ElRow,
    ElCol,
    ElLink,
    ElUpload,
  },

  setup() {
    const route = useRoute();
    const store = useStore();
    const router = useRouter();
    const like = computed(() => store.getters.getLike);
    const likeicon = computed(() => store.getters.getLikeicon);
    const state = reactive({
      no: route.query.no,
      item: [],
      img: [],
      like: false,
      likeicon: require("../../assets/board/likebtncen.png"),
      comment: "",
      mid: "",
    });
    const dateTime = (value) => {
      return moment(value).tz("Asia/Seoul").format("");
    };

    function selectmove() {
      router.push("/boardview");
    }

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/board/selectone?no=${state.no}`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      // console.log(response.data);
      if (response.data.status == 200) {
        state.item = response.data.board;
        for (let i = 0; i < response.data.boardimg.length; i++) {
          state.img[i] = "/TEAMMOVIE/board/image?no=" + response.data.boardimg[i];
        }
        const url2 = `/TEAMMOVIE/api/board/like?no=${state.no}`;
        const headers2 = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response2 = await axios.get(url2, { headers: headers2 });
        if (response2.data.status == 200) {
          store.commit("setLike", true);
          store.commit(
            "setLikeicon",
            require("../../assets/board/likebtn1.png")
          );
        } else {
          store.commit("setLike", false);
          store.commit(
            "setLikeicon",
            require("../../assets/board/likebtncen.png")
          );
        }
      }
    };

    const handlePrev = () => {
      router.push({
        name: "boardcontent",
        query: { no: state.prevNext.Prev.bno },
      });
      state.no = state.prevNext.Prev.bno;
      handleData();
      handleData2();
      handlePrevNext();
    };

    function backmove() {
      router.push("/board");
    }

    const handleNext = () => {
      router.push({
        name: "boardcontent",
        query: { no: state.prevNext.Next.bno },
      });
      state.no = state.prevNext.Next.bno;
      handleData();
      handleData2();
      handlePrevNext();
    };

    const handlePrevNext = async () => {
      const url = `/TEAMMOVIE/api/board/selectPrevNext?no=${state.no}`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        state.prevNext = response.data;
        console.log(state.prevNext);
      }
    };

    const handleData2 = async () => {
      const url = `/TEAMMOVIE/api/board/commentlist?no=${state.no}`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });

      if (response.data.status == 200) {
        state.comments = response.data.comments;
        for (var i = 0; i < response.data.comments.length; i++) {
          state.comments[i].update = false;
        }
      }
    };

    const handleToken = async () => {
      const url = `/TEAMMOVIE/api/member/readtoken`;
      const headers = {
        "Content-Type": "application/json",
        TOKEN: sessionStorage.getItem("TOKEN"),
      };
      const response = await axios.get(url, { headers });

      if (response.data.status == 200) {
        state.mid = response.data.userid;
      }
    };

    const handleDeleteComment = async (cno) => {
      console.log(cno);
      const url = `/TEAMMOVIE/api/board/commentdelete?cno=${cno}`;
      const headers = {
        "Content-Type": "application/json",
        TOKEN: sessionStorage.getItem("TOKEN"),
      };
      const response = await axios.delete(url, { headers });
      console.log(response);
      handleData2();
    };

    const handleUpdateComment = async (cno, ccontent) => {
      if (ccontent.length > 0) {
        const url = `/TEAMMOVIE/api/board/commentupdate`;
        const body = { cno: cno, ccontent: ccontent };
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response = await axios.put(url, body, { headers });
        console.log(response);
      }
      handleData2();
    };

    const handleComment = async () => {
      if (state.comment.length > 0) {
        const url = `/TEAMMOVIE/api/board/commentinsert`;
        const body = {
          ccontent: state.comment,
          boardEntity: { bno: state.no },
        };
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response = await axios.post(url, body, { headers });
        console.log(response);
        state.comment = "";
        handleData2();
      }
    };

    onMounted(() => {
      handleData();
      handleData2();
      handleToken();
      handlePrevNext();
      dateTime;
    });

    const handleDelete = async () => {
      if (confirm("삭제할까요?")) {
        const url = `/TEAMMOVIE/api/board/delete?no=${state.item.bno}`;
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response = await axios.delete(url, { headers });
        if (response.data.status === 200) {
          router.push({ name: "BoardView" });
        }
      } else {
        return false;
      }
    };

    const handleUpdate = () => {
      router.push({ name: "boardupdate", query: { no: state.item.bno } });
    };

    const handleLike = async () => {
      if (like.value == false) {
        const url = `/TEAMMOVIE/api/board/updatelike?no=${state.no}`;
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response = await axios.get(url, { headers });
        if (response.data.status == 200) {
          store.commit("setLike", true);
          store.commit(
            "setLikeicon",
            require("../../assets/board/likebtn1.png")
          );
        }
        handleData();
      } else if (like.value == true) {
        const url = `/TEAMMOVIE/api/board/updatelike?no=${state.no}`;
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response = await axios.get(url, { headers });
        if (response.data.status == 200) {
          store.commit("setLike", false);
          store.commit(
            "setLikeicon",
            require("../../assets/board/likebtncen.png")
          );
        }
        handleData();
      }
    };
    return {
      state,
      handleLike,
      like,
      likeicon,
      handleComment,
      handleDeleteComment,
      handleUpdateComment,
      handleDelete,
      handleUpdate,
      handlePrev,
      handleNext,
      backmove,
      selectmove,
    };
  },
};
</script>

<style  scoped>
h5 {
  margin-bottom: 0px;
}
span {
  margin-left: 10px;
}
.img {
  max-height: 300px;
  max-width: 300px;
  min-height: 100px;
  min-width: 100px;
}
</style>