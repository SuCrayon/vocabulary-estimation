<template>
  <div id="app-container">
    <div id="main">
      <div class="wordtext">
        <div class="txt">
          <!-- 提示判断单词 -->
          <p id="notices">
            <span>Judge the following words</span>
          </p>
          <!-- 单词显示位置 -->
          <div id="word">vocabulary</div>
        </div>
        <!-- 认识和不认识的按钮 -->
        <div class="buttons">
          <button class="bttn-float bttn-md" id="know" @click="know">
            know
          </button>
          <button class="bttn-float bttn-md" id="unknow" @click="unknow">
            unknow
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Main",
  data() {
    return {
      wordList: [],
      word: "123",
    };
  },
  created() {},
  methods: {
    getWords() {},
    know() {
      console.log("know");
    },
    unknow() {
      console.log("unknow");
    },
  },
};
</script>

<style scoped>
@import url("../../styles/style.css");
@import url("../../styles/bttn.min.css");
@import url("../../styles/reset.css");
</style>