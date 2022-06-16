<template>
  <div>
    <div class="con" v-if="state.movie">
      <div class="wrap">
        <div class="item">
          <img :src="state.imageurl" style="width: 200px" />
        </div>
        <div class="item1">
          <h2 v-text="state.movie.mtitle"></h2>

          <div class="starwrap">
            <h5 style="width: 60px">rank : {{ state.movie.mrank }}</h5>
            <div style="display: flex; width: 60px">
              <img src="../../assets/movie/star.png" class="star" />
              <h5>{{ state.movie.mgpa }}</h5>
            </div>
            <h6 style="margin: 0px">
              {{ state.movie.filmratingEntity.ffilmrating }}
            </h6>
            <div>
              <div
                style="display: flex; align-items: center; margin-left: 20px"
              >
                <h5 style="margin: 0px">좋아요 : {{ state.movie.mlike }}</h5>
                <img
                  :src="likeicon"
                  style="
                    width: 30px;
                    height: 30px;
                    margin-left: 5px;
                    cursor: pointer;
                  "
                  @click="handleLike()"
                />
              </div>
            </div>
          </div>
          <hr />
          <div>
            <h5 style="display: inline" v-if="state.movie.categories[0]">
              장르:
              {{ state.movie.categories[0].categoryEntity.ccategory }},
            </h5>
            <h5 style="display: inline" v-if="state.movie.categories[1]">
              {{ state.movie.categories[1].categoryEntity.ccategory }}
            </h5>
          </div>
          <h5>감독 : {{ state.movie.mdirector }}</h5>
          <h5>배우 : {{ state.movie.mactor }}</h5>
          <h5 class="aa" v-if="state.movie">
            개봉일: {{ state.movie.mrelease[0] }}
          </h5>
        </div>
      </div>
    </div>

    <div class="con">
      <div class="wrap">
        <div
          class="btnitem"
          v-bind:style="state.styleObject1"
          @click="(state.menu = 1), colorm(1), btnitemcolor()"
        >
          <h3>포스터</h3>
        </div>
        <div
          class="btnitem"
          v-bind:style="state.styleObject2"
          @click="(state.menu = 2), colorm(2), btnitemcolor()"
        >
          <h3>줄거리</h3>
        </div>
        <div
          class="btnitem"
          v-bind:style="state.styleObject3"
          @click="(state.menu = 3), colorm(3), btnitemcolor()"
        >
          <h3>평점</h3>
        </div>
      </div>
    </div>

    <div v-if="state.movie" style="padding: 20px">
      <div>
        <div>
          <Posters1
            v-if="state.menu == 1"
            @handleImageUrl="handleImageUrl()"
          ></Posters1>
          <Story v-if="state.menu == 2"></Story>
          <Gpa v-if="state.menu == 3"></Gpa>
        </div>

        <div>
          <div class="con">
            <div class="wrap" style="align-items: center">
              <div class="reviewwrap">
                <h3 style="margin-top: 0">리뷰달기</h3>
                <div>
                  <textarea
                    type="text"
                    placeholder="내용을 입력해주세요."
                    v-model="state.review.rcontent"
                    class="reviewinp"
                  />
                  <h5 style="text-align: right; margin-right: 20px">
                    {{ state.review.rcontent.length }}/100
                  </h5>
                  <div class="reviewitem">
                    <select v-model="state.review.rgpa">
                      <option disabled selected>평점을 선택해주세요.</option>
                      <option v-for="tmp of 10" :key="tmp" :value="tmp">
                        {{ tmp }}점
                      </option>
                    </select>
                    <button @click="handleInsertReview()">등록</button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div class="con">
            <div class="wrap" style="margin-top: 50px">
              <div style="width: 100%">
                <div class="bb">
                  <h2>리뷰</h2>
                  <select
                    v-model="state.type"
                    @change="handleChange"
                    class="sele"
                  >
                    <option value="1">최신순</option>
                    <option value="2">높은 평점순</option>
                    <option value="3">낮은 평점순</option>
                  </select>
                </div>
                <div v-for="tmp of state.reviews" :key="tmp">
                  <div class="reviewstarcon">
                    <h4>
                      {{ tmp.ticketEntity.memberEntity.mid }}
                    </h4>
                    <p class="ll">|</p>
                    <img src="../../assets/movie/star.png" class="star1" />

                    <h4>{{ tmp.rgpa }}</h4>
                  </div>
                  <div v-if="state.update == false">
                    <h4>{{ tmp.rcontent }}</h4>
                    <h4>
                      {{
                        dateTime(tmp.rregdate)
                          .split("+")[0]
                          .split("T")
                          .join(" ")
                      }}
                    </h4>
                    <h5
                      style="display: inline; cursor: pointer"
                      @click="handleDeleteReview(tmp)"
                    >
                      삭제
                    </h5>
                    <h5
                      style="display: inline; cursor: pointer; margin-left: 5px"
                      @click="state.update = true"
                    >
                      수정
                    </h5>
                  </div>

                  <div v-if="state.update == true" class="updatecon">
                    <div class="con">
                      <div
                        class="wrap"
                        style="align-items: center; margin: 0px"
                      >
                        <div class="reviewwrap">
                          <h3 style="margin-top: 0">리뷰수정</h3>
                          <div>
                            <textarea
                              type="text"
                              placeholder="내용을 입력해주세요."
                              v-model="tmp.rcontent"
                              class="reviewinp"
                              style="height: 100px; width: 95%"
                            />
                            <h5 style="text-align: right; margin-right: 20px">
                              {{ tmp.rcontent.length }}/100
                            </h5>
                            <div class="reviewitem">
                              <button
                                @click="
                                  handleUpdateReview(tmp.rcode, tmp.rcontent)
                                "
                              >
                                수정
                              </button>
                              <button @click="state.update = false">
                                취소
                              </button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive, computed, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import Posters1 from "./Posters.vue";
