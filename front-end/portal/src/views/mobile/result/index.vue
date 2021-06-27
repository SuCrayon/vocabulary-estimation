<!--
 * @Author: Crayon
 * @Date: 2021-06-27 20:48:16
 * @Last Modified by: Crayon
 * @LastEditTime: 2021-06-27 21:59:05
-->
<template>
  <div id="app-container">
    <loading v-show="loading" />
    <el-container>
      <el-main>
        <div class="card">
          <h1 class="headline">你的词汇量</h1>
          <el-row v-if="numbers.length > 0" :gutter="4" class="result">
            <el-col v-for="(number, idx) in numbers" :key="idx" :span="6">
              <transition name="el-zoom-in-center">
                <div class="number-block">{{ number }}</div>
              </transition>
            </el-col>
          </el-row>
        </div>
        <el-row>
          <el-col style="margin-bottom: 10px">
            <div class="btn primary" @click="goBack">重新测试</div>
          </el-col>
          <el-col>
            <div class="btn secondary" @click="goBack">返回</div>
          </el-col>
        </el-row>
      </el-main>
    </el-container>
  </div>
</template>

<script>
export default {
  name: "M_Result",
  components: {
    Loading: () => import("@/components/Loading/index"),
  },
  data() {
    return {
      result: 0,
      numbers: [],
      loading: false,
    };
  },
  created() {
    this.loading = true;
    const loadingTimer = setInterval(() => {
      this.loading = false;
      clearInterval(loadingTimer);
    }, 2000);
    this.setResult();
  },
  methods: {
    goBack() {
      this.$router.push({ name: "M_Estimation" });
    },
    setNumbers() {
      if (this.result > 9999) {
        this.result = 9999;
      }
      this.numbers.push(...(this.result + ""));
      while (this.numbers.length < 4) {
        // 位数不够要补零
        this.numbers.unshift("0");
      }
    },
    setResult() {
      let result = parseInt(this.$route.params.result);
      console.log(result);
      if (result) {
        this.result = result;
        this.setNumbers();
      } else {
        // 非法参数跳转回到测试页
        this.$router.push({ name: "M_Estimation" });
      }
    },
  },
};
</script>

<style scoped>
.card {
  padding: 20px;
  margin-bottom: 20px;
  box-shadow: 0px 0px 10px 5px rgb(244, 244, 244);
}
.headline {
  font-weight: 800;
  font-size: 1.2rem;
  margin-bottom: 20px;
}
.number-block {
  background-color: rgb(50, 60, 80);
  color: rgb(255, 255, 250);
  padding: 20px 0px;
  text-align: center;
  font-size: 8vh;
  font-weight: bold;
  border-radius: 8px;
  border: none;
}
.btn {
  width: 100%;
  display: inline-block;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  text-align: center;
  font-weight: 600;
  padding: 12px 8px;
  box-sizing: border-box;
}

.btn.primary {
  background-color: rgb(15, 75, 225);
  color: #fff;
}

.btn.secondary {
  background-color: rgb(220, 240, 250);
  color: rgb(10, 50, 200);
}
</style>