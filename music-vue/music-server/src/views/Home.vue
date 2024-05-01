<template>
<div class="home">
  <swiper/>
  <div class="section" v-for="(item,index) in songList" :key="index">
    <div class="section-title">{{item.name}}</div>
    <content-list :contentList="item.list"></content-list>
  </div>
</div>
</template>

<script>
import Swiper from "@/components/Swiper";
import {getAllSinger,getAllSongList} from "@/api";
import ContentList from "@/components/ContentList";
export default {
  name: "Home",
  components:{
    ContentList,
    Swiper,
  },
  created() {
    this.getSongList();
    this.getSinger();
  },
  data(){
    return{
      songList:[
        {name:"歌单",list:[]},
        {name:"歌手",list:[]},
      ]
    }
  },
  methods:{
    getSongList(){
      getAllSongList().then((res)=>{
        this.songList[0].list = res.slice(0,10);
        console.log(res);
      }).catch((err)=> {
        console.log(err);
      })
    },
    getSinger(){
      getAllSinger().then((res)=>{
        this.songList[1].list = res.slice(0,10);
        console.log(res);
      }).catch((err)=> {
        console.log(err);
      })
    },
  }
}
</script>

<style scoped>
.section-title{
  font-size: 25px;
  font-weight: bolder;
  color: #2c3e50;
}
.section{
  width: 100%;
}
</style>
