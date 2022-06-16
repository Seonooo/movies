<template>
  <div class="back">
    <div class="writecon">
      <el-form :model="state" label-width="90px">
        <el-form-item label="제목">
          <el-input v-model="state.title" />
        </el-form-item>
        <el-form-item label="글 타입">
          <el-select
            v-model="this.state.type.value"
            placeholder="게시판을 선택해주세요."
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
          <el-input v-model="state.content" rows="10" type="textarea" />
        </el-form-item>
        <el-form-item label="이미지 추가 ">
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
              <div class="el-upload__tip">20kb이하jpg/png파일</div>
            </template>
          </el-upload>
        </el-form-item>
        <el-form-item style="margin-left: 150px">
          <el-button @click="handleInsert()">글쓰기</el-button>
          <el-button @click="boardmove()">Cancel</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
import { reactive } from "vue";
import { UploadFilled } from "@element-plus/icons-vue";
import { useRouter } from "vue-router";
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
    ElUpload,
  },

  setup() {
    const router = useRouter();
    const state = reactive({
      title: "",
      content: "",
      imageFile: "",
      imageURL: "",
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

    function boardmove() {
      router.push("/boardview");
    }

    const handleChange = (uploadFile, uploadFiles) => {
      console.log(uploadFile.raw);
      console.log(uploadFiles);
      state.imageFile = [];
      state.imageURL = [];

      for (var i = 0; i < uploadFiles.length; i++) {
        state.imageFile[i] = uploadFiles[i].raw;
        state.imageURL[i] = URL.createObjectURL(uploadFiles[i].raw);
      }

      console.log(state.imageFile);
      console.log(state.imageURL);
      console.log("------------------");
    };

    const handleRemove = (uploadFile, uploadFiles) => {
      console.log(uploadFile);
      console.log(uploadFiles);
      let count = 1;
      for (let i = 0; i < state.imageFile.length; i++) {
        if (state.imageFile[i].uid === uploadFile.raw.uid && count === 1) {
          state.imageFile.splice(i, 1);
          state.imageURL.splice(i, 1);
          i--;
          count -= 1;
        }
      }

      console.log(state.imageFile);
      console.log(state.imageURL);
      console.log("------------------");
    };

    const handleInsert = async () => {
      if (state.title.length > 100) {
        alert("제목 100글자이하로 입력해주세요");
        return false;
      }
      if (state.title.length <= 2) {
        alert("제목 3글자이상 입력해주세요");
        return false;
      }
      if (state.content.length <= 2) {
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
        url = `/TEAMMOVIE/api/board/insert`;
      } else {
        url = `/TEAMMOVIE/api/board/insertnoimg`;
      }
      body.append("btitle", state.title);
      body.append("bcontent", state.content);
      body.append("btype", state.type.value);

      const response = await axios.post(url, body, { headers });

      console.log(response.data);

      if (response.data.status === 200) {
        router.push({ name: "BoardView" });
      }
    };

    return { state, handleChange, handleRemove, handleInsert, boardmove };
  },
};
</script>

<style scoped>
.writecon {
  border: 1px solid #aaaaaa;
  border-radius: 0.5em;
  padding: 20px;
  width: 700px;
  margin-left: auto;
  margin-right: auto;
  padding-top: 40px;
}
.back {
  padding-top: 100px;
}
</style>