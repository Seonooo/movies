<template>
  <div v-if="state.movies" style="margin-top: 30px">
    <div class="moviecon" style="margin-bottom: 10px">
      <div class="moviewrap">
        <div class="titleitem">
          <h2>무비차트</h2>

          <div>
            <div v-if="state.genres" style="display: inline">
              <h5 style="display: inline">검색:&nbsp;</h5>
              <input
                type="text"
                v-model="state.search"
                @keyup.enter="searchm()"
                class="findinp"
              />
              <h5 style="display: inline">장르:&nbsp;</h5>
              <select v-model="state.genre" @change="handleData" class="select">
                <option
                  v-for="tmp of state.genres"
                  :key="tmp"
                  v-text="tmp.ccategory"
                  :value="tmp.ccode"
                ></option>
              </select>
            </div>
            <div style="display: inline; margin-left: 10px; margin-right: 20px">
              <select v-model="state.size" @change="handleData" class="select">
                <option value="5">5개씩</option>
                <option value="10">10개씩</option>
                <option value="20">20개씩</option>
              </select>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="moviecon">
      <div class="moviewrap">
        <div v-for="(a, i) in state.movies" :key="i" class="movieitem">
          <img :src="state.url[i]" class="poster" @click="handleSelectOne(a)" />
          <h4>{{ a.mtitle }}</h4>

          <div class="starwrap">
            <h5>rank : {{ a.mrank }}</h5>
            <div style="display: flex">
              <img src="../../assets/movie/star.png" class="star" />
              <h5>{{ a.mgpa }}</h5>
            </div>
            <h6 style="margin: 0px">{{ a.filmratingEntity.ffilmrating }}</h6>
          </div>
        </div>
      </div>
    </div>
    <div class="elpag">
      <el-pagination
        layout="prev, pager, next"
        :total="state.total * 10"
        @current-change="handlePage"
      />
    </div>
  </div>
</template>

<script>
import { computed, reactive, watch } from "vue";
import { onMounted } from "vue";
import axios from "axios";
import { useRoute, useRouter } from "vue-router";
import { useStore } from "vuex";
import { ElPagination } from "element-plus";
export default {
  components: {
    ElPagination,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const store = useStore();
    const title = computed(() => store.getters.getSearch);
    const state = reactive({
      page: 1,
      size: 5,
      sort: 1,
      genre: 0,
      mscode: 1,
      title: route.query.title,
      imageurl: [],
      url: [],
      movies: [],
      beforetitle: "",
      pages: 1,
      search: "",
    });

    onMounted(() => {
      store.commit("searchm", "");
      handleData();
      handleGenre();
      store.commit("cgvnavm", true);
    });

    const handlePage = (idx) => {
      console.log(idx);
      state.page = idx;
      handleData();
    };

    function searchm() {
      store.commit("searchm", state.search);
      router.push({ name: "movie", query: { title: state.search } });
    }

    function serch() {
      console.log("serch", title.value);
      if (store.state.search !== "") {
        state.title = store.state.search;
      }
    }

    const handleSelectOne = (row) => {
      router.push({
        name: "movieone",
        query: {
          mcode: row.mcode,
          mgpa: row.mgpa,
        },
      });
    };

    // 영화데이터 가져오기
    const handleData = async () => {
      const url = `/TEAMMOVIE/api/movie/select?size=${state.size}&page=${state.page}&title=${title.value}&mscode=${state.mscode}&genre=${state.genre}&sort=${state.sort}`;
      state.movies = [];

      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, headers);
      console.log(response.data.status);
      if (response.data.status === 200) {
        state.movies = response.data.movies;
        state.pages = response.data.pages;
        state.total = response.data.total;
        console.log(response.data);
      }
      handleImageUrl();
      state.beforetitle = state.title;
    };

    // 장르가져오기
    const handleGenre = async () => {
      const url = `/TEAMMOVIE/api/select/genre`;
      const headers = { "Content-type": "application/json" };
      const response = await axios.get(url, headers);
      if (response.data.status === 200) {
        state.genres = response.data.list;
      }
    };

    // const handleChange = async () => {
    //   handleData();
    //   handleImageUrl();
    //   if (title.value != state.beforetitle) {
    //     router.go();
    //   }
    // };
    watch(title, () => {
      handleData();
    });

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

    return { state, handleSelectOne, handleData, handlePage, searchm };
  },
};
</script>

<style scoped>
.elpag {
  display: flex;
  justify-content: center;
}

.select {
  border: 1px solid #6666;
  border-radius: 10px;
}

.titleitem {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}
.moviecon {
  display: flex;
  justify-content: center;
}
.moviewrap {
  width: 1000px;
  display: flex;
  justify-content: left;
  flex-wrap: wrap;
}
.movieitem {
  width: 185px;
  margin-right: 15px;
  margin-bottom: 30px;
}
.poster {
  width: 184px;
  height: 263px;
  border-radius: 5px;
  cursor: pointer;
}
.movieitem h4 {
  text-align: center;
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
.starwrap h5 {
  margin-top: 1px;
  margin-bottom: 0px;
}
.findinp {
  display: inline;
  width: 110px;
  border: 1px solid #6666;
  border-radius: 15px;
  margin-right: 10px;
}
</style>