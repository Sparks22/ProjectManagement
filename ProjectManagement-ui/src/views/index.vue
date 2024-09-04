<template>
  <div style="display: flex">
  <router-view />

    <div class="logout">
      <el-button @click="logout" type="danger" :icon="CircleCloseFilled"  >退出登录</el-button>
    </div>
  </div>
</template>

<script setup>
import {ref,reactive} from "vue";
import {ElMessage} from "element-plus";
import {post,get} from "@/net";
import router from "@/router";
import {CircleCloseFilled ,Management} from '@element-plus/icons-vue'

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