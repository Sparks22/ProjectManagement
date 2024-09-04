<template>
  <div style="flex: 1">
  <el-menu
      :default-active="activeIndex"
      class="el-menu-vertical-demo"
      mode="vertical"
      :collapse="isCollapse"
      @open="menuHandlerOpen"
      @close="menuHandlerClose"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b"
  >
   <div style="text-align: center">
      <el-icon size="30px"><VideoPlay /></el-icon>
      <span v-if="!isCollapse" style="font-size: 30px">  项目管理</span>
   </div>
    <el-menu-item index="1">
      <el-icon><House /></el-icon>
      <template #title>首页</template>
    </el-menu-item>
    <el-menu-item index="2">
      <el-icon><Share /></el-icon>
      <template #title>Git凭据</template>
    </el-menu-item>
    <el-menu-item index="3">
      <el-icon><More /></el-icon>
      <template #title>服务器节点</template>
    </el-menu-item>
    <el-menu-item index="4">
      <el-icon><Operation /></el-icon>
      <template #title>工程列表</template>
    </el-menu-item>
    <el-sub-menu index="5">
      <template #title>
        <el-icon><Management /></el-icon>
        <span>系统管理</span>
      </template>
        <el-menu-item-group>
          <el-menu-item index="5-1">用户管理</el-menu-item>
          <el-menu-item index="5-2">权限管理</el-menu-item>
          <el-menu-item index="5-3">角色管理</el-menu-item>
          <el-menu-item index="5-4">菜单管理</el-menu-item>
        </el-menu-item-group>
    </el-sub-menu>
    <div class="divider-button">
      <el-button @click="closeCollapse" v-if="!isCollapse" type="primary" :icon="ArrowLeft"></el-button>
      <el-button @click="openCollapse" v-else type="primary" :icon="ArrowRight"></el-button>
    </div>
  </el-menu>
<!--  <el-divider direction="vertical" />-->
  </div>
</template>

<script setup>
import {ref,reactive} from "vue";
import {ElMessage} from "element-plus";
import router from "@/router";
import {VideoPlay ,House,More,Operation,Management,ArrowRight,ArrowLeft,Share} from '@element-plus/icons-vue'

const activeIndex = ref('1');

const isCollapse = ref(false);


const menuHandlerOpen = (key, keyPath) => {
  console.log(key, keyPath);
};
const menuHandlerClose = (key, keyPath) => {
  console.log(key, keyPath);
};

const closeCollapse = () => {
  isCollapse.value = true;
};
const openCollapse = () => {
  isCollapse.value = false;
};


</script>

<style scoped>
.el-menu-vertical-demo:not(.el-menu--collapse) {
  width: 235px;
  min-height: 100vh;
  position: relative;
}
.el-menu-vertical-demo.el-menu--collapse {
  min-height: 100vh;
  position: relative;
  /* 其他针对折叠菜单的样式 */
}

.divider-button{
  position: absolute;
  bottom: 0;
  width: 100%;
  justify-content: center;
  display: flex;
  align-items: center;
  padding: 0;
}



</style>