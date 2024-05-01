<template>
<div class="song-audio">
  <audio ref="player"
         :src="url"
         controls="controls"
         preload="auto"
         @canplay="startPlay"
         @ended="ended"
         @timeupdate="timeupdate">
  </audio>
</div>
</template>
<script>
import {mapGetters} from 'vuex';
export default {
  name: "SongAudio",
  data(){
    return{
    }
  },
  created() {
  },
  computed:{
    ...mapGetters([
        'id',
        'url',
        'isPlay',
        'listOfSongs',
        'curTime',
        'changeTime',
        'autoNext',
        'volume',
    ])
  },
  watch:{
    isPlay: function(){
      this.togglePlay();
    },
    //跳转到指定的播放时刻
    changeTime(){
      this.$refs.player.currentTime = this.changeTime;
    },
    //改变音量
    volume(val){
      this.$refs.player.volume=this.volume;
    }
  },
  methods:{
    //开始播放
    startPlay(){
      let player = this.$refs.player;
      this.$store.commit('setDuration',player.duration);
      player.play();
      this.$store.commit('setIsPlay',true);
    },
    //播放完成
    ended(){
      this.$store.commit('setIsPlay',false);
      this.$store.commit('setCurTime',0);
      this.$store.commit('setAutoNext',!this.autoNext);
    },
    //开始暂停
    togglePlay(){
      let player=this.$refs.player;
      if(this.isPlay){
        player.play();
      }else{
        player.pause();
      }
    },
    //音乐播放时记录播放位置
    timeupdate(){
      this.$store.commit('setCurTime',this.$refs.player.currentTime);
    }
  }
}
</script>

<style scoped>
.song-audio{
  display: none;
}
</style>
