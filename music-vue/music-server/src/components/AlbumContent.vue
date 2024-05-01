<template>
<div class="content">
<!--  <h1 class="title">-->
<!--    <slot name="title"></slot>-->
<!--  </h1>-->
  <ul style="list-style: none;width: 100%;padding: 0">
    <li>
      <div class="song-item">
        <span class="item-index" style="display: inline-block;width: 40px;height: 40px;"></span>
        <span class="item-title" style="display: inline-block;width: 150px;height: 40px;">歌曲名</span>
        <span class="item-name" style="display: inline-block;width: 200px;height: 40px;">歌手</span>
        <span class="item-intro" style="display: inline-block;width: 150px;height: 40px;">专辑</span>
      </div>
    </li>
    <li v-for="(item,index) in songList" :key="index" style="width: 100%">
      <div class="song-item" @click="toPlay(item.id,item.url,item.pic,index,item.name,item.lyric)">
        <span class="item-index" style="display: inline-block;width: 40px;height: 40px;margin-top: 10px" >
          {{index+1}}
        </span>
        <span class="item-title" style="display: inline-block;width: 150px;height: 40px;margin-top: 10px">{{replaceFName(item.name)}}</span>
        <span class="item-name" style="display: inline-block;width: 200px;height: 40px;margin-top: 10px">{{replaceLName(item.name)}}</span>
        <span class="item-intro" style="display: inline-block;width: 150px;height: 40px;margin-top: 10px">{{ item.introduction }}</span>
      </div>
    </li>
  </ul>
</div>
</template>

<script>
import {mapGetters} from "vuex";
import {mixin} from "@/mixins";
export default {
  name: "AlbumContent",
  mixins:[mixin],
  props:[
      'songList'
  ]
}
</script>

<style scoped>
.content{
  width: 100%;
}
.song-item{
  width: 100%;
  cursor: pointer;
}
</style>
