<template>
  <div>
    <div class="videocon">
      <video class="videobox" autoplay muted loop>
        <source
          src="https://www.lottecinema.co.kr/NLCHS/Content/video/visual_colorium.mp4"
          type="video/mp4"
        />
      </video>
      <div class="videop">
        <h4 style="margin-top: 25px; text-align: center">
          단 하나의 프리미엄 상영관
        </h4>
      </div>
    </div>
    <div class="conwrap" :style="state.bg">
      <div class="con">
        <div class="item" :style="state.styleObject">
          <h3 style="font-size: 40px; color: #c0bfbe; margin: 0px">The</h3>
          <h3 class="aa">First Class</h3>
          <p style="margin-top: 100px; font-size: 30px">최상의 관람환경</p>
          <small>
            온 몸을 감싸는 최고급 가죽시트와 <br />각도 조절이 가능한 리클라이너
            체어로 <br />퍼스트 클래스의 안락함을 느껴보세요.
          </small>
          <div class="c5div"></div>
        </div>
        <div class="c1img" :style="state.styleObject2">
          <img
            :src="require('../../assets/cinema/c1.png')"
            style="height: 500px; width: 700px"
          />
        </div>
      </div>
    </div>
    <div :style="state.styleObject3" class="loungecon">
      <div class="con1">
        <div>
          <p class="bb">Lounge</p>
          <p style="font-size: 30px; margin: 0px">
            품격있는 전용 라운지
            <br />기다림의 즐거움
          </p>
          <small> 영화의 기다림을 인상적인 순간으로 남겨드립니다. </small>
        </div>
      </div>
      <div class="c2div">
        <div>
          <img
            :src="require('../../assets/cinema/c2.png')"
            style="height: 450px"
          />
        </div>
      </div>
    </div>
    <div class="serviceconwrap" :style="state.bg1">
      <div class="con3 servicecon" :style="state.styleObject4">
        <div>
          <img
            :src="require('../../assets/cinema/ci1.jpg')"
            style="width: 500px; height: 400px"
          />
        </div>
        <div style="padding: 30px">
          <p style="font-size: 40px; color: #c0bfbe; margin: 0px">Premium</p>
          <p class="aa1">Service</p>
          <div class="ii">
            <p style="font-size: 30px; margin: 0px">프리미엄 서비스</p>
            <small>
              웰컴 음료&푸드, 개인 담요와 물품 보관까지,<br />
              프리미엄 서비스를 누려보세요.
            </small>
          </div>
        </div>
      </div>
    </div>
    <div
      class="con3 bellcon"
      style="justify-content: right"
      :style="state.styleObject5"
    >
      <div style="padding: 30px; margin-right: 30px">
        <p class="aa1" style="top: 1px; left: 150px">Concierge</p>
        <div class="ii" style="margin-top: 100px">
          <p style="font-size: 30px; margin: 0px">
            당신이 필요한
            <br />순간에는 언제나
          </p>
          <small>
            개인 호출 벨을 통해 영화 감상 중<br />
            언제든지 서비스를 요청하실 수 있습니다.
          </small>
        </div>
      </div>
      <div><img :src="require('../../assets/cinema/c4.png')" /></div>
    </div>
    <div class="schcon">
      <div class="schwrap">
        <DxScheduler
          time-zone="Asia/Seoul"
          id="scheduler"
          :data-source="this.state.appointments"
          :current-date="this.state.currentDate"
          :views="this.state.views"
          :show-all-day-panel="false"
          :height="900"
          :start-day-hour="9"
          :editing="true"
          :cellDuration="60"
          :shade-until-current-time="true"
          :on-appointment-updating="showUpdatingToast"
          :on-appointment-deleted="showDeletedToast"
          :on-appointment-dbl-click="dblclick"
          :on-appointment-click="click"
          :on-cell-click="cellclick"
          :on-appointment-deleting="showDeletingToast"
        >
          <DxAppointmentDragging
            :group="this.state.draggingGroupName"
            :on-remove="onAppointmentRemove"
          />
          <DxResource
            :data-source="assignees"
            field-expr="index"
            label="영화"
          />
        </DxScheduler>
      </div>
    </div>
  </div>
