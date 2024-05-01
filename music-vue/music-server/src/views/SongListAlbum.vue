<template>
<div class="song-list-album">
  <div class="left">
  <div class="album-img">
    <img :src="attachImageUrl(tempList.pic)" style="width: 300px;height: 300px;border-radius: 20px;
box-shadow: rgba(0, 0, 0, 0.25) 0px 54px 55px, rgba(0, 0, 0, 0.12) 0px -12px 30px, rgba(0, 0, 0, 0.12) 0px 4px 6px, rgba(0, 0, 0, 0.17) 0px 12px 13px, rgba(0, 0, 0, 0.09) 0px -3px 5px;">
  </div>
  <div class="album-info">
    <h2>简介：</h2>
    <span style="color: #818386">{{tempList.introduction}}</span>
  </div>
  </div>
  <div class="right">
  <div class="album-content">
    <div class="album-title">
      <p>{{tempList.title}}</p>
    </div>
    <div class="song">
      <album-content :songList="listOfSongs">
      </album-content>
    </div>
  </div>
  </div>
</div>
</template>

<script>
import {mixin} from "@/mixins";
import {mapGetters} from "vuex";
import {listSongDetail,songOfSongId} from "@/api";
import AlbumContent from "@/components/AlbumContent";
export default {
  name: "SongListAlbum",
  mixins:[mixin],
  components:{
    AlbumContent
  },
  data(){
    return{
      songList:[],
      songListsId:'',
    }
  },
  computed:{
    ...mapGetters([
        'listOfSongs',
        'tempList'
    ])
  },
  created() {
    this.songListsId=this.$route.params.id;
    this.getSongId();
  },
  methods:{
    //查询歌单歌曲列表
    getSongId(){
      listSongDetail(this.songListsId).then(res=>{
        for(let item of res){
          console.log(item);
          this.getSongList(item.songId);
        }
        this.$store.commit('setListOfSongs',this.songList);
      }).catch(err=>{
        console.log(err);
      })
    },
    //根据歌曲id获取信息
    getSongList(id){
      songOfSongId(id).then(res=>{
        this.songList.push(res);
      }).catch(err=>{
        console.log(err);
      })
    }
  }
}
</script>

<style scoped>
.album-img{
  margin-top: 20px;
}
.album-info{
  margin-top: 20px;
}
.album-title{
  color: #b2b2b2;
  font-size: 30px;
  font-weight: bolder;
}
.song-list-album{
  display: grid;
  grid-template-columns: 1fr 2fr;
}
</style>
