<!--
 * @Author: Crayon
 * @Date: 2021-06-24 21:49:38
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-26 10:29:53
-->
<template>
  <div id="app-container">
    <div class="main">
      <div class="getscore">
        <div id="txt">
          <p>Your vocabulary is</p>
        </div>
        <div id="score">
          <p>{{ result }}</p>
        </div>
        <div id="backbutton">
          <button class="bttn-float bttn-lg" @click="goBack">back</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Result",
  data() {
    return {
      result: 0,
    };
  },
  created() {
    this.setResult();
  },
  methods: {
    setResult() {
      let result = parseInt(this.$route.params.result);
      console.log(result);
      if (result) {
        this.result = result;
      } else {
        // 非法参数跳转回到测试页
        this.$router.push({ name: "Estimation" });
      }
    },
    goBack() {
      this.$router.push({ name: "Estimation" });
    },
  },
};
</script>

<style scoped>
@import url("../../../styles/bttn.min.css");
@import url("../../../styles/result.css");
</style>