</template>

<script>
import { reactive } from "@vue/reactivity";
import { useStore } from "vuex";
import DxScheduler, {
  DxAppointmentDragging,
  DxResource,
} from "devextreme-vue/scheduler";
import DxDraggable from "devextreme-vue/draggable";
import DxScrollView from "devextreme-vue/scroll-view";
import axios from "axios";
import notify from "devextreme/ui/notify";
import { onMounted } from "@vue/runtime-core";
import { useRouter } from "vue-router";
export default {
  components: {
    DxScheduler,
    DxDraggable,
    DxScrollView,
    DxAppointmentDragging,
    DxResource,
  },
  setup() {
    const store = useStore();
    const state = reactive({
      styleObject: {},
      styleObject2: {},
      styleObject3: {},
      styleObject4: {},
      styleObject5: {},
      bg: {},
      bg1: {},
      scrolledy: 0,

      draggingGroupName: "appointmentsGroup",
      views: [{ type: "day", intervalCount: 5 }],
      currentDate: new Date(),
      appointments: "",
      movies: "",
      title: "",
      theater: "",
      scheduleindex: 0,
      thcode: 3,
    });

    const assignees = [
      {
        // 기존스케줄에 있는 영화
        text: "Samantha Bright",
        id: 0,
        color: "#7E8588",
      },
      {
        // 새로 추가된 영화
        text: "John Heart",
        id: 1,
        color: "#32c9ed",
      },
    ];

    const dblclick = (e) => {
      // console.log("dblclick",e);
      e.cancel = true;
    };
    const click = (e) => {
      // console.log("dblclick",e);
      e.cancel = true;
    };

    const cellclick = (e) => {
      // console.log("cellclick",e);
      e.cancel = true;
    };

    const onListDragStart = (e) => {
      console.log("onListDragStart");
      e.cancel = true;
    };

    const onItemDragEnd = (e) => {
      console.log("onItemDragEnd", e);
      if (e.toData) {
        e.cancel = true;
      }
    };

    const showDeletedToast = (e) => {
      if (e.appointmentData.index < state.scheduleindex) {
        e.cancel;
      } else {
        showToast("Deleted", e.appointmentData.text, "warning");
        console.log("showDeletedToast", state.appointments);
      }
    };

    const showDeletingToast = (e) => {
      console.log(e);
      if (e.appointmentData.index < state.scheduleindex) {
        e.cancel = true;
      }
    };

    const showUpdatingToast = (e) => {
      const now = new Date();

      if (now > e.newData.startDate || e.oldData.index < state.scheduleindex) {
        e.cancel = true;
      } else if (now < e.newData.startDate) {
        console.log("showUpdatingToast", e);
        showToast("Updated", e.newData, "info");
      }
      console.log(state.appointments);
    };

    const showToast = (event, value, type) => {
      notify(`${event} "${value}" task`, type, 800);
    };

    const onAppointmentRemove = (e) => {
      const index = state.appointments.indexOf(e.itemData);
      console.log("onAppointmentRemove", e);
      if (index >= state.scheduleindex) {
        // console.log("onAppointmentRemove",e.itemData.text);
        state.appointments = [...state.appointments];
        state.appointments.splice(index, 1);
        state.title = [...state.title, e.itemData];
        console.log("onAppointmentRemove", state.appointments);
      }
    };

    onMounted(() => {
      handleSelectTheater();
    });

    const handleSelectTheater = async () => {
      const url = `/TEAMMOVIE/api/select/scheduletoday?thcode=${state.thcode}`;
      const headers = { "Content-Type": "application/json" };
      const response = await axios.get(url, { headers });
      if (response.data.status === 200) {
        let appointment = new Array();
        console.log("handleSelectTheater 1", response.data.schedule.length);
        state.appointments = "";
        if (response.data.schedule.length > 0) {
          for (let i = 0; i < response.data.schedule.length; i++) {
            let appointments = new Object();
            appointments.index = 0;
            appointments.text = response.data.schedule[i].mtitle;

            appointments.startDate = new Date(response.data.schedule[i].tstart);
            appointments.endDate = new Date(response.data.schedule[i].tend);
            appointment.push(appointments);
          }
        }

        state.scheduleindex = response.data.schedule.length;
        state.appointments = appointment;
        console.log("handleSelectTheater 2", state.appointments);
      }
    };

    window.addEventListener("scroll", handleScroll);
    window.addEventListener("scroll", slide);

    function handleScroll(event) {
      state.scrolledy = window.scrollY;
    }

    function slide() {
      if (state.scrolledy > 700) {
        state.styleObject.transform = "translateX(0px)";

        state.styleObject2.transform = "translateX(100px)";

        state.bg.background = "#eeeeee";
      }
      if (state.scrolledy > 1300) {
        state.styleObject3.transform = "translateY(0px)";
      }
      if (state.scrolledy > 2100) {
        state.styleObject4.transform = "translateX(0px)";

        state.bg1.background = "#eeeeee";
      }
      if (state.scrolledy > 2500) {
        state.styleObject5.transform = "translateX(0px)";
      }
    }

    store.commit("cgvnavm", true);
    return {
      state,
      handleScroll,
      onAppointmentRemove,

      onListDragStart,

      onItemDragEnd,

      showToast,
      showUpdatingToast,

      dblclick,
      cellclick,

      handleSelectTheater,
      click,
      showDeletingToast,
      showDeletedToast,
      assignees,
    };
  },
};
</script>

