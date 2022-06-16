<template>
  <div v-if="state.movies">
    <div class="con" v-for="(movie, idx) in state.movies" :key="movie">
      <div class="wrap">
        <img :src="state.url[idx]" class="poster" />
      </div>
      <div class="wrap">
        <h4>
          <span>{{ movie.mtitle }} </span>
        </h4>
        <div class="flexbet">
          <h5>rank : {{ movie.mrank }}</h5>
          <div class="starwrap">
            <img src="../../assets/movie/star.png" class="star" />
            <h5>평점 : {{ movie.mgpa }}</h5>
          </div>
          <div class="starwrap">
            <img src="../../assets/board/likebtn1.png" class="star" />
            <h5>좋아요 : {{ movie.mlike }}</h5>
          </div>
        </div>
        <h5>{{ movie.filmratingEntity.ffilmrating }}</h5>
        <h5>
          감독 : <span>{{ movie.mdirector }}</span>
        </h5>
        <h5>
          배우 : <span>{{ movie.mactor }}</span>
        </h5>

        <h5>
          상영시간 <span>{{ movie.mtime }}분 </span>
        </h5>

        <!-- <h5>code : {{ movie.mcode }}</h5> -->

        <h5>
          {{ dateTime(movie.mrelease).split("T")[0] }}~{{
            dateTime(movie.mdeadline).split("T")[0]
          }}
        </h5>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import axios from "axios";
import moment from "moment-timezone";
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
      size: 10,
      page: 1,
      url: [],
    });

    const dateTime = (value) => {
      return moment(value).tz("Asia/Seoul").format("");
    };

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/member/movies_like?size=${state.size}&page=${state.page}`;
      const headers = {
        "Cotent-type": "application/json",
        TOKEN: state.token,
      };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        state.movies = response.data.movies;
        console.log(state.movies);
        handleImageUrl();
      }
    };

    // 메인포스터 가져오기
    const handleImageUrl = async () => {
      state.url = [];
      for (let i = 0; i < state.movies.length; i++) {
        const url = `/TEAMMOVIE/api/movie/get_main_poster?mcode=${state.movies[i].mcode}`;
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

    return { state, dateTime };
  },
};
</script>

<style scoped>
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