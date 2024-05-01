<template>
<div class="content-list">
  <ul class="section-content">
    <li class="content-item" v-for="(item,index) in contentList" :key="index">
      <div class="con">
        <img class="item-img" :src="attachImageUrl(item.pic)" @click="goAlbum(item,item.name)">
        <div class="overlay"><i class="el-icon-video-play"/></div>
        <span class="item-name">{{item.name||item.title}}</span>
      </div>
    </li>
  </ul>
</div>
</template>

<script>
import {mixin} from "@/mixins";

export default {
  name: 'content-list',
  mixins:[mixin],
  props: ['contentList'],
  methods:{
    goAlbum(item,type){
      this.$store.commit("setTempList",item);
      if(type){
        this.$router.push({path:`/singer-album/${item.id}`});
      }else{
        this.$router.push({path:`/song-list-album/${item.id}`});
      }
    }
  }
}
</script>

<style scoped>
.section-content{
  list-style: none;
  padding: 0;
}
.content-item{
  display: inline-block;
  height: 200px;
  width: 200px;
  margin-right: 5px;
  margin-left: 5px;
  margin-top: 5px;
}
.con{
  display: inline-block;
  height: 100%;
  width: 100%;
  border-radius: 10px;
  position: relative;
}
.con:hover{
  box-shadow: rgba(0, 0, 0, 0.3) 0px 19px 38px, rgba(0, 0, 0, 0.22) 0px 15px 12px;
}
.con img{
  height: 100%;
  width: 100%;
  border-radius: 10px;
}

.overlay{
  position: absolute;
  top: 75px;
  left: 75px;
  color: white;
  font-size: 50px;
}
</style>