import Story from "./Story.vue";
import Gpa from "./Gpa.vue";
import { useStore } from "vuex";
import moment from "moment-timezone";
export default {
  components: {
    Posters1,
    Story,
    Gpa,
  },
  setup() {
    const router = useRouter();
    const route = useRoute();
    const store = useStore();
    const like = computed(() => store.getters.getLike);
    const likeicon = computed(() => store.getters.getLikeicon);
    const state = reactive({
      mcode: route.query.mcode,
      menu: 1,
      token: sessionStorage.getItem("TOKEN"),
      review: {
        rcontent: "",
        rgpa: 10,
      },
      page: 1,
      size: 10,
      type: 1,
      update: false,
      styleObject1: {},
      styleObject2: {},
      styleObject3: {},
      color: 1,
      date: [],
      movie: "",
    });

    // const handleReviewChange = () => {

    // };
    const dateTime = (value) => {
      return moment(value).tz("Asia/Seoul").format("");
    };

    const handleChange = () => {
      handleData();
    };
    //btnitem 활성화시 색상
    function btnitemcolor() {
      if (state.color === 1) {
        state.styleObject1.borderBottom = "2px solid black";
        state.styleObject1.color = "black";
        state.styleObject2.borderBottom = "1px solid #6666";
        state.styleObject2.color = "#6666";
        state.styleObject3.borderBottom = "1px solid #6666";
        state.styleObject3.color = "#6666";
      }
      if (state.color === 2) {
        state.styleObject2.borderBottom = "2px solid black";
        state.styleObject2.color = "black";
        state.styleObject1.borderBottom = "1px solid #6666";
        state.styleObject1.color = "#6666";
        state.styleObject3.borderBottom = "1px solid #6666";
        state.styleObject3.color = "#6666";
      }
      if (state.color === 3) {
        state.styleObject3.borderBottom = "2px solid black";
        state.styleObject3.color = "black";
        state.styleObject1.borderBottom = "1px solid #6666";
        state.styleObject1.color = "#6666";
        state.styleObject2.borderBottom = "1px solid #6666";
        state.styleObject2.color = "#6666";
      }
    }

    onMounted(() => {
      btnitemcolor();
      store.commit("cgvnavm", true);
    });

    // 영화 좋아요 클릭시
    const handleLike = async () => {
      if (like.value == false) {
        const url = `/TEAMMOVIE/api/movie/updatelike?mcode=${state.mcode}`;
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
        console.log(response);
        handleData();
      } else if (like.value == true) {
        const url = `/TEAMMOVIE/api/movie/updatelike?mcode=${state.mcode}`;
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
        console.log(response);
        handleData();
      }
    };

    function colorm(e) {
      state.color = e;
    }
    // 영화 한개 정보
    const handleData = async () => {
      console.log(state.mcode);
      const url = `/TEAMMOVIE/api/movie/selectOne?mcode=${state.mcode}`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, { headers });

      if (response.data.status === 200) {
        state.movie = response.data.movie;
        state.genre = response.data.category;
        state.regdate = response.data.release;
        state.movie.mrelease = state.movie.mrelease.split("T");
      }
      const url2 = `/TEAMMOVIE/api/movie/like?mcode=${state.mcode}`;
      const headers2 = {
        "Content-type": "application/json",
        TOKEN: state.token,
      };
      const response2 = await axios.get(url2, { headers: headers2 });
      if (response2.data.status === 200) {
        store.commit("setLike", true);
        store.commit("setLikeicon", require("../../assets/board/likebtn1.png"));
      } else {
        store.commit("setLike", false);
        store.commit(
          "setLikeicon",
          require("../../assets/board/likebtncen.png")
        );
      }
    };

    // 영화 메인포스터
    const handleImageUrl = async () => {
      const url = `/TEAMMOVIE/api/movie/get_main_poster?mcode=${state.mcode}`;
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

    // 리뷰 삭제
    const handleDeleteReview = async (row) => {
      if (confirm("정말 삭제하시겠습니까?")) {
        const url = `/TEAMMOVIE/api/review/delete?rcode=${row.rcode}`;
        const headers = {
          "Content-type": "application/json",
          token: state.token,
        };
        const response = await axios.delete(url, { headers });
        if (response.data.status === 200) {
          console.log(response.data);
          alert("삭제가 완료되었습니다.");
          handleDataReview();
        }
      }
    };

    // 리뷰 등록
    const handleInsertReview = async () => {
      const url = `/TEAMMOVIE/api/review/insert?mcode=${state.mcode}`;
      const headers = {
        "Content-type": "application/json",
        TOKEN: state.token,
      };
      const body = state.review;

      const response = await axios.post(url, body, { headers });
      if (state.review.rcontent.length > 100) {
        alert("100자 이하로 입력해주세요");
        return false;
      }
      if (response.data.status === 200) {
        console.log(response.data);
        alert("등록이 완료되었습니다.");
        state.review.rcontent = "";
        state.review.rgpa = 10;
        handleDataReview();
      } else if (response.data.status === 100) {
        alert("리뷰를 작성한 영화입니다");
        state.review.rcontent = "";
        state.review.rgpa = 10;
      } else if (response.data.status === 0) {
        alert("관람되지 않은 영화 입니다.");
        state.review.rcontent = "";
        state.review.rgpa = 10;
      }
    };

    // 리뷰 수정
    const handleUpdateReview = async (rcode, rcontent) => {
      const url = `/TEAMMOVIE/api/review/update`;
      const headers = {
        "Content-type": "application/json",
        TOKEN: state.token,
      };
      const body = { rcode: rcode, rcontent: rcontent };
      const response = await axios.put(url, body, { headers });

      if (rcontent.length > 100) {
        alert("100자 이하로 입력해주세요");
        return false;
      }
      if (response.data.status === 200) {
        console.log(response.data);
        alert("수정이 완료되었습니다.");
        state.update = false;
        handleDataReview();
      }
    };

    // 목록으로
    const handleList = () => {
      router.push("/movie");
    };

    // 리뷰 리스트
    const handleDataReview = async () => {
      const url = `/TEAMMOVIE/api/review/selectlist?page=${state.page}&size=${state.size}&type=${state.type}&mcode=${state.mcode}`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, {}, { headers });
      if (response.data.status === 200) {
        console.log(response.data);
        state.reviews = response.data.review;
        for (let i = 0; i < state.reviews.length; i++) {
          state.reviews[i].rregdate = state.reviews[i].rregdate;
        }
      }
    };

    onMounted(() => {
      handleData();
      handleImageUrl();
      handleDataReview();
    });

    return {
      state,
      handleList,
      handleInsertReview,
      handleDeleteReview,
      handleUpdateReview,
      btnitemcolor,
      colorm,
      handleLike,
      like,
      likeicon,
      handleChange,
      dateTime,
    };
  },
};
</script>

