<template>
<div class="setting">
  <div class="leftCol">
    <div class="setting-header" style="margin-top: 10px; color: #9c9fa4;font-weight: bolder">设置</div>
    <ul class="setting-aside">
      <li v-for="(item,index) in settingList" :key="index" :class="{activeColor:activeName==item.name}"
      @click="handleClick(item)">
        {{ item.name }}
      </li>
    </ul>
  </div>
  <div class="rightCol">
    <component :is="componentSrc"></component>
  </div>
</div>
</template>

<script>
import Info from "@/components/Info";
import Upload from "@/components/Upload";
export default {
  name: "Setting",
  components:{
    Info,Upload
  },
  data(){
    return{
      settingList:[
        {name:'个人信息',path:'Info'},
        {name:'修改头像',path:'Upload'},
      ],
      activeName:'个人信息',
      componentSrc:'Info',
    }
  },
  methods:{
    handleClick(item){
      this.activeName=item.name;
      this.componentSrc=item.path;
    }
  }
}
</script>

<style scoped>
.setting{
  display: grid;
  grid-template-columns: 1fr 6fr;
}
.setting-aside{
  list-style: none;
  padding: 0;
  cursor: pointer;
}
.activeColor{
  background-color: #409EFF;
  color: white;
}
.setting-aside li{
  height: 40px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 10px;
  margin-left: 5px;
  margin-right: 5px;
}
</style>
