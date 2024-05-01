<template>
<div>
  <div class="item-up" :style="{ bottom: toggle ? '60px' : '10px' }" :class="{turn:toggle}">
    <i class="el-icon-arrow-up" @click="clickT"></i>
  </div>
<div class="play-bar" :style="{ display: toggle ? 'block' : 'none' }">
  <el-row :gutter="0" style="height:100%;width:100%;display: flex;align-items: center;">
    <el-col :span="1"><i class="el-icon-d-arrow-left" @click="prev"></i></el-col>
    <el-col :span="1">
      <div v-if="playButtonUrl=='1'" @click="togglePlay">
        <i class="el-icon-video-pause"/>
      </div>
      <div v-if="playButtonUrl=='0'" @click="togglePlay">
        <i class="el-icon-video-play"/>
      </div>
    </el-col>
    <el-col :span="1"><i class="el-icon-d-arrow-right" @click="next"></i></el-col>
    <el-col :span="2">
      <img :src="picUrl" style="width: 30px;height: 30px;border-radius: 5px" @click="toLyric"/>
    </el-col>
    <el-col :span="2"><div style="font-size: 13px">{{this.title}}</div></el-col>
    <el-col :span="2"><div style="font-size: 13px">{{this.artist}}</div></el-col>
    <el-col :span="2"><div class="current-time">{{nowTime}}</div></el-col>
    <el-col :span="7"><c-progress class="c-progress" :percent="this.curLength" @percentChange="onPercentChange" progress-color="#bfbfbf"/></el-col>
    <el-col :span="2"><div class="left-time">{{songTime}}</div></el-col>
    <el-col :span="1">
      <div class="micr">
<!--      <i class="el-icon-microphone"></i>-->
      <div v-if="volume!==0"><i class="el-icon-microphone"></i></div>
      <div v-if="volume===0"><i class="el-icon-turn-off-microphone"></i></div>
      </div>
    </el-col>
    <el-col :span="1"></el-col>
    <el-col :span="1"><i class="el-icon-download" @click="download"></i></el-col>
    <el-col :span="1"><i class="el-icon-s-operation" @click="changeAside"></i></el-col>
    <el-col :span="1"><el-slider class="volume" v-model="volume" :vertical="true"></el-slider></el-col>
  </el-row>
</div>
</div>
</template>

<script>
import {mapGetters} from "vuex";
import {mixin} from "@/mixins";
import CProgress from "@/components/CProgress";
import {download} from "@/api/index";

