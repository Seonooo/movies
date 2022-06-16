<template>
  <div style="padding-right: 30px; padding-left: 30px">
    <div id="demo-container">
      <h2>프리미엄관</h2>
      <DxScrollView id="scroll" class="dxscroll">
        <DxDraggable
          id="list"
          :group="this.state.draggingGroupName"
          :on-drag-start="onListDragStart"
          data="dropArea"
        >
          <DxDraggable
            v-for="task in this.state.title"
            :key="task"
            :clone="true"
            :group="this.state.draggingGroupName"
            :data="task"
            :on-drag-start="onItemDragStart"
            :on-drag-end="onItemDragEnd"
            class="item dx-card dx-theme-text-color dx-theme-background-color"
          >
            {{ task.text }}
          </DxDraggable>
        </DxDraggable>
      </DxScrollView>

      <el-button
        size="large"
        style="
          position: relative;
          top: 700px;
          width: 240px;
          height: 100%;
          background: #7e858b;
          color: white;
          left: -110px;
        "
        @click="handleTicket()"
      >
        예매하기</el-button
      >

      <DxScheduler
        time-zone="Asia/Seoul"
        id="scheduler"
        :data-source="this.state.appointments"
        :current-date="this.state.currentDate"
        :views="this.state.views"
        :show-all-day-panel="false"
        :height="700"
        :start-day-hour="9"
        :editing="true"
        :cellDuration="10"
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
          :on-add="onAppointmentAdd"
        />
        <DxResource :data-source="assignees" field-expr="index" label="영화" />
      </DxScheduler>
    </div>
  </div>
  <div></div>
