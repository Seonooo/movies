<template>
  <div class="logincon">
    <div class="loginwrap">
      <div>
        <h4 style="margin-top: 0px">아이디</h4>
        <input type="text" v-model="state.mid" />
        <h4>비밀번호</h4>
        <input type="password" v-model="state.mpw" @keyup.enter="handleLogin" />
        <div style="margin-top: 60px">
          <button @click="handleLogin">
            <h4 style="margin: 0px">Login</h4>
          </button>
          <h5 @click="handleModal(1)" style="cursor: pointer">아이디 찾기 ></h5>
          <h5 @click="handleModal(2)" style="cursor: pointer">
            비밀번호 찾기 >
          </h5>
        </div>
      </div>
      <div class="loginitem1">
        <video class="videobox" autoplay muted>
          <source src="../assets/video/login.mp4" type="video/mp4" />
        </video>
      </div>
    </div>
  </div>

  <div class="modal" v-show="id.idModal === 1">
    <div class="wrap">
      <div class="item">
        <div class="item_topdiv">
          <div class="flexbetween">
            <h3 style="margin: 0px; color: white">아이디찾기</h3>
            <h3
              style="margin: 0px; color: white; cursor: pointer"
              @click="id.idModal = 0"
            >
              X
            </h3>
          </div>
        </div>
        <div class="item_underdiv">
          <div style="margin-bottom: 150px">
            <h3 style="margin-bottom: 10px">아이디를 찾기 위해</h3>
            <h3 style="margin-bottom: 10px">이름,연락처를 입력해주세요</h3>

            <input
              type="text"
              placeholder="이름을 입력해주세요"
              v-model="id.mname"
              class="wrap_input"
            />

            <input
              type="text"
              placeholder="연락처를 입력해주세요"
              v-model="id.mphone"
              class="wrap_input"
            />

            <button
              @click="handleFindId"
              style="margin-left: auto; display: block"
            >
              아이디 찾기
            </button>
          </div>
          <div><img src="../assets/home/homead.jpg" class="ad" /></div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal" v-show="pw.pwModal === 1">
    <div class="wrap">
      <div class="item">
        <div class="item_topdiv">
          <div class="flexbetween">
            <h3 style="margin: 0px; color: white">비밀번호찾기</h3>
            <h3
              style="margin: 0px; color: white; cursor: pointer"
              @click="pw.pwModal = 0"
            >
              X
            </h3>
          </div>
        </div>
        <div class="item_underdiv">
          <div style="margin-bottom: 80px">
            <h3 style="margin-top: 10px; margin-bottom: 10px">
              비밀번호를 찾기 위해
            </h3>
            <h3 style="margin-bottom: 10px">
              아이디,이름,연락처를 입력해주세요
            </h3>

            <input
              type="text"
              placeholder="아이디를 입력해주세요"
              v-model="pw.mid"
              class="wrap_input"
            />

            <input
              type="text"
              placeholder="이름을 입력해주세요"
              v-model="pw.mname"
              class="wrap_input"
            />

            <input
              type="text"
              placeholder="연락처를 입력해주세요"
              v-model="pw.mphone"
              class="wrap_input"
            />

            <button
              @click="handleFindPw"
              style="margin-left: auto; display: block"
            >
              비밀번호 찾기
            </button>
          </div>
          <div><img src="../assets/home/homead.jpg" class="ad" /></div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal" v-show="id.idModal === 2">
    <div class="wrap">
      <div class="item">
        <div class="item_topdiv">
          <div class="flexbetween">
            <h3 style="margin: 0px; color: white">아이디찾기</h3>
            <h3
              style="margin: 0px; color: white; cursor: pointer"
              @click="id.idModal = 0"
            >
              X
            </h3>
          </div>
        </div>
        <div class="item_underdiv">
          <div style="margin-bottom: 150px">
            <h2 style="display: inline">아이디:</h2>

            <h2 style="display: inline" v-text="id.userId"></h2>
          </div>
          <div><img src="../assets/home/homead.jpg" class="ad" /></div>
        </div>
      </div>
    </div>
  </div>

  <div class="modal" v-show="pw.pwModal === 2">
    <div class="wrap">
      <div class="item">
        <div class="item_topdiv">
          <div class="flexbetween">
            <h3 style="margin: 0px; color: white">아이디찾기</h3>
            <h3
              style="margin: 0px; color: white; cursor: pointer"
              @click="id.idModal = 0"
            >
              X
            </h3>
          </div>
        </div>
        <div class="item_underdiv">
          <div style="margin-bottom: 150px">
            <h2 style="margin-bottom: 10px">password</h2>

            <input
              type="text"
              placeholder="새 비밀번호를 입력해주세요"
              v-model="pw.mpw"
            />
            <div class="flexbetween">
              <button @click="pw.pwModal = 0">나가기</button>
              <button @click="handleUpdatePw">변경</button>
            </div>
          </div>
          <div><img src="../assets/home/homead.jpg" class="ad" /></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
