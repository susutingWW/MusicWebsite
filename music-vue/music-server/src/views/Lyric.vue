<template>
<div class="song-lyric">
  <h1 class="lyric-title">歌词</h1>
  <ul class="has-lyric" v-if="lyr.length" key="index">
    <li class="li" v-for="(item,index) in lyr" v-bind:key="index">
      {{item[1]}}
    </li>
  </ul>
  <div v-else class="no-lyric" key="no-lyric">
    <span>暂无歌词</span>
  </div>
</div>
</template>

<script>
import {mapGetters} from "vuex";
import {mixin} from "@/mixins";

export default {
  name: "Lyric",
  mixins:[mixin],
  data(){
    return{
      lyr:[]
    }
  },
  computed:{
    ...mapGetters([
        'curTime',
        'id',
        'lyric',
        'listIndex',
        'listOfSongs',
    ])
  },
  created() {
    this.lyr=this.lyric;
  },
  watch:{
    id:function (){
      this.lyr=this.parseLyric(this.listOfSongs[this.listIndex].lyric);
    },
    curTime:function(){
      if(this.lyr.length>0){
        for(let i=0;i<this.lyr.length;i++){
          if(this.curTime>=this.lyr[i][0]){
            for(let j=0;j<this.lyr.length;j++){
              document.querySelectorAll('.has-lyric li')[j].style.color='#a8a8a8';
              document.querySelectorAll('.has-lyric li')[j].style.fontSize='15px';
            }
            if(i>=0){
              document.querySelectorAll('.has-lyric li')[i].style.color='#3d3d3d';
              document.querySelectorAll('.has-lyric li')[i].style.fontSize='25px';
            }
          }
        }
      }
    }
  }
}
</script>

<style scoped>
.song-lyric{
  margin: 20px auto auto;
  width: 700px;
  background-color: #eef1f6;
  border-radius: 12px;
  padding:0 20px 50px 20px;
}
.lyric-title{
  text-align: center;
  height: 60px;
  line-height: 60px;
  border-bottom: 2px solid black;
}
.has-lyric{
  font-size:18px;
  padding: 30px 0;
  width: 100%;
  min-height: 170px;
  text-align: center;
  list-style: none;
}
.li{
  width: 100%;
  height:40px;
  line-height: 40px;
}
</style>
