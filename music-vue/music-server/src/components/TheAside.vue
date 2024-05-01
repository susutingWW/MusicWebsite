<template>
<transition name="slide-fade">
  <div class="the-aside" v-if="showAside">
    <h2 class="title">播放列表</h2>
    <ul class="menus" ref="list">
      <li v-for="(item,index) in listOfSongs" :key="index" class="liA" :class="{'is-play':id===item.id}"
          @click="toPlay(item.id,item.url,item.pic,item.index,item.name,item.lyric)">
        {{replaceFName(item.name)}}
      </li>
    </ul>
  </div>
</transition>
</template>

<script>
import {mapGetters} from "vuex";

export default {
  name: "TheAside",
  date(){

  },
  computed:{
    ...mapGetters([
        'showAside',
        'listOfSongs',
        'id',
    ])
  },
  mounted() {

  },
  methods:{
    //获取名字前半部分
    replaceLName(str){
      let arr=str.split('-');
      return arr[0];
    },
    //后半部分
    replaceFName(str){
      let arr=str.split('-');
      return arr[1];
    },
    //播放
    toPlay:function (id,url,pic,index,name,lyric){
      console.log('111');
      this.$store.commit('setId',id);
      this.$store.commit('setUrl',this.$store.state.configure.HOST+'/'+url);
      this.$store.commit('setPicUrl',this.$store.state.configure.HOST+'/'+pic);
      this.$store.commit('setListIndex',index);
      this.$store.commit('setTitle',this.replaceFName(name));
      this.$store.commit('setArtist',this.replaceLName(name));
      this.$store.commit('setLyric',this.parseLyric(lyric));
    },
    parseLyric(text){
      let lines=text.split("\n");
      let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
      let result=[];
      if(!(/\[.+\]/.test(text)))
      {
        return [[0,text]];
      }
      while(!pattern.test(lines[0])){
        lines=lines.slice(1);
      }
      for(let item of lines){
        let time=item.match(pattern);
        let value = item.replace(pattern,'');
        for(let item1 of time){
          let t=item1.slice(1,-1).split(":");
          if(value!=''){
            result.push([parseInt(t[0],10)*60+parseFloat(t[1]),value]);
          }
        }
      }
      result.sort(function (a,b){
        return a[0]-b[0];
      });
      return result;
    }
  },
}
</script>

<style scoped>
.the-aside{
  font-size: 14px;
  width: 250px;
  height: 370px;
  position: fixed;
  right: 0;
  top: 150px;
  z-index: 99;
  background-color: #f5f5f5;
  box-shadow: rgba(0, 0, 0, 0.3) 0px 19px 38px, rgba(0, 0, 0, 0.22) 0px 15px 12px;
  border: 1px solid rgba(0,0,0,0.5);
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  overflow: hidden;
}
.menus{
  list-style: none;
  padding: 0;
}
.liA{
  font-size: 15px;
  font-weight: lighter;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
}
.liA:hover{
  box-shadow: rgba(0, 0, 0, 0.3) 0px 19px 38px, rgba(0, 0, 0, 0.22) 0px 15px 12px;
}
.is-play{
  color: #b4b4ce;
  font-weight: bolder;
}
</style>
