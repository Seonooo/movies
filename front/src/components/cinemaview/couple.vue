<template>
  <!-- 오1 -->
  <div class="con2" style="justify-content: right; margin-top: 200px">
    <!-- <div class="fcl" style="left: 130px; top: 10px">
      <img :src="require(`../../assets/cinema/fll1.png`)" />
    </div> -->

    <div class="winddiv" style="padding: 0px; padding-right: 50px">
      <p class="pp">Space</p>
      <h3 style="font-size: 25px; margin: 0px; text-align: right">
        데이트를 위한 최고의 선택
      </h3>
      <h5 style="text-align: right">
        넓은 독립형 좌석으로 로맨틱한 영화관람을 할수있습니다
      </h5>
    </div>
    <div>
      <img
        :src="require(`../../assets/cinema/sweet.png`)"
        style="width: 600px"
      />
    </div>
  </div>

  <!-- 왼1 -->
  <div class="con2">
    <div>
      <img
        :src="require(`../../assets/cinema/ball.png`)"
        style="width: 400px"
      />
    </div>
    <div class="winddiv">
      <p class="pp">Comfortable</p>
      <h3 style="font-size: 30px; margin: 0px">Experience</h3>
      <h5>소중한 사람들과의 시간을 더욱 편안하고 특별하게</h5>
    </div>
  </div>

  <!-- 오2 -->
  <div class="con2" style="justify-content: right">
    <div class="winddiv" style="padding: 0px; padding-right: 50px">
      <p class="pp">Customized</p>
      <h3 style="font-size: 30px; margin: 0px; text-align: right">Service</h3>
      <h5 style="text-align: right">
        맞춤형 서비스로 성공적인 행사를 약속 드립니다.
      </h5>
    </div>
    <div>
      <img :src="require(`../../assets/cinema/fire.jpg`)" class="wai" />
    </div>
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
        <DxResource :data-source="assignees" field-expr="index" label="영화" />
      </DxScheduler>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import DxScheduler, {
  DxAppointmentDragging,
  DxResource,
} from "devextreme-vue/scheduler";
import DxDraggable from "devextreme-vue/draggable";
import DxScrollView from "devextreme-vue/scroll-view";
import { reactive, onMounted } from "vue";
import axios from "axios";
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
    store.commit("cgvnavm", true);

    const state = reactive({
      draggingGroupName: "appointmentsGroup",
      views: [{ type: "day", intervalCount: 5 }],
      currentDate: new Date(),
      appointments: "",
      movies: "",
      title: "",
      theater: "",
      scheduleindex: 0,
      thcode: 2,
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

    return {
      state,
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
.con {
  display: flex;
  justify-content: center;
}
.wrap {
  width: 1000px;
}
.schcon {
  display: flex;
  justify-content: center;
}
.schwrap {
  width: 1000px;
}

.wai {
  width: 450px;
  height: 255px;
}

.hub {
  width: 450px;
  height: 255px;
}

.fcl {
  margin-bottom: 150px;
  height: 60px;
  width: 60px;
  background-size: contain;
  background-repeat: no-repeat;
  position: absolute;
  right: 300px;
}

.winddiv {
  padding-left: 60px;
}

.pp {
  font-weight: 500;
  font-size: 80px;
  color: #c0bfbe;
  margin: 0px;
  margin-bottom: 15px;
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
  top: 400px;
}

.videop {
  color: white;
  position: absolute;
  top: 450px;
  left: 350px;
  font-size: 30px;
}

.aa1 {
  font-weight: 500;
  font-size: 80px;
  color: #c0bfbe;
  margin: 0px;
  position: absolute;
  width: 400px;
  top: 22px;
}

.con3 {
  display: flex;
  width: 1012px;
  margin: 0 350px;
  margin-bottom: 150px;
  position: relative;
}

.con2 {
  display: flex;
  width: 1012px;
  margin: 0 250px;
  justify-content: left;
  margin-bottom: 200px;
  align-items: center;
  position: relative;
}

.con1 {
  display: flex;
  height: 200px;
  align-items: center;
  width: 1012px;
  margin: 0 350px;
  justify-content: left;
  text-align: left;
  margin-bottom: 100px;
  margin-top: 120px;
  position: relative;
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
  width: 100%;
  height: 280px;
  flex-wrap: unset;
  background-image: url("../../assets/cinema/f2.png");
  background-size: cover;
  background-repeat: no-repeat;
}

.con img {
  width: 635px;
  height: 358px;
}

.con {
  display: flex;
  align-items: center;
  margin: 0 350px;
  margin-top: 100px;
}
.videocon {
  position: relative;
}
</style>