import { reactive, onMounted } from "vue";
export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
      mid: "",
      mpw: "",
    });

    const id = reactive({
      mid: "",
      mname: "",
      mphone: "",
      idModal: 0,
      userId: "",
    });
    const pw = reactive({
      mid: "",
      mname: "",
      mphone: "",
      mpw: "",
      pwModal: 0,
    });

    function logincheck() {
      if (sessionStorage.getItem("TOKEN")) {
        router.push("mycgv");
      } else {
        router.push("login");
      }
    }
    logincheck();

    const handleModal = (no) => {
      if (no === 1) {
        if (id.idModal === 0) {
          id.idModal = 1;
          pw.pwModal = 0;
        } else {
          id.idModal = 0;
        }
      } else if (no === 2) {
        if (pw.pwModal === 0) {
          pw.pwModal = 1;
          id.idModal = 0;
        } else {
          pw.pwModal = 0;
        }
      }
    };

    const handleFindPw = async () => {
      if (pw.mid.length < 1) {
        alert("아이디를 입력해주세요");
        return false;
      } else if (pw.mname.length < 1) {
        alert("이름을 입력해주세요");
        return false;
      } else if (pw.mphone.length < 1) {
        alert("연락처를 입력해주세요");
        return false;
      } else {
        const url = `/TEAMMOVIE/api/member/find_pw_chk`;
        const headers = { "Content-type": "application/json" };
        const body = {
          mid: pw.mid,
          mname: pw.mname,
          mphone: pw.mphone,
        };
        const response = await axios.post(url, body, { headers });
        if (response.data.status === 200) {
          pw.pwModal = 2;
        } else {
          alert("정보가 일치하지 않습니다.");
        }
      }
    };

    const handleUpdatePw = async () => {
      if (pw.mpw.length < 1) {
        alert("암호를 입력해주세요");
        return false;
      } else {
        const url = `/TEAMMOVIE/api/member/update_pw`;
        const headers = { "Content-type": "application/json" };
        const body = {
          mid: pw.mid,
          mpw: pw.mpw,
        };
        const response = await axios.put(url, body, { headers });
        if (response.data.status === 200) {
          pw.pwModal = 0;

          alert("변경이 완료되었습니다.");
        }
      }
    };

    const handleFindId = async () => {
      if (id.mname.length < 1) {
        alert("이름을 입력해주세요");
        return false;
      } else if (id.mphone.length < 1) {
        alert("연락처를 입력해주세요");
        return false;
      } else {
        const url = `/TEAMMOVIE/api/member/find_id`;
        const headers = {
          "Content-type": "application/json",
        };
        const body = {
          mname: id.mname,
          mphone: id.mphone,
        };
        const response = await axios.post(url, body, { headers });
        if (response.data.status === 200) {
          console.log(response.data);
          id.idModal = 2;
          id.userId = response.data.mid;
        } else {
          alert("등록된 아이디가 없습니다.");
        }
      }
    };

    onMounted(() => {
      store.commit("cgvnavm", true);
    });

    const handleLogin = async () => {
      const url = `/TEAMMOVIE/api/member/login`;
      const body = {
        mid: state.mid,
        mpw: state.mpw,
      };
      const headers = {
        "Content-type": "application/json",
      };
      const response = await axios.post(url, body, { headers });
      if (response.data.status === 200) {
        sessionStorage.setItem("TOKEN", response.data.token);
        router.push("/");
        alert("로그인완료");
        store.commit("setLogged", true);
        sessionStorage.setItem("ID", state.mid);
      } else {
        store.commit("setLogged", false);
        alert("실패");
      }
    };
    return {
      state,
      id,
      pw,
      handleLogin,
      handleModal,
      handleFindId,
      handleFindPw,
      handleUpdatePw,
      logincheck,
    };
  },
};
</script>

<style scoped>
.logincon {
  display: flex;
  justify-content: center;
}
.loginwrap {
  width: 1000px;
  display: flex;
  justify-content: space-between;
}

.ad {
  width: 320px;
  height: 370px;
}

.item_underdiv h3 {
  margin-bottom: 5px;
  margin-top: 10px;
}
.item_underdiv input {
  width: 350px;
  margin-bottom: 30px;
}

.item_underdiv {
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  box-sizing: border-box;
  padding: 0px 30px;
  height: 85%;
}

.item_topdiv {
  background: #587387;
  width: 100%;
  height: 50px;
  padding: 15px;
  box-sizing: border-box;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
}

.item {
  background: white;
  width: 800px;
  height: 500px;
  margin-left: auto;
  margin-right: auto;
  border-radius: 10px;
}

.wrap_input {
  display: block;
  margin-left: auto;
  margin-right: auto;
  margin-bottom: 50px;
}

.wrap {
  width: 1000px;
  padding-top: 150px;
}

.modal {
  width: 100%;
  height: 170%;
  background-color: black;
  background-color: rgba(0, 0, 0, 0.5);
  position: absolute;
  top: 0px;
  right: 0px;
  z-index: 100;
  display: flex;
  justify-content: center;
}

h1 {
  margin-top: 0px;
  font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
}

.videobox {
  width: 550px;
}

.logincon {
  display: flex;
  height: 60vh;
  padding-top: 100px;
}
.loginitem {
  background: white;
  padding-left: 180px;
  min-width: 500px;
}
input {
  width: 330px;
  height: 30px;
  border: 1px solid #6666;
  border-radius: 5px;
}
.loginitem h4 {
  margin-top: 40px;
  margin-bottom: 10px;
}
button {
  width: 100px;
  height: 35px;
  background: white;
  border: 1px solid #6666;
  border-radius: 5px;
  cursor: pointer;
  color: #909399;
  margin-top: 10px;
}
h5 {
  display: inline;
  margin-left: 35px;
}
.flexbetween {
  display: flex;
  justify-content: space-between;
}
</style>