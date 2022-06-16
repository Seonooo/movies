<template>
  <div>
    <div class="profile">
      <div class="profilecon">
        <div style="display: flex">
          <div class="profile_div-profileimg" v-if="state.profileimage">
            <img :src="state.profileimage" />
          </div>
          <div class="profile_div-comment" v-if="state.myprofile">
            <div class="profile_div-comment_div-name">
              <h3>{{ state.myprofile.mname }}님</h3>
            </div>
            <h4>
              {{ state.myprofile.mnickname }}님은
              {{ state.mymembership }}입니다.
            </h4>
          </div>
        </div>
        <div class="middleprofile">
          <div class="middleprofile_div-mycoupon">
            <h3>정보수정</h3>
            <div
              class="middleprofile_div-mycoupon_div-slot"
              style="justify-content: center"
            >
              <button @click="handleMyCgv(3)" class="updatebtn">
                회원정보수정
              </button>
            </div>
            <div
              class="middleprofile_div-mycoupon_div-slot"
              style="justify-content: center"
            >
              <button @click="handleMyCgv(4)" class="updatebtn">
                프로필변경
              </button>
            </div>
            <div
              class="middleprofile_div-mycoupon_div-slot"
              style="justify-content: center"
            >
              <button @click="handleMyCgv(5)" class="updatebtn">
                회원탈퇴
              </button>
            </div>
          </div>
        </div>
      </div>
      <div class="profilecon">
        <div class="profile_div-lately" v-if="state.url">
          <h2 style="text-align: center; margin-bottom: 40px">최근 본 영화</h2>
          <img
            v-for="tmp in state.url.length"
            :key="tmp"
            :src="state.url[tmp - 1]"
          />
          <img v-if="state.url.length < 1" src="@/assets/mycgv/movie.png" />
          <img v-if="state.url.length < 2" src="@/assets/mycgv/movie.png" />
          <img v-if="state.url.length < 3" src="@/assets/mycgv/movie.png" />
          <img v-if="state.url.length < 4" src="@/assets/mycgv/movie.png" />
          <img v-if="state.url.length < 5" src="@/assets/mycgv/movie.png" />
          <img v-if="state.url.length < 6" src="@/assets/mycgv/movie.png" />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import img from "../../assets/mycgv/movie.png";
import axios from "axios";
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import { useStore } from "vuex";

export default {
  setup() {
    const store = useStore();
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
      myprofile: [],
      mymembership: "",
      url: [],
    });

    const replaceByDefault = (e) => {
      console.log("나나ㅏㄴ나");
      e.target.src = img;
    };

    const handleMyCgv = (no) => {
      store.commit("mycgvm", no);
    };

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/member/mypage`;
      const headers = {
        "Content-Type": "application/json",
        TOKEN: state.token,
      };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        state.myprofile = response.data.member;
        state.mymembership = response.data.member.membershipEntity.msmembership;
        state.profileimage =
          "/TEAMMOVIE/member/memberprofile?mid=" + state.myprofile.mid;

        // var bytes, blob;
        // bytes = new Uint8Array(data.blob);
        // blob = new Blob([bytes], {type:'image/bmp'});
        // data['imgSrc'] = URL.createObjectURL(blob);
      }
    };

    const handleMovieData = async () => {
      const url = `/TEAMMOVIE/api/ticket/get_customer_ticket?size=6&page=1&type=1&tscode=3&tno=`;
      const headers = {
        "Content-type": "multipart/form-data",
        TOKEN: state.token,
      };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        console.log(response.data);
        state.tickets = response.data.tickets;
      }
      handleImageUrl();
    };

    const handleImageUrl = async () => {
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
      handleMovieData();
      store.commit("cgvnavm", true);
    });

    return { state, replaceByDefault, handleMyCgv };
  },
};
</script>

<style scoped="scoped">
.profilecon {
  flex: 1 auto;
  width: 500px;
  padding-top: 30px;
}

.updatebtn {
  border: 1px solid #6666;
  background: #f7f7f7;
  border-radius: 5px;
  cursor: pointer;
}

.profile_div-lately {
}

.profile_div-lately img {
  width: 120px;
  margin-left: 35px;
}

.profile_div-comment_div-name small {
  margin-left: 10px;
}

.profile_div-comment_div-name {
  border-bottom: 1px solid #6666;
  height: 30px;
}

* {
  box-sizing: border-box;
}
.middleprofile_div-mycoupon_div-slot button {
  width: 100px;
  height: 20px;
  padding: 0;
  font-size: 11px;
}

.middleprofile_div-mycoupon > h3 {
  text-align: center;
}

.middleprofile_div-mycoupon {
  width: 60%;
  height: 290px;
  box-sizing: border-box;
  /* border-right: 1px solid #6666;
    border-left: 1px solid #6666; */
}

.middleprofile_div-mycoupon_div-slot h5 {
  display: inline;
}

.middleprofile_div-mycoupon_div-slot {
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid #6666;
  align-items: center;
  height: 50px;
}

.middleprofile {
  display: flex;
  justify-content: center;
  border-right: 1px solid #6666;
}

.profile {
  display: flex;
  height: 50%;
  justify-content: left;
  align-items: center;
  margin-bottom: 20px;
}
.profile_div-profileimg {
  width: 100px;
  margin-left: 40px;
}
.profile_div-comment {
  width: 360px;
  padding: 30px;
  border-right: 1px solid #6666;
}
.profile_div-profileimg img {
  width: 100%;
}
.profile_div-comment h3 {
  display: inline;
}
</style>