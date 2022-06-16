<template>
  <div v-if="state.movies">
    <div class="backcon">
      <div class="backwrap">
        <div
          class="radibtn"
          @click="(state.move -= 300), slide()"
          style="right: 30px"
        >
          <h4 style="color: #6666">&#62;</h4>
        </div>
        <div
          class="radibtn"
          @click="(state.move += 300), slide()"
          style="left: 30px"
        >
          <h4 style="color: #6666">&#60;</h4>
        </div>
        <div class="flexbet">
          <h2>무비차트</h2>
          <div class="allwatch" @click="allwatchf()"><h5>전체보기 ></h5></div>
        </div>

        <div class="con">
          <div
            class="wrap"
            v-for="(movie, i) in state.movies"
            :key="i"
            :style="state.styleObject"
          >
            <img
              :src="state.url[i]"
              class="poster"
              @mouseenter="state.mouseenter[i] = 1"
            />

            <div
              class="detailwrap"
              v-show="state.mouseenter[i] === 1"
              @mouseleave="state.mouseenter[i] = 0"
            >
              <div
                class="detail"
                style="margin-top: 80px"
                @click="postermove(i)"
              >
                <h5>상세보기</h5>
              </div>
              <div class="flexcenter">
                <div
                  @mouseenter="state.reservebtnhover[i] = 1"
                  @mouseleave="state.reservebtnhover[i] = 0"
                >
                  <div class="detail" style="background: black; cursor: unset">
                    <h5 style="color: white">예매하기</h5>
                  </div>
                  <div class="flexcenter">
                    <div v-show="state.reservebtnhover[i] === 1">
                      <div
                        class="detail"
                        style="margin-top: 0px; background: skyblue"
                        @click="sche1()"
                        :style="state.slideobj"
                      >
                        <h5 style="color: white">일반상영관</h5>
                      </div>
                      <div
                        class="detail"
                        style="margin-top: 0px; background: #ff6957"
                        @click="sche2()"
                      >
                        <h5 style="color: white">커플상영관</h5>
                      </div>
                      <div
                        class="detail"
                        style="margin-top: 0px; background: #ffd700"
                        @click="sche3()"
                      >
                        <h5 style="color: white">프리미엄관</h5>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <h4 class="mtitle">{{ state.movies[i].mtitle }}</h4>
            <div class="underdiv">
              <h5>rank : {{ state.movies[i].mrank }}</h5>
              <div class="starwrap">
                <img src="../../assets/movie/star.png" class="star" />
                <h5>{{ state.movies[i].mgpa }}</h5>
              </div>
              <div class="starwrap">
                <h6 style="margin: 0px">
                  {{ state.movies[i].filmratingEntity.ffilmrating }}
                </h6>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { onMounted } from "vue";
import axios from "axios";
import router from "@/routes/router";
export default {
  setup() {
    const state = reactive({
      styleObject: {},
      move: 0,
      page: 1,
      movies: [],
      url: [],
      mouseenter: [],
      reservebtnhover: [],
      slideobj: {},
    });

    function postermove(i) {
      // router.push(`movieone?mcode=${state.movies[i].mcode}`);

      router.push({
        path: "/movieone",
        query: { mcode: state.movies[i].mcode },
      });
    }
    function sche1() {
      router.push("/sche1");
    }
    function sche2() {
      router.push("/sche2");
    }
    function sche3() {
      router.push("/sche3");
    }
    function allwatchf() {
      router.push("/movie");
    }

    function slide() {
      if (state.move > 0) {
        state.move = 0;
      }
      if (state.move < -(state.movies.length - 4) * 300) {
        state.move = -(state.movies.length - 4) * 300;
      }

      state.styleObject.transform = `translateX(${state.move}px)`;
    }

    // 영화데이터 가져오기
    const handleData = async () => {
      state.movies = [];
      const url = `/TEAMMOVIE/api/movie/select?size=10&page=${state.page}&title=&mscode=1&genre=0&sort=1`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, headers);
      console.log(response.data.status);
      if (response.data.status === 200) {
        state.movies = response.data.movies;
        console.log(state.movies.length);
      }
      handleImageUrl();
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

    return {
      state,
      slide,
      handleImageUrl,
      handleData,
      postermove,
      sche1,
      sche2,
      sche3,
      allwatchf,
    };
  },
};
</script>

<style lang="scss" scoped>
.allwatch :hover {
  background: #6666;
  color: white;
  border-radius: 30px;
}

.allwatch h5 {
  margin: 0px;
  text-align: center;
}

.allwatch {
  width: 100px;
  height: 30px;
  border: 1px solid #6666;
  background: white;
  line-height: 30px;
  border-radius: 30px;
  color: gray;
  cursor: pointer;
}

.flexbet {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0px 50px;
}

.flexcenter {
  display: flex;
  justify-content: center;
}

.detailwrap {
  position: absolute;
  background-color: rgba(0, 0, 0, 0.4);
  top: 0px;
  left: 50px;
  height: 250px;
  width: 200px;
  border-radius: 10px;
}

.detail h5 {
  text-align: center;
  color: gray;
  margin: 0px;
}

.detail {
  width: 100px;
  padding: 10px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 5px;
  cursor: pointer;
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-top: 10px;
}

.radibtn {
  width: 40px;
  height: 40px;
  background: rgba(255, 255, 255, 0.8);
  text-align: center;
  line-height: 1px;
  border-radius: 50%;
  box-shadow: 1px 1px 10px 1px #6666;
  cursor: pointer;
  position: absolute;
  top: 230px;
  z-index: 10;
}

.backwrap {
  width: 1200px;
  position: relative;
  overflow: hidden;
}
.backcon {
  width: 100%;
  height: 500px;
  background: white;
  display: flex;
  justify-content: center;
  padding-top: 100px;
}

.logo {
  width: 105px;
  height: 40px;
  display: block;
  margin-left: auto;
  margin-right: auto;
}

.con {
  display: flex;
  position: absolute;
  top: 100px;
}
.wrap {
  background: white;
  flex: 0 0 300px;
  transition: all 1s;

  box-sizing: border-box;
  position: relative;
}

.poster {
  width: 200px;
  height: 250px;
  display: block;
  margin-left: auto;
  margin-right: auto;
  border-radius: 10px;
}
.underdiv {
  display: flex;
  justify-content: space-evenly;
}
.star {
  width: 15px;
  height: 15px;
  margin-right: 6px;
}
.starwrap {
  display: flex;
  align-items: center;
  justify-content: space-evenly;
}
.mtitle {
  text-align: center;
  margin-bottom: 0px;
}
</style>