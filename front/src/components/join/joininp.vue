<template>
  <div class="gencon" v-show="$store.state.genre == 1">
    <div class="genitem">
      <button class="xx" @click="$store.state.genre = 0">X</button>
      <div class="gencheck">
        <p v-for="(a, i) in category" :key="i" class="aa">
          {{ a.ccategory }}
          <input type="checkbox" :value="`${i}`" v-model="categoryCode" />
        </p>
      </div>

      <button class="genitembtn" @click="$store.state.genre = 0; genrechm()" >제출</button>
    </div>
  </div>
  <div class="con">
    <div class="item">
      <div class="incon">
        <div class="initem">
         
          <img src="../../assets/poster.jpg" />
    
        </div>
        <div class="initem1">
         <h2 style="margin-left:20px">회원가입</h2> 
          <input
            type="text"
            placeholder="아이디를 입력해주세요."
            class="regular_ex"
            v-model="mid"
            @keyup="idcheck()"
          />
          <div class="checksmall">
            <small>{{ checksmall }}</small>
          </div>

          <input
            type="password"
            placeholder="비밀번호를 입력해주세요."
            class="regular_ex"
            v-model="mpw"
          />

          <input
            type="password"
            placeholder="비밀번호를 재입력해주세요."
            class="regular_ex"
            v-model="mpwc"
            @keyup="pwcheck()"
          />
          <div class="checksmall">
            <small>{{ checkpwsmall }}</small>
          </div>
          <input
            type="text"
            placeholder="이름을 입력해주세요."
            class="regular_ex"
            v-model="mname"
          />

          <input
            type="email"
            placeholder="이메일을 입력해주세요."
            class="regular_ex"
            v-model="memail"
            style="width:250px;"
          />
          <div style="display: inline; margin-left:-20px">
        <h5 style="display: inline"> @ </h5> 
        <select>
          <option>gmail</option>
          <option>naver</option>
          <option>daum</option>
        </select>
         <h5 style="display: inline">.com</h5> 
      </div>
          <input
            type="email"
            placeholder="닉네임을 입력해주세요."
            class="regular_ex"
            v-model="mnickname"
          />
          <input
            type="date"
            placeholder="생일을 입력해주세요."
            class="regular_ex"
            v-model="mbirth"
          />
          <input
            type="text"
            placeholder="휴대전화번호을 입력해주세요. (ex 010-xxxx-xxxx)"
            class="regular_ex"
            v-model="mphone"
            @keyup="telcheck()"
          />
          <div class="checksmall">
            <small>{{ checktelsmall }}</small>
          </div>
          <input
            type=""
            placeholder="지역을 입력해주세요."
            class="regular_ex"
            v-model="maddr"
          />

          <div class="incheck">
            <p>성별</p>
            <span>남:</span>
            <input type="radio" @click="mgender = 'male'" name="gender" />
            <span>여:</span>
            <input type="radio" @click="mgender = 'female'" name="gender" />
          </div>

          <div class="genre">
            <p>
              선호장르 <button @click="$store.state.genre = 1">체크</button>
              <h2 class="genrex" v-show="genrech==false">{{ genrex }} </h2>
              <h2 class="genrev" v-show="genrech==true">{{ genrev }} </h2>
            </p>
          </div>
          <button class="genbtn"><p @click="mpo()">가입</p></button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      mid: "",
      mpw: "",
      mpwc: "",
      mname: "",
      memail: "",
      mphone: "",
      mrole: "customer",
      maddr: "",
      mnickname: "",
      mbirth: 0,
      mgender: "",
      mscode: "BVIP",
      categoryCode: [],
      idc: false,
      checksmall: "",
      checkpwsmall: "",
      checktelsmall: "",
      genrex: "X",
      genrev: "V",
      genrech: false,
      checkid: 0,
      checkpw: 0,
      checktel: 0,
    };
  },
  created() {
    if (this.$store.state.joincli === 1) {
      this.$store.state.joincli = 0;
    }

    axios.get("/TEAMMOVIE/api/member/category").then((e) => {
      console.log(e);
      this.category = e.data.category;
    });
  },

  watch: {},

  methods: {
    genrechm() {
      if (this.categoryCode.length == 0) {
        this.genrech = false;
      } else {
        this.genrech = true;
      }
    },

    idcheck() {
      var regExp = /^[a-z]+[a-z0-9]{5,19}$/g; //영문자로 시작하는 영문자 또는 숫자 6~20자
      if (!regExp.test(this.mid)) {
        this.checksmall = "영문자로 시작하는 영문자 또는 숫자 6~20자";
        return false;
      }
      const url = "/TEAMMOVIE/api/member/memberid";
      const body = {
        mid: this.mid,
      };
      axios.post(url, body).then((e) => {
        if (e.data.status === 0) {
          this.checksmall = "아이디중복";
          return false;
        } else {
          this.checksmall = "";
          this.idc = true;
          this.checkid = 1;
        }
      });
    },

    pwcheck() {
      var regExppw = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$/;

      if (this.mpw !== this.mpwc) {
        this.checkpwsmall = "비밀번호 다름";
        return false;
      }
      if (!regExppw.test(this.mpw)) {
        this.checkpwsmall = "비밀번호 8 ~ 16자 영문, 숫자 조합";
        return false;
      } else {
        this.checkpwsmall = "";
        this.checkpw = 1;
      }
    },

    telcheck() {
      var regExptel = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;

      if (!regExptel.test(this.mphone)) {
        this.checktelsmall = "번호를 정확히 입력해주세요";
        return false;
      } else {
        this.checktelsmall = "";
        this.checktel = 1;
      }
    },

    mpo() {
      if (this.checkid === 1) {
        if (this.checkpw === 1) {
          if (this.checktel === 1) {
            const url = "/TEAMMOVIE/api/member/join";
            const body = {
              mid: this.mid,
              mpw: this.mpwc,
              mname: this.mname,
              memail: this.memail,
              mphone: this.mphone,
              mrole: this.mrole,
              maddr: this.maddr,
              mnickname: this.mnickname,
              mbirth: this.mbirth,
              mgender: this.mgender,
              mscode: this.mscode,
              categoryCode: this.categoryCode,
            };

            axios.post(url, body).then((e) => {
              console.log(e);
              if (e.data.status === 200) {
                alert("가입완료, 다시 로그인해주세요.");
                this.$router.push("/");
              }
            });
          } else {
            alert("전화번호 확인필요");
            return false;
          }
        } else {
          alert("비밀번호 확인필요");
          return false;
        }
      } else {
        alert("아이디 확인필요");
        return false;
      }
    },
  },
};
</script>

