<!--
 * @Author: Crayon
 * @Date: 2021-06-24 21:49:38
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-30 18:37:32
-->
<template>
  <div id="app-container">
    <div id="main" v-if="wordList.length > 0" v-loading="loading">
      <!-- 进度条 -->
      <el-progress
        :stroke-width="12"
        :percentage="(index / wordList.length) * 100"
        :show-text="false"
      ></el-progress>
      <div class="wordtext">
        <div class="txt">
          <!-- 提示判断单词 -->
          <p id="notices">
            <span>Judge the following words</span>
          </p>
        </div>
        <!-- 单词显示位置 -->
        <div id="word">
          {{ wordList[index].word }}
          <audio
            :src="`https://dict.youdao.com/dictvoice?audio=${wordList[index].word}&type=1`"
            autoplay
          ></audio>
        </div>
        <!-- 按钮 -->
        <div
          v-for="(option, idx) in wordList[index].options"
          :key="idx"
          style="text-align: center"
          :style="{ top: `${60 + idx * 10}%` }"
          class="buttons"
        >
          <button
            style="
              width: 75%;
              text-overflow: ellipsis;
              overflow: hidden;
              white-space: nowrap;
            "
            class="bttn-float bttn-md"
            @click="selectOption(option)"
          >
            {{ option }}
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import estimationApi from "@/api/estimation";
export default {
  name: "Estimation",
  data() {
    return {
      loading: false, // 是否加载中
      index: 0, // 当前显示单词在单词列表的索引
      wordList: [],
      wordNums: [],
      levelRightCounts: [],
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
            this.$router.push({ name: "Result", params: { result } });
          })
          .catch((err) => console.log(err))
          .finally(() => {
            this.loading = false;
          });
      }
    },
    selectOption(option) {
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
@import url("../../../styles/bttn.min.css");
@import url("../../../styles/estimation.css");
</style>