</template>
<script>
import DxScheduler, {
  DxAppointmentDragging,
  DxResource,
} from "devextreme-vue/scheduler";
import DxDraggable from "devextreme-vue/draggable";
import DxScrollView from "devextreme-vue/scroll-view";
import axios from "axios";
import notify from "devextreme/ui/notify";
import { reactive } from "@vue/reactivity";
import { onMounted } from "@vue/runtime-core";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { ElButton } from "element-plus";
export default {
  components: {
    DxScheduler,
    DxDraggable,
    DxScrollView,
    DxAppointmentDragging,
    DxResource,
    ElButton,
  },
  setup() {
    const router = useRouter();
    const store = useStore();
    const state = reactive({
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
    // 컬러
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

    const handleTicket = async () => {
      if (state.appointments.length > state.scheduleindex) {
        let movie = "";
        movie = state.appointments[state.scheduleindex];
        console.log(movie);

        let year = movie.startDate.getFullYear();
        let month = movie.startDate.getMonth();
        let day = movie.startDate.getDate();
        let date = new Date(year, month, day);
        console.log(date);
        console.log(movie.startDate);
        const body = {
          tdate: date,
          tstart: movie.startDate,
          tend: movie.endDate,
        };

        const url = `/TEAMMOVIE/api/ticket/insert?mcode=${movie.code}&thcode=${state.thcode}`;
        const headers = {
          "Content-Type": "application/json",
          TOKEN: sessionStorage.getItem("TOKEN"),
        };
        const response = await axios.post(url, body, { headers });
        console.log(response);

        if (response.data.status === 200) {
          router.push({ name: "Payment" });
        }
      } else {
        console.log("영화를 선택해 주세요");
      }
    };

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/movie/select?page=1&size=10`;
      const headers = { "Content-Type": "application/json" };
      const response = await axios.get(url, { headers });
      //   console.log(response.data);
      if (response.data.status === 200) {
        state.movies = response.data.movies;
        let title = new Array();

        for (let i = 0; i < state.movies.length; i++) {
          let Item1 = new Object();
          Item1.text = state.movies[i].mtitle;
          Item1.time = state.movies[i].mtime;
          Item1.code = state.movies[i].mcode;
          Item1.index = 1;
          title.push(Item1);
        }

        state.title = title;
        // console.log(state.title);
      }
    };

    const handleTheater = async () => {
      const url = `/TEAMMOVIE/api/theater/selects`;
      const headers = { "Content-Type": "application/json" };
      const response = await axios.get(url, { headers });

      console.log(response);
      if (response.data.status === 200) {
        let theaters = new Array();
        let theater = response.data.theaters;
        for (let i = 0; i < response.data.theaters.length; i++) {
          let Item = new Object();
          Item.thcode = theater[i].thcode;
          Item.thmaximum = theater[i].thmaximum;
          Item.thprice = theater[i].thprice;
          Item.thstate = theater[i].thstate;
          Item.thtype = theater[i].thtype;

          theaters.push(Item);
        }
        state.theater = theaters;
        console.log(state.theater);
      }
    };

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

    onMounted(() => {
      handleData();
      handleTheater();
      handleSelectTheater();
      store.commit("cgvnavm", true);
    });

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

      handleData();
    };

    const showToast = (event, value, type) => {
      notify(`${event} "${value}" task`, type, 800);
    };

    const showDeletedToast = (e) => {
      if (e.appointmentData.index < state.scheduleindex) {
        e.cancel;
      } else {
        showToast("Deleted", e.appointmentData.text, "warning");
        console.log("showDeletedToast", state.appointments);
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

    const onAppointmentAdd = (e) => {
      const index = state.title.indexOf(e.fromData);
      // console.log("onAppointmentAdd e.itemData",e.itemData);
      let start = true;
      let end = true;
      let now = true;

      // 시간설정
      let date = new Date();
      let nowDate = new Date();
      const time = Math.ceil(e.itemData.time / 10) * 10 + 10;
      const hour = Math.floor(time / 60);
      const minute = time - hour * 60;

      let endDate = e.itemData.endDate;
      let startDate = e.itemData.startDate;
      date.setTime(startDate);
      date.setHours(date.getHours() + hour);
      date.setMinutes(date.getMinutes() + minute);
      endDate.setTime(date);

      if (nowDate.getTime() > e.itemData.startDate) {
        now = false;
      }

      if (state.appointments.length > 0) {
        for (let i = 0; i < state.appointments.length; i++) {
          if (e.itemData.startDate >= state.appointments[i].startDate) {
            if (e.itemData.startDate < state.appointments[i].endDate) {
              start = false;
              console.log("start false");
            }
          } else if (e.itemData.startDate < state.appointments[i].startDate) {
            if (e.itemData.endDate > state.appointments[i].startDate) {
              end = false;
              console.log("end false");
            }
          }
          console.log("onAppointmentAdd", state.appointments);
          //   console.log("영화 시작시간",e.itemData.startDate);
          //   console.log("영화 종료시간",e.itemData.endDate);
          //   console.log("다른영화 시작시간",state.appointments[i].startDate);
        }
      }

      if (
        start === true &&
        end === true &&
        now === true &&
        state.appointments.length == state.scheduleindex
      ) {
        if (index >= 0) {
          state.title = [...state.title];
          state.title.splice(index, 1);
          state.appointments = [...state.appointments, e.itemData];
        }
      }

      handleData();
      console.log("onAppointmentAdd", state.appointments);
    };

    const onListDragStart = (e) => {
      console.log("onListDragStart");
      e.cancel = true;
    };

    const onItemDragStart = (e) => {
      e.itemData = e.fromData;
      console.log("onItemDragStart", e.fromData);
    };

    const onItemDragEnd = (e) => {
      console.log("onItemDragEnd", e);
      if (e.toData) {
        e.cancel = true;
      }
    };

    const showDeletingToast = (e) => {
      console.log(e);
      if (e.appointmentData.index < state.scheduleindex) {
        e.cancel = true;
      }
    };
    return {
      state,
      onAppointmentRemove,
      onAppointmentAdd,
      onListDragStart,
      onItemDragStart,
      onItemDragEnd,
      handleData,
      showToast,
      showUpdatingToast,
      showDeletedToast,
      dblclick,
      cellclick,
      handleTheater,
      handleSelectTheater,
      click,
      showDeletingToast,
      handleTicket,
      assignees,
    };
  },
};
</script>
<style scoped>
#scroll,
#list {
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  width: 240px;
}

.item {
  box-sizing: border-box;
  padding: 10px 20px;
  margin-bottom: 10px;
}

#scheduler {
  margin-left: 270px;
}

.dx-draggable-source {
  opacity: 0.5;
}

.dx-draggable-dragging > * {
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1), 0 6px 8px rgba(0, 0, 0, 0.2);
}
.dxscroll {
  margin-top: 350px;
  margin-left: 30px;
  height: 800px;
}
h2 {
  display: inline;
  position: relative;
  top: 50px;
}
</style>
