<template>
  <div class="con" style="margin-top: 50px">
    <div class="wrap">
      <h1 v-if="state.kakao.ticketStateEntity">
        {{ state.kakao.ticketStateEntity.tsstate }}
      </h1>
    </div>
  </div>

  <div class="con">
    <div class="wrap">
      <div class="item">
        <div>
          <img
            :src="state.imageurl"
            style="width: 400px; border-radius: 20px"
          />
        </div>
        <div class="item1">
          <img src="../../assets/imgcon/team_movie3.png" class="logo" />
          <hr class="hr1" />
          <h2 v-if="state.kakao.movieEntity">
            {{ state.kakao.movieEntity.mtitle }}
          </h2>
          <h5 v-if="state.kakao.theaterEntity">
            가격: {{ state.kakao.theaterEntity.thprice }}원
          </h5>
          <h5 v-if="state.kakao.movieEntity">
            {{ state.kakao.theaterEntity.thcontent }}/{{
              state.kakao.movieEntity.filmratingEntity.ffilmrating
            }}
          </h5>

          <h5 v-if="state.kakao.tstart">
            {{ state.kakao.tstart.split(".")[0].split("T")[0] }}&nbsp;{{
              state.kakao.tstart.split(".")[0].split("T")[1]
            }}~{{ state.kakao.tend.split(".")[0].split("T")[0] }}&nbsp;{{
              state.kakao.tend.split(".")[0].split("T")[1]
            }}
          </h5>

          <hr class="hr2" />
          <div class="flexbetween">
            <button class="paybtn" @click="handleDelete()">결제취소</button>
            <button class="paybtn"><a :href="state.url">결제하기</a></button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import axios from "axios";
import { useRouter, useRoute } from "vue-router";
export default {
  setup() {
    const router = useRouter();
    const route = useRoute();
    const state = reactive({
      ticket: "",
      url: "",
      kakao: [],
      tno: route.query.tno,
    });
    const handleData = async () => {
      if (state.tno === undefined) {
        console.log("티켓번호를 찾을 수 없음");
        const url = `/TEAMMOVIE/api/ticket/get_customer_ticket?tscode=1&type=1`;
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response = await axios.get(url, { headers });
        console.log("handleData.response", response.data);
        state.kakao = response.data.tickets[0];
        console.log("handleData", state.kakao);
        console.log("handleData.response", response.data);

        if (response.data.status === 200) {
          state.ticket = state.kakao;
          console.log(state.ticket.tno);
          handlePay();
          handleImageUrl();
        }
      } else {
        console.log("티켓번호가 있음");
        const url = `/TEAMMOVIE/api/ticket/get_customer_ticket?tno=${state.tno}`;
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response = await axios.get(url, { headers });
        console.log("handleData.response", response.data);
        state.kakao = response.data.ticket;
        console.log("handleData", state.kakao);
        console.log("handleData.response", response.data);

        if (response.data.status === 200) {
          state.ticket = state.kakao;
          console.log(state.ticket.tno);
          handlePay();
          handleImageUrl();
        }
      }
    };
    const handlePay = async () => {
      const url = `/TEAMMOVIE/api/kakao/kakaoPay?tno=${state.ticket.tno}`;
      const headers = { "Content-Type": "application/json" };
      const response = await axios.post(url, { headers });
      // console.log(response.data);
      if (response.data.status === 200) {
        state.url = response.data.url;
        // console.log(state.url);
      }
    };
    // 결제취소
    const handleDelete = async () => {
      if (confirm("결제 취소하시겠습니까?")) {
        const url = `/TEAMMOVIE/api/ticket/delete?tno=${state.ticket.tno}`;
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const body = {};
        const response = await axios.put(url, body, { headers });
        console.log(response.data);
        if (response.data.status === 200) {
          router.push({ name: "movie" });
        }
      } else {
        return false;
      }
    };

    // 영화 메인포스터
    const handleImageUrl = async () => {
      console.log(state.ticket.movieEntity.mcode);
      const url = `/TEAMMOVIE/api/movie/get_main_poster?mcode=${state.ticket.movieEntity.mcode}`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, { headers });
      console.log(response.data);
      if (response.data.status === 200) {
        if (response.data.poster.pimageUrl != null) {
          state.imageurl = response.data.poster.pimageUrl;
        } else {
          state.imageurl =
            "/TEAMMOVIE/movie/poster?pcode=" + response.data.poster.pcode;
        }
      }
      console.log(response.data);
    };

    onMounted(() => {
      handleData();
      console.log("tno", state.tno);
    });
    return {
      state,
      handlePay,
      handleImageUrl,
      handleDelete,
    };
  },
};
</script>

<style scoped>
.flexbetween {
  justify-content: space-between;
  display: flex;
}

button {
  cursor: pointer;
}

.bacode {
  width: 400px;
}

.hr2 {
  border: 1px solid black;
  margin: 20px 0px;
  margin-bottom: 40px;
}

.hr1 {
  border: 1px solid black;
  margin: 30px 0px;
}

.logo {
  width: 90px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}
.con {
  display: flex;
  justify-content: center;
}
.wrap {
  width: 1000px;
}
.item {
  display: flex;
  justify-content: space-between;
}
.item1 {
  width: 400px;
  border-radius: 20px;
  border: 2px solid black;
  padding: 30px;
  box-sizing: border-box;
  padding-top: 30px;
}
h2 {
  margin-top: 0px;
  color: black;
}
.paybtn {
  margin-left: auto;
  margin-right: auto;
  background: white;
  border: 1px solid #6666;
  border-radius: 5px;
  width: 80px;
  height: 40px;
}
.paybtn a {
  text-decoration-line: none;
  color: black;
}
h5 {
  margin: 50px 0;
  color: black;
}
</style>