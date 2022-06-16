<template>
  <div>
    <div style="min-height: 520px">
      <div class="elwrap">
        <el-select
          v-model="this.state.pagecnttable.value"
          class="m-2"
          placeholder="게시글 개수"
          size="large"
          @change="handlePagecnt()"
          style="width: 130px"
        >
          <el-option
            v-for="pagecnt in this.state.pagecnttable"
            :key="pagecnt.value"
            :label="pagecnt.label"
            :value="pagecnt.value"
          />
        </el-select>
        <div>
          <el-input
            v-model="this.state.title"
            placeholder="검색"
            class="elinp"
          />
          <el-button @click="handleSearch()">검색</el-button>
        </div>
      </div>
      <el-table
        :data="this.state.items"
        @cell-click="handleSelect"
        :table-layout="tableLayout"
        style="cursor: pointer"
      >
        <el-table-column prop="bno" label="번호" width="180" />
        <el-table-column prop="btitle" label="제목" />
        <!-- <el-table-column prop="btype" label="타입" width="180" /> -->
        <el-table-column prop="memberEntityMid" label="작성자" />
        <el-table-column prop="bhit" label="조회수" />
        <el-table-column prop="bregdate" label="작성일" />
      </el-table>
    </div>
    <div class="elpage">
      <el-pagination
        layout="prev, pager, next"
        :total="this.state.pagination"
        @current-change="handlePage"
      />
    </div>
  </div>
</template>

<script>
import { onMounted, reactive, ref } from "vue";
import { useRouter } from "vue-router";
import axios from "axios";
import moment from "moment-timezone";
import {
  ElSelect,
  ElOption,
  ElPagination,
  ElTableColumn,
  ElButton,
  ElTable,
  ElInput,
} from "element-plus";
export default {
  components: {
    ElSelect,
    ElOption,
    ElPagination,
    ElTableColumn,
    ElButton,
    ElTable,
    ElInput,
  },

  setup() {
    const router = useRouter();
    const tableLayout = ref("fixed");
    const state = reactive({
      title: "",
      pagination: 10,
      page: 1,
      pagecnt: 5,
      token: sessionStorage.getItem("TOKEN"),
      pagecnttable: [
        {
          value: 5,
          label: "5개",
        },
        {
          value: 10,
          label: "10개",
        },
        {
          value: 20,
          label: "20개",
        },
      ],
      bregdate: [],
    });

    const dateTime = (value) => {
      return moment(value).tz("Asia/Seoul").format("");
    };

    const handleData = async () => {
      console.log(state.pagecnttable.value);
      if (state.pagecnttable.value == undefined) {
        const url = `/TEAMMOVIE/api/board/selectlist?page=${state.page}&title=${state.title}&pagecnt=5&type=2`;
        const headers = {
          "Content-Type": "application/json",
        };
        const response = await axios.get(url, { headers });
        console.log(response.data);
        if (response.data.status == 200) {
          state.items = response.data.boardlist;

          state.pagination = Math.ceil(response.data.boardcnt / 5) * 10;
          console.log(state.pagination);
          for (let i = 0; i < state.items.length; i++) {
            state.items[i].bregdate = dateTime(state.items[i].bregdate)
              .split("+")[0]
              .split("T")
              .join(" ");
          }
        }
      } else {
        const url = `/TEAMMOVIE/api/board/selectlist?page=${state.page}&title=${state.title}&pagecnt=${state.pagecnttable.value}&type=2`;
        const headers = {
          "Content-Type": "application/json",
        };
        const response = await axios.get(url, { headers });
        console.log(response.data);
        if (response.data.status == 200) {
          state.items = response.data.boardlist;
          state.pagination =
            Math.ceil(response.data.boardcnt / state.pagecnttable.value) * 10;
          for (let i = 0; i < state.items.length; i++) {
            state.items[i].bregdate = dateTime(state.items[i].bregdate)
              .split("+")[0]
              .split("T")
              .join(" ");
          }
        }
      }
    };
    onMounted(() => {
      handleData();
    });

    const handlePage = (idx) => {
      console.log(idx);
      state.page = idx;
      handleData();
    };

    const handleSearch = () => {
      state.page = 1;
      handleData();
    };
    const handlePagecnt = () => {
      handleData();
    };

    const handleSelect = async (row, column) => {
      if (column.label != "버튼") {
        const url = `/TEAMMOVIE/api/board/updatehit?no=${row.bno}`;
        const headers = {
          "Content-Type": "application/json",
        };
        const response = await axios.get(url, { headers });
        console.log(response.data);
        if (response.data.status == 200) {
          router.push({ name: "boardcontent", query: { no: row.bno } });
        }
      }
    };

    const handleDelete = async (row) => {
      const url = `/TEAMMOVIE/api/board/admindelete?no=${row.bno}`;
      const headers = {
        "Content-Type": "application/json",
        TOKEN: state.token,
      };
      const body = {};
      const response = await axios.put(url, body, { headers: headers });
      console.log(response.data);
      if (response.data.status == 200) {
        const url2 = `/TEAMMOVIE/api/board/deleteallimg?no=${row.bno}`;
        const headers2 = {
          "Content-Type": "application/json",
          TOKEN: state.token,
        };
        const response2 = await axios.delete(url2, { headers: headers2 });
        console.log(response2);
        handleData();
      }
    };

    return {
      state,
      handlePage,
      handleSearch,
      handlePagecnt,
      handleSelect,
      handleDelete,
      tableLayout,
      dateTime,
    };
  },
};
</script>

<style scoped>
.elpage {
  justify-content: center;
  display: flex;
}
.elinp {
  width: 200px;
  margin-right: 10px;
}
.elwrap {
  display: flex;
  justify-content: space-between;
}
.aa {
  background: red;
}
</style>