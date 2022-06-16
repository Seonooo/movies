<template>
  <div>
    <div class="speech">
      <div class="speech_div-imgspeech">
        <div @click="handleReser(1)">
          <img src="../../assets/mycgv/egg.png" />
          <h3>기대되는 영화</h3>
          <h5>보고 싶은 영화들을 미리<br />담아두고 싶다면?</h5>
        </div>
      </div>
      <div class="speech_div-imgspeech">
        <div @click="handleReser(2)">
          <img src="../../assets/mycgv/movie.png" />
          <h3>내가 본 영화</h3>
          <h5>관람한 영화들을 한번에<br />모아 보고 싶다면?</h5>
        </div>
      </div>
      <div class="speech_div-imgspeech">
        <div @click="handleReser(3)">
          <img src="../../assets/mycgv/star.png" />
          <h3>내가 쓴 평점</h3>
          <h5>관람 후 내 감상평을 적어<br />추억하고 싶다면?</h5>
        </div>
      </div>
    </div>
    <div>
      <MyCgvMovieLike v-if="state.reser == 1"></MyCgvMovieLike>
      <MyCgvOldMovie v-if="state.reser == 2"></MyCgvOldMovie>
      <MyCgvGpa v-if="state.reser == 3"></MyCgvGpa>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import MyCgvOldMovie from "./MyCgvOldMovie.vue";
import MyCgvGpa from "./MyCgvGpa.vue";
import MyCgvMovieLike from "./MyCgvMovieLike.vue";
export default {
  setup() {
    const state = reactive({
      reser: 1,
    });

    const handleReser = (no) => {
      state.reser = no;
      console.log(state.reser);
    };

    return { state, handleReser };
  },
  components: { MyCgvOldMovie, MyCgvGpa, MyCgvMovieLike },
};
</script>

<style scoped>
.congu1 h5 {
  display: inline;
}
.congu1 h3 {
  display: inline;
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
  cursor: pointer;
}
</style>