export default {
  name: "PlayBar",
  components: {CProgress},
  mixins:[mixin],
  data(){
    return{
      nowTime:'00:00',
      songTime:'00:00',//歌曲总时间
      curLength: 0,
      volume:50,
      thisShow:false,
      toggle:true,
    }
  },
  methods:{
    clickT(){
      this.toggle=!this.toggle;
      console.log(this.toggle);
    },
    //控制音乐播放暂停
    togglePlay(){
      if(this.$store.getters.url===null){
        this.notify('没有选择歌曲','error');
      }else{
        if(this.isPlay){
          this.$store.commit('setIsPlay',false);
        }else{
          this.$store.commit('setIsPlay',true);
        }
      }
    },
    onPercentChange (per) {
      let newTime=this.duration*per/100;
      this.$store.commit('setChangeTime',newTime);
    },
    //解析时间
    formatSeconds(value){
      let theTime=parseInt(value);
      let result='';
      let hour=parseInt(theTime/3600);
      let minute=parseInt((theTime/60)%60);
      let second = parseInt(theTime%60);
      if(hour>0){
        if(hour<10){
          result='0'+hour+':';
        }else{
          result=hour+':';
        }
      }
      if(minute>0){
        if(minute<10){
          result+='0'+minute+':';
        }else{
          result+=minute+':';
        }
      }else{
        result+="00:"
      }
      if(second>0){
        if(second<10){
          result+='0'+second;
        }else{
          result+=second;
        }
      }else{
        result+="00"
      }
      return result;
    },
    //显示播放中的歌曲
    changeAside(){
      this.$store.commit('setShowAside',!this.thisShow);
      this.thisShow=!this.thisShow;
    },
    //上一首
    prev(){
      if((this.listIndex!==-1)&&(this.listOfSongs.length>1)){
        if(this.listIndex>0){
          this.$store.commit('setListIndex',this.listIndex-1);
        }else{
          this.$store.commit('setListIndex',this.listOfSongs.length-1);
        }
        this.toPlay(this.listOfSongs[this.listIndex].url);
      }
    },
    //下一首
    next(){
      if((this.listIndex!==-1)&&(this.listOfSongs.length>1)){
        if(this.listIndex<this.listOfSongs.length-1){
          this.$store.commit('setListIndex',this.listIndex+1);
        }else{
          this.$store.commit('setListIndex',0);
        }
        this.toPlay(this.listOfSongs[this.listIndex].url);
      }
    },
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
    toPlay:function (url){
      if(url&&(url!==this.url)){
        this.$store.commit('setId',this.listOfSongs[this.listIndex].id);
        this.$store.commit('setUrl',this.$store.state.configure.HOST+'/'+url);
        this.$store.commit('setPicUrl',this.$store.state.configure.HOST+'/'+this.listOfSongs[this.listIndex].pic);
        this.$store.commit('setTitle',this.replaceFName(this.listOfSongs[this.listIndex].name));
        this.$store.commit('setArtist',this.replaceLName(this.listOfSongs[this.listIndex].name));
        this.$store.commit('setLyric',this.parseLyric(this.listOfSongs[this.listIndex].lyric));
      }
    },
    //解析歌词
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
    },
    //转向Lyric
    toLyric()
    {
      this.$router.push({path:`/lyric`});
    },
    //下载音乐
    download(){
      download(this.url).then(res=>{
        let content=res.data;
        let eleLink=document.createElement('a');
        eleLink.download=`${this.artist}-${this.title}.mp3`;
        eleLink.style.display='none';
        let blob=new Blob([content]);
        eleLink.href=URL.createObjectURL(blob);
        document.body.appendChild(eleLink);
        eleLink.click();
        document.body.removeChild(eleLink);
      }).catch(err=>{
        console.log(err);
      })
    }
  },
  computed:{
    ...mapGetters([
        'id',
        'url',
        'isPlay',
        'playButtonUrl',
        'picUrl',
        'title',
        'artist',
        'duration',
        'curTime',
        'showAside',
        'listIndex',
        'listOfSongs',
        'autoNext',
    ]),
  },
  watch:{
    isPlay(){
      if(this.isPlay){
        this.$store.commit('setPlayButtonUrl','1');
      }else{
        this.$store.commit('setPlayButtonUrl','0');
      }
    },
    curTime(){
      this.nowTime=this.formatSeconds(this.curTime);
      this.songTime=this.formatSeconds(this.duration);
      this.curLength=(this.curTime/this.duration)*100;
    },
    //音量变化
    volume(){
      this.$store.commit('setVolume',this.volume/100);
    },
    //自动播放下一首
    autoNext(){
      this.next();
    }
  },
  mounted() {
    document.querySelector('.micr').addEventListener('click',function (e){
      document.querySelector('.volume').classList.add('show-volume');
      e.stopPropagation();
    },false);
    document.querySelector('.volume').addEventListener('click',function (e){
      e.stopPropagation();
    },false);
    document.addEventListener('click',function (){
      document.querySelector('.volume').classList.remove('show-volume');
    },false);
  }
}
</script>

<style scoped>
.play-bar{
  position: fixed;
  width: 100%;
  height: 50px;
  left: 0;
  bottom: 0;
  background-color: #d8e0ee;
  display: flex;
  justify-content: center;
  align-items: center;
}
.current-time{
  font-size: 15px;
  font-weight: lighter;
}
.volume{
  position: absolute;
  display: none;
  height: 100px;
  top: -90px;
  right: 185px;
}
.show-volume{
  display: block;
}
.item-up{
  position: fixed;
  bottom: 60px;
  left: 20px;
  cursor: pointer;
}
.turn{
  transform:rotate(180deg);
}
</style>