<style lang="css" scoped>
.bb {
  display: flex;
  justify-content: space-between;
}

.sele {
  border: 1px solid #6666;
  height: 25px;
}

.updatecon textarea {
  display: block;
  height: 300px;
  resize: none;
  width: 1000px;
}
.ll {
  width: 20px;
  text-align: center;
  color: #6666;
  margin: 0px;
}

.reviewstarcon h4 {
  margin: 0;
  margin-top: 7px;
}

.reviewstarcon {
  display: flex;
  align-items: baseline;
  margin-top: 20px;
}

.star1 {
  width: 15px;
  height: 15px;
  margin-bottom: 10px;
  margin-right: 5px;
}

.con {
  display: flex;
  justify-content: center;
}
.wrap {
  display: flex;
  width: 1000px;
  margin-top: 40px;
}
.item {
}
.aa {
  width: 50px;
  overflow: hidden;
  display: inline;
}
.item1 {
  padding-top: 30px;
  padding-left: 30px;
  width: 50%;
}
hr {
  border: 1px solid #eeeeee;
  margin: 20px 0;
}
.star {
  width: 15px;
  height: 15px;
  margin-right: 6px;
}
.starwrap {
  display: flex;
  align-items: center;
  justify-content: left;
  margin-top: 10px;
}
.starwrap h5 {
  margin-top: 1px;
  margin-bottom: 0px;
}
.btnitem {
  flex: 1 auto;
  text-align: center;
  border-bottom: 1px solid #6666;
  color: #6666;
  cursor: pointer;
}
.reviewinp {
  height: 100px;
  width: 95%;
  resize: none;
  border: none;
}
.reviewwrap {
  border: 1px solid #ababab;
  width: 100%;
  padding: 30px 30px;
  padding-bottom: 0px;
  padding-right: 0px;
  margin-top: 50px;
}
.reviewitem {
  display: flex;
  justify-content: right;
  border-top: 1px solid #eaeaea;
}
.reviewitem button {
  background: white;
  border: none;
  border-left: 1px solid #eaeaea;
  cursor: pointer;
  height: 60px;
  width: 60px;
}
.reviewitem select {
  background: white;
  border: none;
  border-left: 1px solid #eaeaea;
  cursor: pointer;
}
option[value="rere"] {
  display: none;
}
</style>