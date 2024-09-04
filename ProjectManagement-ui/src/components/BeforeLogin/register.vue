<template>

  <div style="text-align: center; margin: 0 20px;">
    <div style="margin-top: 100px;">
      <span style="font-size: 50px;font-weight: bold">注册</span>
      <div class="input_up">
        <el-input   placeholder="请输入用户名" style="margin-top: 20px; height: 50px;" clearable>
          <template #prefix>
            <el-icon size="30"><UserFilled /></el-icon>
          </template>
        </el-input>
        <el-input   placeholder="请输入密码" style="margin-top: 20px; height: 50px;"  type="password" show-password clearable>
          <template #prefix>
            <el-icon size="30"><Lock /></el-icon>
          </template>
        </el-input>
        <el-input   placeholder="再次输入密码" style="margin-top: 20px; height: 50px;"  type="password" show-password clearable>
          <template #prefix>
            <el-icon size="30"><Lock /></el-icon>
          </template>
        </el-input>
      </div>
      <div style="margin-top: 20px;">
        <el-button  type="primary" style="width: 100%;height: 50px;font-size: 20px;">注册</el-button>
      </div>
      <div style="margin-top: 20px;">
        <el-button @click="router.push('/')" type="danger" style="width: 100%;height: 50px;font-size: 20px;">返回登录</el-button>
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
</script>

<style scoped>

</style>