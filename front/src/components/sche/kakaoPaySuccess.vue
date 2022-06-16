<template>
  <!-- <div v-if="state.kakao">
    <span v-if="state.kakao.amount">{{ state.kakao.amount.total }} </span>
    {{ state.kakao.approved_at }}
    {{ state.kakao.item_name }}
    {{ state.kakao.partner_order_id }}
    {{ state.kakao.partner_user_id }}
  </div> -->

  <div class="con">
    <div style="width: 1000px"><h1>결제완료</h1></div>
  </div>

  <div class="con">
    <div class="wrap" v-if="state.kakao">
      <div style="width: 500px" v-if="state.url">
        <img :src="state.url" class="poster" />
        <h3>{{ state.kakao.item_name }}</h3>
        <div class="spacebet">
          <h5>주문번호:{{ state.kakao.partner_order_id }}</h5>
          <h5>아이디:{{ state.kakao.partner_user_id }}</h5>
          <h5 v-if="state.kakao.amount">
            가격:{{ state.kakao.amount.total }}원
          </h5>

          <h5>
            결제일:{{
              state.kakao.approved_at.split(".")[0].split("T").join("  ")
            }}
          </h5>
        </div>
      </div>
    </div>
  </div>
  <button class="homebtn" @click="homemove()">Home</button>
</template>

<script>
import { reactive, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import axios from "axios";
export default {
  setup() {
    const router = useRouter();
    const route = useRoute();
    const state = reactive({
      pg_token: route.query.pg_token,
      tno: route.query.tno,
      kakao: [],
    });

    function homemove() {
      router.push("/");
    }

    const handleData = async () => {
      console.log(state.pg_token);
      const url = `/TEAMMOVIE/api/kakao/kakaoPaySuccess?tno=${state.tno}&pg_token=${state.pg_token}`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      state.kakao = response.data.info;
      console.log(state.kakao);
      handleImage();
    };

    const handleImage = async () => {
      const url = `/TEAMMOVIE/api/movie/get_main_poster?mcode=${state.kakao.item_code}`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        if (response.data.poster.pimageUrl != null) {
          state.url = response.data.poster.pimageUrl;
          console.log(response.data.poster.pimageUrl);
        } else {
          state.url = "/TEAMMOVIE/movie/poster?pcode=" + response.data.poster.pcode;
          // console.log("/TEAMMOVIE/movie/poster?pcode=" + response.data.poster.pcode);
        }
      }
    };

    const updateTicket = async () => {
      const url = `/TEAMMOVIE/api/ticket/buy_ticket?tno=${state.tno}`;
      const headers = {
        "Content-Type": "application/json",
        TOKEN: sessionStorage.getItem("TOKEN"),
      };
      const body = {};
      const response = await axios.put(url, body, { headers });
      console.log(response);
    };
    onMounted(() => {
      handleData();
      updateTicket();
    });
    return { state, homemove };
  },
};
</script>

<style  scoped>
.con {
  display: flex;
  justify-content: center;
}
.wrap {
  width: 1000px;
  display: flex;
  justify-content: center;
}
.spacebet {
  display: flex;
  justify-content: space-between;
}
.poster {
  margin-left: auto;
  margin-right: auto;
  display: block;
}
h3 {
  text-align: center;
}
h1 {
  text-align: center;
}
.homebtn {
  display: block;
  margin-left: auto;
  margin-right: auto;
  cursor: pointer;
  border: 1px solid #6666;
  width: 60px;
  height: 30px;
  margin-top: 30px;
  border-radius: 5px;
}
</style>