<style scoped>
.initem img {
  width: 400px;
}

select {
  height: 32px;
  border: 1px solid #6666;
}

.genrev {
  display: inline;
  margin-left: 10px;
  color: green;
}

.genrex {
  display: inline;
  margin-left: 10px;
  color: crimson;
}

.checksmall {
  margin-left: 20px;
  color: #ee6900;
}

button {
  display: inline;
  color: white;
  background: #333333;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  padding: 7px;
}

.xx {
  display: block;
  margin-left: auto;
  margin-bottom: 20px;
  border: none;
  background: white;
  cursor: pointer;
  color: black;
}

.aa {
  flex: 1 auto;
  text-align: center;
}

.gencheck {
  display: flex;
  flex-wrap: wrap;
}

.genitembtn {
  display: block;
  margin-left: auto;
  margin-right: auto;
  color: white;
  background: #333333;
  border: none;
  border-radius: 5px;
  width: 213px;
  cursor: pointer;
  margin-top: 50px;
  padding: 10px;
}

.gencon {
  display: flex;
  /* justify-content: center; */
  /* background: skyblue; */
  height: 70vh;
  position: absolute;
  left: 500px;
}
.genitem {
  background: white;
  width: 500px;
  margin-top: 50px;
  margin-bottom: 50px;
  box-shadow: 7px 14px 42px 3px rgba(163, 174, 184, 0.7);
  padding: 20px;
}

.con {
  display: flex;
  border-top: 1px solid black;
  justify-content: center;
  width: 1060px;
  margin-top: 100px;
}
.item {
  background: #f8f8f8;
  width: 100%;
  padding: 50px;
}
.incon {
  display: flex;
  justify-content: space-between;
}
.initem {
  flex: 0 auto;
  width: 450px;
}
.initem1 {
  flex: 0 auto;
  width: 470px;
}
.regular_ex {
  display: inline;
  margin: 10px 20px;
  width: 400px;
  padding: 5px;
  color: #555;
  border: 1px solid #dddddd;
}
.genbtn {
  color: white;
  background: #333333;
  border: none;
  border-radius: 5px;
  width: 410px;
  margin-left: 20px;
  cursor: pointer;
}
.incheck span {
  display: inline;
  color: #333333;
}
.incheck p {
  display: inline;
  color: #333333;
  margin-left: 20px;
  margin-right: 10px;
}
.incheck {
  margin-bottom: 10px;
}

.genre span {
  display: inline;
  color: #333333;
}
.genre p {
  display: inline;
  color: #333333;
  margin-left: 20px;
  margin-right: 10px;
}
.genre {
  margin-bottom: 10px;
}
.initem1 button {
  display: inline;
}
</style>