<template>
  <div>
    <div
      style="
        border: 1px solid #aaaaaa;
        border-radius: 0.5em;
        padding: 20px;
        width: 700px;
        margin-left: auto;
        margin-right: auto;
        margin-top: 50px;
        padding-top: 40px;
      "
    >
      <el-form :model="state" label-width="120px">
        <el-form-item label="제목">
          <el-input v-model="state.item.btitle" />
        </el-form-item>
        <el-form-item label="글 타입">
          <el-select
            v-model="this.state.type.value"
            placeholder="please select your zone"
          >
            <el-option
              v-for="tmp in this.state.type"
              :key="tmp.value"
              :label="tmp.label"
              :value="tmp.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="글 내용">
          <el-input v-model="state.item.bcontent" rows="10" type="textarea" />
        </el-form-item>
        {{ state.chk }}
        <el-form-item label="이미지">
          <div v-if="this.state.img.length > 0">
            <el-button @click="handleAllimg" style="margin-bottom: 10px"
              >이미지 전체 삭제</el-button
            >
            <div v-for="(tmp, idx) in this.state.img.length" :key="tmp">
              <img :src="this.state.img[tmp - 1]" class="img" /><br />
              <el-button @click="handleCheck(state.chkno[idx])">삭제</el-button>
            </div>
          </div>
        </el-form-item>
        <el-form-item label="이미지 추가">
          <el-upload
            class="upload-demo"
            drag
            action="https://jsonplaceholder.typicode.com/posts/"
            multiple
            :on-change="handleChange"
            :on-remove="handleRemove"
          >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
              Drop file here or <em>click to upload</em>
            </div>
            <template #tip>
              <div class="el-upload__tip">20kb 이하 jpg/png 파일</div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item>
          <el-button @click="handleUpdate()">글수정</el-button>
          <el-button @click="boardmove()">Cancel</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { reactive, onMounted } from "vue";
