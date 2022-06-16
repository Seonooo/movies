<template>
  <div>
    <div class="congu">
      <div class="congu1">
        <h3>MY 예매내역</h3>
        <h4 v-if="state.tickets.length">{{ state.tickets.length }}건</h4>
        <div class="selectdiv">
          <select v-model="state.type" @change="handleChange">
            <option value="0">전체</option>
            <option value="1">상태별</option>
          </select>
          <select
            v-if="state.type == 1"
            v-model="state.tscode"
            @change="handleChange"
          >
            <option value="0">결제취소</option>
            <option value="1">결제대기</option>
            <option value="2">결제완료</option>
            <option value="3">상영완료</option>
          </select>
        </div>
      </div>

      <h5>예매번호로만 티켓을 찾을 수 있으니 반드시 확인 부탁드립니다.</h5>
    </div>

    <div style="border: 1px solid black" v-if="state.tickets">
      <div class="con2" v-for="(ticket, idx) in state.tickets" :key="ticket">
        <div class="item2">
          <h5 style="display: inline; color: gray">예매번호</h5>
          <div></div>
          <h4>
            {{ ticket.tno }}
          </h4>
          <h5 style="color: gray">
            {{ ticket.tregdate.split(".")[0].split("T")[0] }}
            <br />
            {{ ticket.tregdate.split(".")[0].split("T")[1] }}
          </h5>
        </div>
        <div style="width: 80%">
          <div class="item2r">
            <div class="item2a">
              <img :src="state.url[idx]" />
            </div>
            <div class="item2b">
              <h5 style="color: black">{{ ticket.movieEntity.mtitle }}</h5>

              <h5>
                관람극장 <span>{{ ticket.theaterEntity.thcode }} </span>
              </h5>
              <h5>
                관람일시
                <span>{{ dateTime(ticket.tstart).split("T")[0] }} </span>
                <span
                  >{{ dateTime(ticket.tstart).split("T")[1].split("+")[0] }}
                </span>
              </h5>
              <h5>
                티켓상태 <span> {{ ticket.ticketStateEntity.tsstate }}</span>
              </h5>
            </div>
          </div>
          <div style="border-bottom: 1px solid #6666">
            <h5>
              총 결제금액
              <span style="color: #6495ed"
                >{{ ticket.theaterEntity.thprice }}원</span
              >
            </h5>
            <div class="paybtnwrap">
              <button
                v-if="state.tscode == 1"
                @click="handleRemovePayment(idx)"
                class="paybtn"
              >
                결제취소</button
              ><button
                v-if="state.tscode == 1"
                @click="handlePayment(idx)"
                class="paybtn"
              >
                결제하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import { useRoute, useRouter } from "vue-router";
import moment from "moment-timezone";
import axios from "axios";
export default {
  setup() {
    const router = useRouter();
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
      size: 10,
      page: 1,
      type: 0,
      tscode: 0,
      tickets: [],
    });

    const dateTime = (value) => {
      return moment(value).tz("Asia/Seoul").format("");
    };

    // 티켓리스트 - 회원
    const handleData = async () => {
      const url = `/TEAMMOVIE/api/ticket/get_customer_ticket?size=${state.size}&page=${state.page}&type=${state.type}&tscode=${state.tscode}&tno=`;
      const headers = {
        "Content-type": "application/json",
        TOKEN: state.token,
      };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        console.log(response.data);
        state.tickets = response.data.tickets;
      }
      handleImageUrl();
    };

    const handleChange = () => {
      handleData();
    };

    const handlePayment = (idx) => {
      console.log(idx);
      console.log(state.tickets[idx]);
      router.push({ name: "Payment", query: { tno: state.tickets[idx].tno } });
    };

    const handleRemovePayment = async (idx) => {
      console.log(idx);
      console.log(state.tickets[idx].tno);
      if (confirm("결제 취소하시겠습니까?")) {
        const url = `/TEAMMOVIE/api/ticket/delete?tno=${state.tickets[idx].tno}`;
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

    // 메인포스터 가져오기
    const handleImageUrl = async () => {
      state.url = [];
      for (let i = 0; i < state.tickets.length; i++) {
        const url = `/TEAMMOVIE/api/movie/get_main_poster?mcode=${state.tickets[i].movieEntity.mcode}`;
        const headers = { "Content-type": "application/json" };
        const response = await axios.get(url, { headers });
        if (response.data.status === 200) {
          if (response.data.poster.pimageUrl != null) {
            state.url.push(response.data.poster.pimageUrl);
          } else {
            state.url.push(
              "/TEAMMOVIE/movie/poster?pcode=" + response.data.poster.pcode
            );
          }
        }
      }
    };

    onMounted(() => {
      handleData();
    });

    return {
      state,
      handleChange,
      handlePayment,
      handleRemovePayment,
      dateTime,
    };
  },
};
</script>

<style lang="scss" scoped>
.paybtnwrap {
  display: flex;
  justify-content: right;
  margin-bottom: 10px;
}

.paybtn {
  margin-left: 10px;
  background: white;
  border: 1px solid #6666;
  border-radius: 10px;
  cursor: pointer;
}

select {
  border: 1px solid #6666;
  border-radius: 10px;
}

.selectdiv {
  margin-left: 5px;
}
.congu1 {
  display: flex;
  align-items: center;
}

.congu1 h4 {
  margin-left: 5px;
}

.congu1 h5 {
}
.congu1 h3 {
}

.congu h5 {
  margin: 0;
}
.congu h3 {
  margin: 0;
}
.congu {
  display: flex;
  justify-content: space-between;
  height: 50px;
  align-items: center;
}

.item2b h5 {
  color: gray;
}

.speech_div-imgspeech h5 {
  color: gray;
}

span {
  margin-left: 10px;
  color: black;
}

.item2r {
  display: flex;
  align-items: center;
}

.item2b {
  padding-left: 20px;
}

.h5r {
  display: inline;
}

.item2a img {
  width: 50px;
  margin-bottom: 50px;
}

.item2 {
  padding: 20px;
}

.con2 {
  display: flex;
}

.speech_div-imgspeech img {
  width: 50px;
}

.speech {
  display: flex;
  background: #f7f7f7;
  border: 1px solid black;
}
.speech_div-imgspeech {
  flex: 1 auto;
  text-align: center;
  padding-top: 20px;
  border-right: 1px solid #6666;
}
</style>