<style scoped>
.schcon {
  display: flex;
  justify-content: center;
}
.schwrap {
  width: 1000px;
}

small {
  margin-top: 20px;
  display: block;
}

.c5div {
  width: 266px;
  height: 256px;
  position: absolute;
  background-image: url("../../assets/cinema/c5.png");
  top: 450px;
}

.videop {
  color: white;
  position: absolute;
  top: 450px;
  left: 350px;
  font-size: 30px;
}
.ii {
  margin-top: 100px;
}

.aa1 {
  font-weight: 500;
  font-size: 80px;
  color: #c0bfbe;
  margin: 0px;
  position: absolute;
  width: 400px;
  top: 58px;
}

.con3 {
  display: flex;
  width: 1012px;
  margin: 0 250px;
  margin-bottom: 150px;
  position: relative;
}

.c2div {
  display: flex;
  width: 1012px;
  margin: 0 250px;
  justify-content: right;
  margin-bottom: 200px;
}

.con1 {
  display: flex;
  height: 200px;
  align-items: center;
  width: 1012px;
  margin: 0 250px;
  justify-content: right;
  text-align: right;
  margin-bottom: 50px;
  margin-top: 120px;
}

.bb {
  font-weight: 500;
  font-size: 80px;
  color: #c0bfbe;
  margin: 0px;
}

.aa {
  font-weight: 500;
  font-size: 80px;
  color: #c0bfbe;
  margin: 0px;
  position: absolute;
  width: 400px;
  top: 52px;
}

.item {
  position: relative;
  padding-right: 90px;
  width: 25%;
  flex-wrap: unset;
  transition: all 3s;
  transform: translateX(-700px);
}

.con img {
  width: 635px;
  height: 358px;
}

.con {
  display: flex;
  height: 560px;
  align-items: center;
  margin-top: 100px;
  width: 1000px;
  justify-content: space-between;
}
.videocon {
  position: relative;
}
.c1img {
  transition: all 3s;
  transform: translateX(1200px);
}
.loungecon {
  transition: all 3s;
  transform: translateY(500px);
}
.servicecon {
  transition: all 3s;
  transform: translateX(-1100px);
}
.bellcon {
  transition: all 3s;
  transform: translateX(1000px);

  margin-top: 100px;
}
.conwrap {
  display: flex;
  justify-content: center;

  transition: all 3s;
}
.serviceconwrap {
  display: flex;
  justify-content: center;

  transition: all 3s;
  height: 650px;
}
</style>