import { UploadFilled } from "@element-plus/icons-vue";
import { useRoute, useRouter } from "vue-router";
import axios from "axios";
import {
  ElSelect,
  ElOption,
  ElPagination,
  ElTableColumn,
  ElButton,
  ElTable,
  ElInput,
  ElForm,
  ElFormItem,
  ElIcon,
  ElRow,
  ElCol,
  ElLink,
  ElUpload,
} from "element-plus";
export default {
  components: {
    UploadFilled,

    ElSelect,
    ElOption,
    ElPagination,
    ElTableColumn,
    ElButton,
    ElTable,
    ElInput,
    ElForm,
    ElFormItem,
    ElIcon,
    ElRow,
    ElCol,
    ElLink,
    ElUpload,
  },
  setup() {
    const route = useRoute();
    const router = useRouter();
    const state = reactive({
      item: [],
      no: route.query.no,
      img: [],
      chkno: [],
      imageFile: "",
      type: [
        {
          value: 1,
          label: "문의",
        },
        {
          value: 2,
          label: "자유",
        },
      ],
    });

    onMounted(() => {
      handleData();
    });

    function boardmove() {
      router.push("/boardview");
    }

    const handleData = async () => {
      const url = `/TEAMMOVIE/api/board/selectone?no=${state.no}`;
      const headers = {
        "Content-Type": "application/json",
      };
      const response = await axios.get(url, { headers });
      console.log(response.data);
      if (response.data.status == 200) {
        state.item = response.data.board;
        state.chkno = response.data.boardimg;
        for (let i = 0; i < response.data.boardimg.length; i++) {
          state.img[i] = "/TEAMMOVIE/board/image?no=" + response.data.boardimg[i];
        }
      }
    };

    const handleUpdate = async () => {
      if (state.item.btitle.length > 100) {
        alert("제목 100글자이하로 입력해주세요");
        return false;
      }
      if (state.item.btitle.length <= 2) {
        alert("제목 3글자이상 입력해주세요");
        return false;
      }

      if (state.item.bcontent.length <= 2) {
        alert("내용 3글자이상 입력해주세요");
        return false;
      }

      var url = "/TEAMMOVIE/api/board/";
      const headers = {
        "Content-Type": "multipart/form-data",
        TOKEN: sessionStorage.getItem("TOKEN"),
      };
      const body = new FormData();
      if (state.imageFile.length > 0) {
        for (let i = 0; i < state.imageFile.length; i++) {
          body.append("file", state.imageFile[i]);
        }
        url = `/TEAMMOVIE/api/board/update`;
      } else {
        url = `/TEAMMOVIE/api/board/insertnoimg`;
      }
      body.append("no", state.item.bno);
      body.append("btitle", state.item.btitle);
      body.append("bcontent", state.item.bcontent);
      body.append("btype", state.type.value);

      const response = await axios.post(url, body, { headers });

      console.log(response.data);

      if (response.data.status === 200) {
        router.push({ name: "BoardView" });
      }
    };

    const handleCheck = async (no) => {
      console.log(no);

      const url = `/TEAMMOVIE/api/board/deleteoneimg?no=${no}`;
      const headers = {
        "Content-Type": "application/json",
        TOKEN: sessionStorage.getItem("TOKEN"),
      };
      const response = await axios.delete(url, { headers });

      console.log(response.data);
      if (response.data.status === 200) {
        router.go();
      }
    };

    const handleAllimg = async () => {
      const url = `/TEAMMOVIE/api/board/deleteallimg?no=${state.item.bno}`;
      const headers = {
        "Content-Type": "application/json",
        TOKEN: sessionStorage.getItem("TOKEN"),
      };
      const response = await axios.delete(url, { headers });

      console.log(response.data);
      if (response.data.status === 200) {
        router.go();
      }
    };

    const handleChange = (uploadFile, uploadFiles) => {
      console.log(uploadFile.raw);
      console.log(uploadFiles);
      state.imageFile = [];

      for (var i = 0; i < uploadFiles.length; i++) {
        state.imageFile[i] = uploadFiles[i].raw;
        // state.imageURL[i] = URL.createObjectURL(uploadFiles[i].raw)
      }

      console.log(state.imageFile);
      // console.log(state.imageURL);
      console.log("------------------");
    };

    const handleRemove = (uploadFile, uploadFiles) => {
      console.log(uploadFile);
      console.log(uploadFiles);
      let count = 1;
      for (let i = 0; i < state.imageFile.length; i++) {
        if (state.imageFile[i].uid === uploadFile.raw.uid && count === 1) {
          state.imageFile.splice(i, 1);
          // state.imageURL.splice(i,1);
          i--;
          count -= 1;
        }
      }

      console.log(state.imageFile);
      // console.log(state.imageURL);
      console.log("------------------");
    };

    const handleInsert = async () => {
      var url = "/TEAMMOVIE/api/board/";
      const headers = {
        "Content-Type": "multipart/form-data",
        TOKEN: sessionStorage.getItem("TOKEN"),
      };
      const body = new FormData();
      if (state.imageFile.length > 0) {
        for (let i = 0; i < state.imageFile.length; i++) {
          body.append("file", state.imageFile[i]);
        }
        url = `/TEAMMOVIE/api/board/insert`;
      } else {
        url = `/TEAMMOVIE/api/board/insertnoimg`;
      }
      body.append("bno", state.item.bno);
      body.append("btitle", state.title);
      body.append("bcontent", state.content);
      body.append("btype", state.type.value);

      const response = await axios.post(url, body, { headers });

      console.log(response.data);

      if (response.data.status === 200) {
        router.push({ name: "BoardView" });
      }
    };

    return {
      state,
      handleData,
      handleUpdate,
      handleCheck,
      handleAllimg,
      handleRemove,
      handleChange,
      handleInsert,
      boardmove,
    };
  },
};
</script>

<style scoped>
.img {
  max-height: 300px;
  max-width: 300px;
  min-height: 100px;
  min-width: 100px;
}
</style>