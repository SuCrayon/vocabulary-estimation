<!--
 * @Author: Crayon
 * @Date: 2021-07-02 22:01:24
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-07-03 21:19:26
-->
<template>
  <div id="app-container">
    <matching v-show="matching" :playerNum="playerNum" @cancel="cancelMatch" />
    <div>{{ this.gameInfo.score.self }}</div>
    <div>{{ this.gameInfo.word }}</div>
    <div>{{ this.gameInfo.options }}</div>
  </div>
</template>

<script>
import wordBattleGameApi from "@/api/wordBattleGame";
export default {
  name: "M_WordBattleGame",
  components: {
    Matching: () => import("@/components/matching/index"),
  },
  data() {
    return {
      matching: true,
      playerNum: {
        currentNum: 0,
        needNum: 0,
      },
      socket: {},
      gameInfo: {
        score: {
          self: 0
        },
        word: "",
        options: []
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
      this.sendMessage({
        cmd: 1
      });
    },
    cancelMatch() {
      this.sendMessage({
        cmd: 2
      });
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
      if (resp.cmd == 101) {
        if (resp.description) {
          this.$message.info(resp.description);
        }
        this.updatePlayerNum(resp);
      } else if (resp.cmd == 103) {
        this.matching = false;
      } else if (resp.cmd == 104) {
        this.gameInfo.score.self = resp.gameInfo.score.self;
        this.gameInfo.word = resp.gameInfo.word;
        this.gameInfo.options = resp.gameInfo.options;
      }
    },
    onError() {},
  },
};
</script>

<style>
</style>