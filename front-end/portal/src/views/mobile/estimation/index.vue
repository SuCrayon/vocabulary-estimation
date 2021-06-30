<!--
 * @Author: Crayon
 * @Date: 2021-06-26 10:21:06
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-30 18:37:46
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
            <transition name="el-fade-in-linear">
              <div v-if="optionSelect.length === 0">
                <span>{{ wordList[index].word }}</span>
                <audio
                  :src="`https://dict.youdao.com/dictvoice?audio=${wordList[index].word}&type=1`"
                  autoplay
                ></audio>
              </div>
            </transition>
          </div>
        </el-main>
        <el-footer :height="'auto'">
          <el-row :gutter="10" class="options">
            <div v-if="optionSelect.length === 0">
              <el-col
                :span="12"
                v-for="(option, idx) in wordList[index].options"
                :key="idx"
              >
                <div class="option-btn" @click="selectOption(option)">
                  <span>{{ option }}</span>
                </div>
              </el-col>
            </div>
            <div v-else>
              <el-col
                :span="12"
                v-for="(option, idx) in optionCheckList"
                :key="idx"
              >
                <div
                  v-if="
                    (optionSelect === option &&
                      option === wordList[index - 1].chMeaning) ||
                    (showAnswer === true &&
                      option === wordList[index - 1].chMeaning)
                  "
                  class="option-btn"
                  style="border-color: #67c23a"
                >
                  <i class="el-icon-circle-check" style="color: #67c23a"></i>
                </div>
                <div
                  v-else-if="
                    optionSelect === option &&
                    option !== wordList[index - 1].chMeaning
                  "
                  class="option-btn"
                  style="border-color: #f56c6c"
                >
                  <i class="el-icon-circle-close" style="color: #f56c6c"></i>
                </div>
                <div v-else class="option-btn">
                  <i
                    class="el-icon-remove-outline"
                    style="visibility: hidden"
                  ></i>
                </div>
              </el-col>
            </div>
          </el-row>
          <el-row :gutter="10">
            <el-col>
              <div class="option-btn" @click="dontKnow">不认识</div>
            </el-col>
          </el-row>
        </el-footer>
      </el-container>
    </div>
  </div>
</template>

<script>
import estimationApi from "@/api/estimation";
export default {
  name: "M_Estimation",
  components: {
    Loading: () => import("@/components/Loading/index"),
  },
  data() {
    return {
      loading: false,
      showAnswer: false,
      index: 0,
      wordList: [],
      wordNums: [],
      levelRightCounts: [],
      optionSelect: "",
      optionCheckList: [],
    };
  },
  created() {
    this.getEstimationWords();
  },
  methods: {
    nextWordOrCalc() {
      if (this.index < this.wordList.length - 1) {
        this.index++;
      } else {
        this.loading = true;
        console.log(this.levelRightCounts);
        estimationApi
          .calculate(this.levelRightCounts)
          .then((resp) => {
            let result = resp.data.result;
            this.$router.push({ name: "M_Result", params: { result } });
          })
          .catch((err) => console.log(err))
          .finally(() => {
            this.loading = false;
          });
      }
    },
    updateOptionSelect(option) {
      this.optionSelect = option;
      this.optionCheckList = this.wordList[this.index].options;
      // 1秒后清空
      const answerCheckTimer = setInterval(() => {
        this.optionSelect = "";
        this.optionCheckList = [];
        clearInterval(answerCheckTimer);
      }, 1000);
    },
    dontKnow() {
      if (this.optionSelect !== "#") {
        this.updateOptionSelect("#");
        this.nextWordOrCalc();
      }
    },
    selectOption(option) {
      this.updateOptionSelect(option);
      // 答对
      if (option === this.wordList[this.index].chMeaning) {
        this.levelRightCounts[this.wordList[this.index].level - 1]++;
      }
      this.nextWordOrCalc();
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
          // 2秒后加载完成
          const loadingTimer = setInterval(() => {
            this.loading = false;
            clearInterval(loadingTimer);
          }, 2000);
        });

      // 初始化
      this.levelRightCounts = new Array(this.wordNums.length).fill(0);
    },
  },
};
</script>

<style scoped>
.container {
  height: 100vh;
  background: url("~@/assets/background.png") center center;
  background-repeat: no-repeat;
  background-size: 100% 100%;
}

.el-container {
  height: 100%;
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

.option-btn {
  width: 100%;
  display: inline-block;
  background: #fff;
  border: 1px solid #dcdfe6;
  color: #606266;
  border-radius: 4px;
  text-align: center;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  font-size: 2vh;
  padding: 12px 8px;
  line-height: 1.4rem;
  box-sizing: border-box;
}
</style>