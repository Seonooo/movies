<template>
  <div v-if="state.member">
    <h3>회원정보</h3>
    <div class="mycgvupdate_flexcon">
      <div class="mycgvupdate_flexitemid">
        <h5>아이디</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <h5>{{ state.member.mid }}</h5>
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>이름</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <h5>{{ state.member.mname }}</h5>
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>멤버쉽</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <h5>{{ state.member.membershipEntity.msmembership }}</h5>
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>전화번호</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <h5>{{ state.member.mphone }}</h5>
      </div>
    </div>
    <div class="mycgvupdate_flexcon" style="border-top: none">
      <div class="mycgvupdate_flexitemid">
        <h5>주소</h5>
      </div>
      <div class="mycgvupdate_flexitemidupdate">
        <h5>{{ state.member.maddr }}</h5>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    const state = reactive({
      token: sessionStorage.getItem("TOKEN"),
      checkpwsmall: "",
      mphone: "",
      checktelsmall: "",
    });

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/member/mypage`;
      const headers = {
        "Content-type": "application/json",
        TOKEN: state.token,
      };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        state.member = response.data.member;
        console.log(state.member);
      }
    };

    onMounted(() => {
      handleData();
    });

    return {
      state,
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