<!--
 * @Author: Crayon
 * @Date: 2021-06-26 10:21:06
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-26 23:10:56
-->
<template>
  <div id="app-container">
    <!-- 使用element-ui布局容器快速布局 -->
    <loading v-show="loading" />
    <div class="container">
      <el-container v-if="wordList.length > 0">
        <el-header :height="'auto'">
          <el-row type="flex" justify="end">
            <label class="switch-label">显示答案</label>
            <el-switch v-model="showAnswer"></el-switch>
          </el-row>
          <el-row>
            <el-progress
              :percentage="(index / wordList.length) * 100"
              :show-text="false"
            ></el-progress>
            <el-row class="word-bar">
              <el-col :span="12" v-if="index > 0">{{
                wordList[index - 1].word
              }}</el-col>
              <el-col
                :offset="index <= 0 ? 12 : 0"
                :span="12"
                v-if="index < wordList.length - 1"
                style="text-align: right"
                >{{ wordList[index + 1].word }}</el-col
              >
            </el-row>
          </el-row>
        </el-header>
        <el-main>
          <div class="showing-word">
            <span v-if="result === -1">{{ wordList[index].word }}</span>
            <span v-else>Your vocabulary is {{ result }}</span>
          </div>
        </el-main>
        <el-footer :height="'auto'">
          <el-row :gutter="10" class="options">
            <el-col
              :span="12"
              v-for="(option, idx) in wordList[index].options"
              :key="idx"
            >
              <el-button plain @click="selectOption(option)">{{
                option
              }}</el-button>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <el-button type="info" plain @click="dontKnow">不认识</el-button>
            </el-col>
          </el-row>
        </el-footer>
      </el-container>
    </div>
  </div>
</template>

<script>
import Loading from "@/components/loading/index";
import estimationApi from "@/api/estimation";
export default {
  name: "M_Estimation",
  components: {
    Loading,
  },
  data() {
    return {
      loading: false,
      showAnswer: false,
      index: 0,
      wordList: [],
      wordNums: [],
      levelRightCounts: [],
      result: -1,
    };
  },
  created() {
    this.getEstimationWords();
  },
  methods: {
    dontKnow() {
      if (this.index < this.wordList.length - 1) {
        this.index++;
      } else {
        console.log(this.levelRightCounts);
        const SCALE = 1000;
        let result = 0;
        // 计算每一个级别答对的比率
        let rightRate = 0;
        for (let i = 0; i < this.wordNums.length; i++) {
          rightRate = this.levelRightCounts[i] / this.wordNums[i];
          if (i > 0) {
            /* 
            如果题目不属于第一级，则将上一级答对的比率和本级相乘
            然后用这个比率乘以固定的预估值作为该级别的预估次数
          */
            let lastRightRate =
              this.levelRightCounts[i - 1] / this.wordNums[i - 1];
            if (lastRightRate === 0) {
              lastRightRate = 0.1;
            }
            rightRate *= lastRightRate;
          }
          // 如果不是第一级，则将上一级答对的比率乘以预估值作为该级别的预估词数
          console.log(`第${i + 1}级别: ${SCALE * rightRate}`);
          result += SCALE * rightRate;
        }
        console.log("result: ", result);
        this.result = parseInt(result);
      }
    },
    selectOption(option) {
      // 答对
      if (option === this.wordList[this.index].chMeaning) {
        this.levelRightCounts[this.wordList[this.index].level - 1]++;
      }
      this.dontKnow();
    },
    async getEstimationWords() {
      this.loading = true;
      await estimationApi
        .listEstimationWords()
        .then((resp) => {
          this.wordList = resp.data.item.estimationWordList;
          this.wordNums = resp.data.item.wordNums;
        })
        .catch((err) => console.log(err))
        .finally(() => {
          // 1秒后加载完成
          this.loading = false;
        });

      // 初始化
      this.levelRightCounts = new Array(this.wordNums.length).fill(0);
    },
  },
};
</script>

<style scoped>
.el-button:active {
  border: 1px solid #dcdfe6 !important;
  color: #606266 !important;
}
.container {
  height: 100vh;
  /* background-image: linear-gradient(
    to bottom right,
    rgb(255, 255, 255),
    rgb(158, 172, 255)
  ); */
  background: url("~@/assets/background.png") center center;
  background-repeat: no-repeat;
  background-size: 100% 100%;
}
.el-container {
  height: 100vh;
}

.el-header {
  padding-top: 10px;
}
.el-row {
  margin-bottom: 10px;
}
.el-row:last-child {
  margin-bottom: 0;
}

.el-footer {
  padding-bottom: 10px;
}

.el-footer .el-col {
  /* height: 8vh;
  max-height: 10vh; */
}

.el-footer .el-button {
  width: 100%;
  height: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  /* white-space: pre-wrap; */
  /* word-wrap: break-word; */
  font-size: 2vh;
}

.switch-label {
  margin-right: 10px;
  color: grey;
  font-size: 1.2rem;
}

.word-bar {
  margin-top: 5px;
  color: grey;
  font-size: 0.8rem;
}

.showing-word {
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2.4rem;
  font-family: wordfont;
}

.options {
  margin-bottom: -10px;
}

.options .el-col {
  margin-bottom: 10px;
}
</style>