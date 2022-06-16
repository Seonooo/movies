<template>
  <div>
    <div class="mycgvhome"><h3>마이 페이지</h3></div>
    <div class="con">
      <h3>나의 예매내역</h3>
      <h5 @click="$store.commit('mycgvm', 1)">예매내역</h5>

      <!-- <h3>관람권/할인쿠폰관리</h3>
      <h5 @click="$store.commit('mycgvm', 2)">보유쿠폰</h5> -->

      <h3>회원정보</h3>
      <h5 @click="$store.commit('mycgvm', 6)">회원정보</h5>
      <h5 @click="$store.commit('mycgvm', 3)">회원 정보 수정</h5>
      <h5 @click="$store.commit('mycgvm', 4)">프로필변경</h5>
      <h5 @click="$store.commit('mycgvm', 5)">회원탈퇴</h5>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.con h3 {
  margin-top: 45px;
}

.con h5 {
  margin-left: 40px;
  color: grey;
  cursor: pointer;
}

.con {
  padding: 0px 15px;
}

* {
  box-sizing: border-box;
}

.mycgvhome {
  background: rgba(26, 22, 22, 0.4);
  color: white;
  height: 50px;
  padding: 15px;
}
.mycgvhome h3 {
  margin: 0;
}
</style>