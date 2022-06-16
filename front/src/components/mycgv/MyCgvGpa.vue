<template>
  <div v-if="state.reviews">
    <div class="con" v-for="(review, idx) of state.reviews" :key="review">
      <div class="wrap" v-if="state.url">
        <img :src="state.url[idx]" class="poster" />
      </div>
      <div class="wrap">
        <h4>
          <span> {{ review.ticketEntity.movieEntity.mtitle }} </span>
        </h4>

        <h5>리뷰번호 : {{ review.rcode }}</h5>
        <h5>리뷰평점 : {{ review.rgpa }}</h5>
        <h5>리뷰내용 : {{ review.rcontent }}</h5>

        <h5>
          리뷰 등록일 :
          {{ dateTime(review.rregdate).split("T").join(" ").split("+")[0] }}
        </h5>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import axios from "axios";
import { onMounted } from "@vue/runtime-core";
import moment from "moment-timezone";
export default {
  setup() {
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
      page: 1,
      size: 10,
      url: [],
    });

    const dateTime = (value) => {
      return moment(value).tz("Asia/Seoul").format("");
    };

    // 메인포스터 가져오기
    const handleImageUrl = async () => {
      for (let i = 0; i < state.reviews.length; i++) {
        const url = `/TEAMMOVIE/api/movie/get_main_poster?mcode=${state.reviews[i].ticketEntity.movieEntity.mcode}`;
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

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/review/selectlist_customer?page=${state.page}&size=${state.size}`;
      const headers = {
        "Content-type": "application/json",
        TOKEN: state.token,
      };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        console.log(response.data);
        state.reviews = response.data.review;
        handleImageUrl();
      }
    };

    onMounted(() => {
      handleData();
    });

    return { state, dateTime };
  },
};
</script>

<style lang="scss" scoped>
.flexbet h5 {
  margin: 0px;
}

.star {
  width: 15px;
  height: 15px;
  margin-right: 6px;
}
.starwrap {
  display: flex;
  align-items: center;
}

.flexbet {
  justify-content: space-between;
  display: flex;
  width: 250px;
}

.con {
  display: flex;
  justify-content: center;
  align-items: center;
  border-bottom: 1px solid #6666;
  margin-top: 20px;
  padding-bottom: 20px;
}
.wrap {
  width: 300px;
}
.poster {
  width: 200px;
  height: 280px;
  border-radius: 5px;
}
h4 {
  margin-top: 0px;
}
</style>