<template>
<div class="song-audio">
  <audio id="player"
         :src="url"
         controls="controls"
         preload="auto"
         @canplay="startPlay"
         @ended="ended">
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
        'isPlay'
    ])
  },
  watch:{
    isPlay: function(){
      this.togglePlay();
    }
  },
  methods:{
    //开始播放
    startPlay(){
      let player = document.querySelector('#player');
      player.play();
    },
    //播放完成
    ended(){
      this.isPlay=false;
    },
    //开始暂停
    togglePlay(){
      let player=document.querySelector('#player');
      if(this.isPlay){
        player.play();
      }else{
        player.pause();
      }
    },

  }
}
</script>

<style scoped>
.song-audio{
  display: none;
}
</style>
