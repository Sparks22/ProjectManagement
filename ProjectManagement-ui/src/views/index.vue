<template>
  <div  style="text-align: center;">
    <div style="font-size: 50px">index</div>
    <div class="logout">
      <el-button @click="logout" type="danger" :icon="CircleCloseFilled"  >退出登录</el-button>
    </div>
  </div>
</template>

<script setup>
import {CircleCloseFilled} from '@element-plus/icons-vue'
import {ElMessage} from "element-plus";
import {post,get} from "@/net";
import router from "@/router";

const logout = () => {
  get("/auth/logout",
      (data)=>{
        ElMessage.success(data.msg);
        router.push('/')
      }
  )
}
</script>

<style scoped>
.logout{
  position: absolute;
  top: 5px;
  right: 5px;
}
</style>