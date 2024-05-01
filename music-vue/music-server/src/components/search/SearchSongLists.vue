<template>
<div class="search-song-lists">
  <content-list :content-list="albumDatas"></content-list>
</div>
</template>

<script>
import ContentList from "@/components/ContentList";
import {getSongListOflikeTitle} from "@/api";
import {mixin} from "@/mixins";
export default {
  name: "SearchSongLists",
  components: {ContentList},
  mixins:[mixin],
  data(){
    return{
      albumDatas:[],
    }
  },
  mounted() {
    this.getSearchList();
  },
  methods:{
    getSearchList(){
      if(!this.$route.query.keywords){
        this.notify('您输入的内容为空','warning');
      } else{
        getSongListOflikeTitle(this.$route.query.keywords).then(res=>{
          console.log(this.$route.query.keywords);
          console.log(res);
          if(res){
            this.albumDatas=res
          }else{
            this.notify('系统暂无歌单','warning');
          }
        })
      }
    }
  }
}
</script>

<style scoped>

</style>
