<!--
 * @Author: Crayon
 * @Date: 2021-06-24 21:49:38
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-26 10:29:41
-->
<template>
  <div id="app-container">
    <div id="main" v-loading="loading">
      <!-- 进度条 -->
      <el-progress
        :stroke-width="12"
        :percentage="percentage"
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
          {{ showingWord }}
        </div>
        <!-- 认识和不认识的按钮 -->
        <div class="buttons">
          <button class="bttn-float bttn-md" @click="update(true)">know</button>
          <button class="bttn-float bttn-md" @click="update(false)">
            unknow
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
      wordList: [], // 单词列表
      showingWord: "", // 当前应该显示的单词
      percentage: 0,
    };
  },
  created() {
    this.getEstimationWords();
  },
  methods: {
    getEstimationWords() {
      this.loading = true;
      estimationApi
        .listEstimationWords()
        .then((resp) => {
          this.wordList = resp.data.items;
          this.showingWord = this.wordList[0].word;
        })
        .catch((err) => console.log(err))
        .finally(() => {
          this.loading = false;
        });
    },
    updateShowingWord() {
      // 索引没越界
      if (this.index + 1 < this.wordList.length) {
        // 更新显示下一个单词
        this.showingWord = this.wordList[this.index + 1].word;
        return this.index++;
      }
      return -1;
    },
    updatePercentage() {
      // 计算百分比
      this.percentage = (this.index / this.wordList.length) * 100;
    },
    update(flag) {
      console.log(flag === true ? "know" : "unknow");
      // 更新显示单词
      let index = this.updateShowingWord();
      // 没做完
      if (index != -1) {
        this.wordList[index].flag = flag;
      } else {
        this.wordList[this.index].flag = flag;
        // 做完了
        let result = this.estimate();
        this.$router.push({ name: "Result", params: { result } });
      }
      // 更新进度条
      this.updatePercentage();
    },
    estimate() {
      let levelCounts = new Array(6).fill(0);
      let levelRightCounts = new Array(6).fill(0);
      // 统计各级别题目数以及各级别答对数
      this.wordList.forEach((value) => {
        // 认识
        if (value.flag === true) {
          levelRightCounts[value.level - 1]++;
        }
        levelCounts[value.level - 1]++;
      });
      console.log(levelCounts);
      console.log(levelRightCounts);
      const SCALE = 1500;
      let result = 0;
      // 计算每一个级别答对的比率
      let rightRate = 0;
      for (let i = 0; i < 6; i++) {
        rightRate = levelRightCounts[i] / levelCounts[i];
        if (i > 0) {
          /* 
            如果题目不属于第一级，则将上一级答对的比率和本级相乘
            然后用这个比率乘以固定的预估值作为该级别的预估次数
          */
          let lastRightRate = levelRightCounts[i - 1] / levelCounts[i - 1];
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
      return result;
    },
  },
};
</script>

<style scoped>
@import url("../../../styles/bttn.min.css");
@import url("../../../styles/estimation.css");
</style>