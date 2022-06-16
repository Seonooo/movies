<template>
  <div>
    <h3>회원정보수정</h3>
    <div class="mycgvupdate_flexcon">
      <div class="mycgvupdate_flexitemid">
        <h5>아이디</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate" v-if="state.member.mid">
        <h5>{{ state.member.mid }}</h5>
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>현재 비밀번호</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <input
          type="password"
          ref="mpw"
          class="mycgvupdate_inputpassword"
          placeholder="현재 비밀번호를 입력해주세요"
          v-model="state.member.mpw"
        />
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>새 비밀번호</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <input
          type="password"
          ref="mpw1"
          maxlength="16"
          class="mycgvupdate_inputpassword"
          placeholder="새 비밀번호를 입력해주세요"
          v-model="state.member.mpw1"
        />
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>새 비밀번호확인</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <input
          type="password"
          ref="mpw1c"
          maxlength="16"
          class="mycgvupdate_inputpassword"
          placeholder="새 비밀번호를 다시 입력해주세요"
          @keyup="handlePwCheck"
          v-model="state.member.mpw1c"
        />
        <div class="checksmall">
          <small>{{ state.checkpwsmall }}</small>
        </div>
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>변경할 주소</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <input
          type="text"
          ref="maddr"
          class="mycgvupdate_inputpassword"
          placeholder="주소를 입력해주세요"
          v-model="state.member.maddr"
        />
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>변경할 이메일</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <input
          type="text"
          ref="email1"
          class="mycgvupdate_inputpassword"
          placeholder="이메일을 입력해주세요"
          v-model="state.member.email1"
          style="width: 200px"
        />
        <div style="display: inline">
          <h5 style="display: inline">@</h5>
          <select v-model="state.member.email2">
            <option>gmail.com</option>
            <option>naver.com</option>
            <option>daum.com</option>
          </select>
        </div>
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>변경할 연락처</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <input
          type="text"
          ref="mphone"
          class="mycgvupdate_inputpassword"
          placeholder="휴대전화번호을 입력해주세요. (ex 010-xxxx-xxxx)"
          v-model="state.member.mphone"
          @keyup="handleTelCheck"
        />
        <div class="checksmall">
          <small>{{ state.checktelsmall }}</small>
        </div>
      </div>
    </div>

    <div class="btncon">
      <button @click="handleUpdate">
        <h5 style="margin: 0">확인</h5>
      </button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { reactive, ref } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import { useRouter } from "vue-router";
export default {
  setup() {
    const router = useRouter();
    const state = reactive({
      member: {
        mid: "",
        mpw: "",
        mpw1: "",
        mphone: "",
        memail: "",
        maddr: "",
        email1: "",
        email2: "gmail.com",
        mpw1c: "",
      },
      token: sessionStorage.getItem("TOKEN"),
      checkpwsmall: "",
      mphone: "",
      checktelsmall: "",
    });

    const mpw = ref(null);
    const mpw1 = ref(null);
    const mphone = ref(null);
    const maddr = ref(null);
    const email1 = ref(null);
    const mpw1c = ref(null);

    const handleUpdate = async () => {
      if (state.member.mpw.length < 1) {
        alert("현재비밀번호를 입력해주세요");
        mpw.value.focus();
        return false;
      } else if (state.member.mpw1.length < 8) {
        alert("새 비밀번호를 입력해주세요");
        mpw1.value.focus();
        return false;
      } else if (
        state.member.mpw1c === "" &&
        state.member.mpw1c !== state.member.mpw1
      ) {
        alert("새 비밀번호를 확인해주세요");
        mpw1c.value.focus();
        return false;
      } else if (state.member.maddr.length < 1) {
        alert("주소를 입력해주세요");
        maddr.value.focus();
        return false;
      } else if (state.member.email1.length < 1) {
        alert("이메일을 입력해주세요");
        email1.value.focus();
        return false;
      } else if (state.member.email2 === "") {
        alert("이메일을 선택해주세요");
        return false;
      } else if (state.member.mphone.length < 1) {
        alert("연락처를 입력해주세요");
        mphone.value.focus();
        return false;
      } else {
        state.member.memail = state.member.email1 + "@" + state.member.email2;
        const url = `/TEAMMOVIE/api/member/updateinfo`;
        const headers = {
          "Content-Type": "application/json",
          TOKEN: state.token,
        };
        const body = state.member;
        const response = await axios.put(url, body, { headers });
        console.log(response.data.status);
        if (response.data.status === 200) {
          alert("수정이 완료되었습니다, 다시 로그인 해주세요.");
          state.member = [];
          router.push("/");
          sessionStorage.removeItem("TOKEN");
        } else {
          alert("수정실패");
          return false;
        }
      }
    };

    const handlePwCheck = () => {
      var regExppw = /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{8,16}$/;

      if (state.member.mpw1 !== state.member.mpw1c) {
        state.checkpwsmall = "비밀번호 다름";
        return false;
      }
      if (!regExppw.test(state.member.mpw1c)) {
        state.checkpwsmall = "비밀번호 8 ~ 16자 영문, 숫자 조합";
        return false;
      } else {
        state.checkpwsmall = "";
      }
    };

    const handleTelCheck = () => {
      var regExptel = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;

      if (!regExptel.test(state.member.mphone)) {
        state.checktelsmall = "번호를 정확히 입력해주세요";
        return false;
      } else {
        state.checktelsmall = "";
      }
    };

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/member/readtoken`;
      const headers = {
        "Content-type": "application/json",
        TOKEN: state.token,
      };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        state.member.mid = response.data.userid;
      }
    };

    onMounted(() => {
      handleData();
    });

    return {
      state,
      handleUpdate,
      handlePwCheck,
      handleTelCheck,
      mpw,
      mpw1,
      mphone,
      maddr,
      email1,
      mpw1c,
    };
  },
};
</script>

<style scoped="scoped">
select {
  height: 32px;
  border: 1px solid #6666;
}

.checksmall {
  margin-left: 20px;
  color: #ee6900;
}

.initem1tex {
  display: inline;
  margin: 10px 20px;
  width: 400px;
  padding: 5px;
  color: #555;
  border: 1px solid #dddddd;
}

.btncon button {
  color: white;
  background: #fb4357;
  border: none;
  width: 60px;
  height: 30px;
  padding: 5px;
  cursor: pointer;
}

.btncon {
  display: flex;
  justify-content: center;
  margin-top: 50px;
}

.mycgvupdate_inputpassword {
  width: 320px;
  height: 32px;
  border: 1px solid #6666;
  padding: 10px;
}

* {
  box-sizing: border-box;
}

.mycgvupdate_flexcon {
  display: flex;
  align-items: center;
  border-top: 1px solid black;
  border-bottom: 1px solid #6666;
}
.mycgvupdate_flexitemid {
  background: #f7f7f7;
  width: 200px;
  padding: 30px;
}
.mycgvupdate_flexitemid h5 {
  margin: 0;
}
.mycgvupdate_flexitemidupdate {
  padding-left: 20px;
}
</style>