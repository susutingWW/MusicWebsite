<template>
<div class="singer-album">
  <div class="left">
    <div class="album-img">
      <img :src="attachImageUrl(singer.pic)" style="width: 300px;height: 300px;border-radius: 20px;
box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px;">
    </div>
    <ul class="info">
      <li style="margin-top: 20px" v-if="singer.sex==0 || singer.sex==1">{{attachSex(singer.sex)}}</li>
      <li style="margin-top: 20px">生日:{{attachBirth(singer.birth)}}</li>
      <li style="margin-top: 20px">故乡:{{singer.location}}</li>
    </ul>
  </div>
  <div class="right">
    <div class="intro">
      <h2 style="color: #9c9fa4;font-size: 30px">{{singer.name}}</h2>
      <span>{{singer.introduction}}</span>
    </div>
    <div class="song">
      <album-content :songList="listOfSongs">
      </album-content>
    </div>
  </div>
</div>
</template>

<script>
import {mixin} from "@/mixins";
import {mapGetters} from "vuex";
import {songOfSingerId} from "@/api";
import AlbumContent from "@/components/AlbumContent";

export default {
  name: "SingerAlbum",
  mixins:[mixin],
  components:{
    AlbumContent,
  },
  data(){
    return{
      singerId:'',
      singer:{},
    }
  },
  computed:{
    ...mapGetters([
      'listOfSongs',
      'tempList'
    ])
  },
  created() {
    this.singerId=this.$route.params.id;
    this.singer=this.tempList;
    console.log(this.singer);
    this.getSongOfSingerId();
  },
  methods:{
    //查询歌手id查询
    getSongOfSingerId(){
      songOfSingerId(this.singerId).then(res=>{
        this.$store.commit('setListOfSongs',res);
      }).catch(err=>{
        console.log(err);
      })
    },
    //获取性别
    attachSex(value){
      if(value==0){
        return '女';
      }else if(value==1){
        return '男';
      }else{
        return '';
      }
    },
  }
}
</script>

<style scoped>
.singer-album{
  display: grid;
  grid-template-columns: 1fr 2fr;
}
.album-img{
  margin-top: 20px;
}
.info{
  list-style: none;
}
.info{
  color: #818386;
}
</style>
