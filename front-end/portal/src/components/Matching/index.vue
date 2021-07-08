<!--
 * @Author: Crayon
 * @Date: 2021-07-03 10:31:30
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-07-03 19:57:19
-->
<template>
  <div id="app-container">
    <div class="matching-mask">
      <div class="matching-text">
        <div>匹配中</div>
        <div>{{ playerNum.currentNum }}/{{ playerNum.needNum }}</div>
      </div>
      <div class="matching-gif"></div>
      <div class="btn danger" @click="cancel">取消</div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Matching",
  props: {
    playerNum: {
      type: Object,
    },
  },
  data() {
    return {};
  },
  methods: {
    cancel() {
      this.$emit("cancel");
    },
  },
};
</script>

<style scoped>
.matching-mask {
  width: 100vw;
  height: 100vh;
  background: rgb(255, 241, 206);
  position: absolute;
  z-index: 999;
  /* opacity: 0.8; */
}
.matching-gif {
  width: 50vmin;
  height: 50vmin;
  position: absolute;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: url("~@/assets/matching.gif") center center;
  background-repeat: no-repeat;
  background-size: 150% 150%;
}

.matching-text {
  position: absolute;
  top: 10vh;
  left: 50%;
  transform: translateX(-50%);
  font-size: 2rem;
  font-weight: 600;
  color: #e74c3c;
}

.matching-text div {
  text-align: center;
  margin: 2vh 0;
}

.btn {
  position: absolute;
  width: 5rem;
  bottom: 10vh;
  left: 50%;
  transform: translateX(-50%);
}
</style>