<template>
  <div class="search">
    <nav class="searchList-nav" ref="change">
      <span :class="{isActive:toggle=='Songs'}" @click="handleChangeView('Songs')" style="cursor: pointer">歌曲</span>
      <span :class="{isActive:toggle=='SongLists'}" @click="handleChangeView('SongLists')" style="margin-left: 100px;cursor: pointer">歌单</span>
    </nav>
    <component :is="currentView"></component>
  </div>
</template>

<script>
import SearchSongs from "@/components/search/SearchSongs";
import SearchSongLists from "@/components/search/SearchSongLists";
export default {
  name: "Search",
  components:{
    SearchSongs,SearchSongLists
  },
  data(){
    return{
      toggle:'Songs',
      currentView:'SearchSongs'
    }
  },
  methods:{
    handleChangeView(component){
      this.currentView='Search'+component;
      this.toggle=component;
    }
  }
}
</script>

<style scoped>
.search{
  margin: 20px auto auto;
  width: 70%;
  background-color: #eef1f6;
  border-radius: 10px;
}
.searchList-nav{
  height: 50px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 25px;
  font-weight: bolder;
  color: #b2b2b2;
}
.isActive {
  color: #1f1f1f;
}
</style>
