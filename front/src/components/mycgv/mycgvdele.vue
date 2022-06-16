<template>
  <div>
    <h3>비밀번호 확인</h3>
    <div class="speech">
      <h5>
        고객님의 개인정보 보호를 위한 절차이오니, 로그인 시 사용하는 비밀번호를
        입력해 주세요.
      </h5>
    </div>
    <div class="idpwcon">
      <div class="idpwcon_div-idpw">
        <h5>아이디</h5>
        <h5>비밀번호</h5>
      </div>
      <div>
        <h5>{{ state.userid }}</h5>
        <input
          type="password"
          class="idpwcon_div_input"
          placeholder="비밀번호를 입력해주세요"
          v-model="state.pw"
        />
      </div>
    </div>
    <div class="btncon">
      <button @click="handleDelete()"><h5 style="margin: 0">확인</h5></button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { reactive } from "@vue/reactivity";
import { useRouter } from "vue-router";
import { onMounted } from "@vue/runtime-core";

export default {
  setup() {
    const router = useRouter();
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
      pw: "",
      userid: "",
    });

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/member/readtoken`;
      const headers = {
        "Content-type": "application/json",
        TOKEN: state.token,
      };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        state.userid = response.data.userid;
      }
    };

    const handleDelete = async () => {
      if (confirm("탈퇴하시겠습니까?")) {
        const url = `/TEAMMOVIE/api/member/delete`;
        const headers = {
          "Content-type": "application/json",
          TOKEN: state.token,
        };
        const body = {
          mpw: state.pw,
        };
        const response = await axios.put(url, body, { headers });
        if (response.data.status === 200) {
          sessionStorage.removeItem("TOKEN");
          alert("탈퇴가 완료되었습니다.");
          router.push("/");
        } else {
          alert("암호를 확인해주세요");
        }
      }
    };

    onMounted(() => {
      handleData();
    });

    return { state, handleDelete };
  },
};
</script>

<style scoped>
.idpwcon_div_input {
  width: 200px;
  height: 25px;
  border: 1px solid #6666;
  padding: 10px;
}

.btncon button {
  color: white;
  background: #fb4357;
  border: none;
  width: 60px;
  height: 30px;
  padding: 5px;
}

.btncon {
  display: flex;
  justify-content: center;
  margin-top: 50px;
}

.idpwcon_div-idpw {
  width: 100px;
}

.idpwcon {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  border-bottom: 1px solid #6666;
  height: 120px;
}

* {
  box-sizing: border-box;
}

.speech {
  background: #f7f7f7;
  padding: 10px;
  border-top: 1px solid #6666;
}
.speech h5 {
  margin: 0px;
}
</style>