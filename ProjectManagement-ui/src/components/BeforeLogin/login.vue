<template>

  <div style="text-align: center; margin: 0 20px;">
    <div style="margin-top: 150px;">
      <span style="font-size: 50px;font-weight: bold">登录</span>
      <div class="input_up">
        <el-input  v-model="form.username" placeholder="请输入用户名" style="margin-top: 20px; height: 50px;" clearable>
          <template #prefix>
            <el-icon size="30"><UserFilled /></el-icon>
          </template>
        </el-input>
        <el-input  v-model="form.password" placeholder="请输入密码" style="margin-top: 20px; height: 50px;"  type="password" show-password clearable>
          <template #prefix>
            <el-icon size="30"><Lock /></el-icon>
          </template>
        </el-input>
      </div>
      <div style="margin-top: 10px">
        <el-row>
          <el-col :span="12" :style="{'text-align':'left'}">
            <el-checkbox v-model="form.rememberMe" label="记住密码(5天)" size="large" />
          </el-col>
          <el-col :span="12" :style="{'text-align':'right'}">
            <el-link>忘记密码?</el-link>
          </el-col>
        </el-row>
      </div>
      <div style="margin-top: 20px;">
        <el-button @click="login()" type="primary" style="width: 100%;height: 50px;font-size: 20px;">登录</el-button>
      </div>
      <div style="margin-top: 20px;">
        <el-button @click="router.push('/register')" color="#626aef" :dark="isDark" style="width: 100%;height: 50px;font-size: 20px;">注册</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {UserFilled,Lock} from "@element-plus/icons-vue"
import {reactive} from "vue";
import {ElMessage} from "element-plus";
import {post,get} from "@/net";
import router from "@/router";

const form = reactive({
        username: '',
        password: '',
        rememberMe: false,
});

const login = () => {
        if (!(form.username && form.password)){
          ElMessage.warning("请输入用户名或密码！");
          return;
        }
        post("/auth/login",form,
            (data)=>{
                ElMessage.success(data.msg);
                router.push('/index')
            }
      )
}



</script>

<style scoped>

/*
 *将类名为 input_up 中的 el-input__inner 元素字体设置为 "Microsoft"。
 *设置字体大小为 30px，并使用 !important 确保优先级。
 */
:deep(.input_up  .el-input__inner){
  font-family:"Microsoft" !important;
  font-size:15px !important;
}
</style>