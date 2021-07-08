<!--
 * @Author: Crayon
 * @Date: 2021-07-02 22:01:24
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-07-03 21:19:26
-->
<template>
  <div id="app-container">
    <matching v-show="matching" :playerNum="playerNum" @cancel="cancelMatch" />
  </div>
</template>

<script>
import wordBattleGameApi from "@/api/wordBattleGame";
export default {
  components: {
    Matching: () => import("@/components/Matching/index"),
  },
  data() {
    return {
      matching: true,
      playerNum: {
        currentNum: 0,
        needNum: 0,
      },
      socket: {},
      transFrame: {
        cmd: 1,
      },
    };
  },
  created() {
    this.socketInit();
  },
  destroyed() {
    // 主动关闭socket
    this.socket.close();
  },
  methods: {
    sendMessage(transFrame) {
      this.socket.send(JSON.stringify(transFrame));
    },
    startMatch() {
      this.sendMessage(this.transFrame);
    },
    cancelMatch() {
      this.transFrame.cmd = 2;
      this.sendMessage(this.transFrame);
    },
    socketInit() {
      this.socket = wordBattleGameApi.createConn();
      if (this.socket == null) {
        this.$message.error("您的浏览器似乎不支持此功能");
        return;
      }
      // 监听socket连接
      this.socket.onopen = this.onOpen;
      // 监听socket关闭
      this.socket.onclose = this.onClose;
      // 监听socket消息
      this.socket.onmessage = this.onMessage;
      // 监听socket错误信息
      this.socket.onerror = this.onError;
    },
    updatePlayerNum(data) {
      this.playerNum.currentNum = data.currentNum;
      this.playerNum.needNum = data.needNum;
    },
    onOpen() {
      this.startMatch();
    },
    onClose() {},
    onMessage({ data }) {
      console.log(data);
      let resp = JSON.parse(data);
      if (resp.description) {
        this.$message.info(resp.description);
      }
      this.updatePlayerNum(resp);
    },
    onError() {},
  },
};
</script>

<style>
</style>