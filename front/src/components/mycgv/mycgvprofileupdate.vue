<template>
    <div>
        <h3>프로필 수정</h3>

        <div class="mycgvupdate_flexcon">
            <div class="mycgvupdate_flexcon_iddiv"><h5>프로필사진</h5></div>
            <div class="mycgvupdate_flexcon_idupdatediv"><img :src="state.profileimage">
            <br>
            <div>
                <input type="file" @change="handleImage">
            </div>
            
            </div>
        </div>
        <div class="bottomcon">
            <button class="photobtn" style="background: #fb4357; color: white;" handleImageUpdate @click="handleImageUpdate"><h5>적용</h5></button>
            
        </div>
    </div>
</template>

<script>
import { reactive } from '@vue/reactivity'
import axios from 'axios';
import { onMounted } from '@vue/runtime-core';
export default {
    setup(){
        const state = reactive({
            token : sessionStorage.getItem("TOKEN"),
            imgdata: "",
            imgurl: '',
        });

        const handleImageUpdate = async()=>{
            const url = `/TEAMMOVIE/api/member/updateprofile`;
            const headers = {"Content-type":"multipart/form-data", "TOKEN" : state.token};
            const body = new FormData();
            body.append("file", state.imgdata);
            const response = await axios.put(url, body, {headers});
            if(response.data.status === 200){
                alert('변경이 완료되었습니다.');
            }
        };

        const handleData = async()=>{
            const url = `/TEAMMOVIE/api/member/mypage`;
            const headers = {
                "Content-Type": "application/json",
                "TOKEN": state.token
            };
            const response = await axios.get(url, {headers});
            if(response.data.status === 200){
                state.profileimage = "/TEAMMOVIE/member/memberprofile?mid=" + response.data.member.mid;
            }
        };

        onMounted(()=>{
            handleData();
        })

        const handleImage = (e)=>{
            if (e.target.files[0]) {
                console.log(e);
                state.imgdata = e.target.files[0];
                state.profileimage = URL.createObjectURL(e.target.files[0]);
            } else {
                state.imgdata = "";
                state.profileimage = require("../../assets/logo2.png");
            }
        };

        return {state, handleImageUpdate, handleImage}

    }
}
</script>

<style scoped>

.bottomcon{
    display: flex;
    justify-content: center;
    margin-top: 50px;
}

*{
    box-sizing: border-box;
}

.mycgvupdate_flexcon{
    display: flex;
    border-top: 1px solid black;
    border-bottom: 1px solid #6666;
    height: 220px;
}
.mycgvupdate_flexcon_iddiv{
    background: #f7f7f7;
    width: 200px;
    padding: 30px;
}
.mycgvupdate_flexcon_iddiv h5{
    margin: 0px;
}
.mycgvupdate_flexcon_idupdatediv{
    padding-left: 20px;
    padding-top: 30px;
}
.mycgvupdate_flexcon_idupdatediv img{
    width: 100px;
    height: 100px;
    display: block;
}
.photobtn{
    background: white;
    width: 82px;
    height: 35px;
    border: 1px solid #6666;
    margin-top: 20px;
    margin-left: 10px;
}
.photobtn h5{
    margin: 0px;
}
</style>