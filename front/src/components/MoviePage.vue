<template>

<div class="titlecon">
    <div class="titleitem"><h1>무비차트</h1></div>
</div>

<div class="con" v-for="a,i in 5" :key="i">
    <div class="item">
    <div v-for=" a1,i1 in 4" :key="i1">
       <div class="itemno" v-show="a==1"><h4>NO.1 </h4> </div>
       <div class="itemno1" v-show="a!==1"><h4>NO.2 </h4> </div>
       <img src="../assets/moviescroll/chartimg0.jpg" alt="" class="itemimg">
       <strong>제목</strong>
       <small>개봉날짜</small>
       <button class="reserbtn">예매하기</button>
    </div>
    </div>
</div>
</template>

<script>

export default {
    created() {
  },
}
</script>

<style scoped>
.reserbtn{
    display: block;
    width: 97px;
    height: 25px;
    background: #fb4357;
    padding: 3px;
    color: white;
    border: none;
    border-radius: 5px;
    margin-top: 10px;
}

strong{
    display: block;
    margin-bottom: 5px;
}

h4{
    margin: 0;
}

.itemno{
    width: 200px;
    height: 30px;
    background: #fb4357;
    color: white;
    text-align: center;
    box-sizing: border-box;
    padding: 5px;
    margin-bottom: 3px;
    margin-top: 20px;
}
.itemno1{
    width: 200px;
    height: 30px;
    background: #333333;
    color: white;
    text-align: center;
    box-sizing: border-box;
    padding: 5px;
    margin-bottom: 3px;
    margin-top: 20px;
}

.con{
    display: flex;
    justify-content: center;
    height: 400px;
}
.item{
    width: 980px;
    display: flex;
    justify-content: space-between;
}
.itemimg{
    width: 200px;
    height: 250px;
    margin-bottom: 10px;
}
.titlecon{
    display: flex;
    justify-content: center;
    margin:30px 0px;
}
.titleitem{
    width: 980px;
    border-bottom: 3px solid black;
}
</style>