<template>
    <div class="titlecon">
        <div class="titleitem">
            <h1>진행중인 이벤트</h1>
        </div>
    </div>
    <div v-for="a,i in eventtitle" :key="i" style="margin:50px 0px">
        <div class="spacebet">
            <div class="spacebet_wrap" style="border: none">
                <div class="spacebet_item">
                    <h2 style="color: #503396;">{{a}}</h2>
                </div>
                <div class="spacebet_item">
                    <h3>더 보기 ></h3>
                </div>
            </div>
        </div>

        <div class="spacebet">
            <div class="spacebet_wrap">

                <div class="eventwrap" v-for="a,i in 4" :key="i">
                    <img
                        src="https://img.megabox.co.kr/SharedImg/event/2022/05/16/dxOKIhpQbC6Qhwzbdz3TjzCqneX6h1ZO.jpg"
                        alt="">
                        <h4 style="margin-bottom: 5px;margin-top: 0px;padding: 18px;">메가박스 오리지널 티켓no52. 범죄 도시</h4>
                        <small style="padding: 18px;">2022.05.18~2022.05.31</small>
                    </div>

                </div>
            </div>
        </div>
    </template>

    <script>
        export default {
            data() {
                return {
                    eventtitle: ['메가Pick', '영화', '극장', '제휴/할인']
                }
            }
        }
    </script>

    <style scoped="scoped">
        .titlecon {
            display: flex;
            justify-content: center;
            margin-top: 50px;
            margin-bottom: 80px;
        }
        .titleitem {
            width: 1080px;
            border-bottom: 2px solid black;
        }

        .spacebet {
            display: flex;
            justify-content: center;
        }
        .spacebet_wrap {
            display: flex;
            justify-content: space-between;
            width: 1080px;
            align-items: center;

        }
        .eventwrap {
            width: 235px;
            height: 355px;
            border-radius: 10px;
            border: 1px solid #6666;
        }
        .eventwrap img {
            border-top-left-radius: 10px;
            border-top-right-radius: 10px;
            width: 100%;
        }
    </style>