<!--
 * @Author: Crayon
 * @Date: 2021-06-26 15:06:14
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-26 15:06:15
-->
<template>
  <div id="app-container">
    <div class="loading-mask">
      <div class="loading-gif"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Loading",
};
</script>

<style scoped>
.loading-mask {
  width: 100vw;
  height: 100vh;
  background: rgb(255, 241, 206);
  position: absolute;
  z-index: 999;
  opacity: 0.8;
}
.loading-gif {
  width: 200px;
  height: 200px;
  position: absolute;
  z-index: 999;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background: url("~@/assets/loading.gif") center center;
  background-repeat: no-repeat;
  background-size: 200% 200%;
}
</style>