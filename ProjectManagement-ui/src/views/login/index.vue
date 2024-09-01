<template class="body-zone">
  <div style="width: 100vw;height: 100vh; overflow: hidden;display: flex">
    <div style="flex: 1">
      <el-image style="width: 100%;height: 100%;" fit="cover"
                src="https://ts1.cn.mm.bing.net/th/id/R-C.9899e77232ba13022901adddea169ac7?rik=TSzj4mC46BmVUQ&riu=http%3a%2f%2fp0.so.qhmsg.com%2ft01b1c0b6030a1051a0.jpg&ehk=2l3EwqFcjJoqU1OwuCtSb2BCIFaTUXap2bZiAdynPSk%3d&risl=&pid=ImgRaw&r=0"/>
    </div>
    <div class="left-title">
      <span style="font-size: 50px; font-weight: bold">ProjectManagement</span><br/>
      <span style="font-size: 20px;">ProjectManagement -自动化部署流水线</span>
    </div>
    <div style="width: 400px;background-color: white;">
      <router-view />
    </div>
  </div>
</template>

<script setup>
import { onMounted, onUnmounted } from 'vue';
onMounted(() => {
  document.body.style.height = "100vh";
  document.body.style.overflow = "hidden";
  document.body.style.width = "100vw";
  document.body.style.display = "flex";
  document.body.style.margin = "0";
});

onUnmounted(() => {
  // 恢复默认样式
  document.body.style.height = "";
  document.body.style.overflow = "";
  document.body.style.width = "";
  document.body.style.display = "";
  document.body.style.margin = "";
});
</script>

<style scoped>
.left-title{
  position: absolute;
  bottom: 30px;
  left: 30px;
  color: white;
  text-shadow: 0 0 10px black;
}
</style>