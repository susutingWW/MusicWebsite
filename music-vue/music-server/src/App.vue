<template>
  <div id="app">
    <SongAudio></SongAudio>
    <TheHeader></TheHeader>
    <router-view/>
    <scroll-top></scroll-top>
    <the-aside></the-aside>
    <PlayBar></PlayBar>
    <TheFooter></TheFooter>
  </div>
</template>

<style lang="less">
html,body,#app{
  height: 100%;
  margin: 0;
}
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

</style>
<script>
import TheHeader from "@/components/TheHeader";
import ScrollTop from "@/components/ScrollTop";
import TheFooter from "@/components/TheFooter";
import SongAudio from "@/components/SongAudio";
import PlayBar from "@/components/PlayBar";
import TheAside from "@/components/TheAside";
export default {
  components: {TheAside, PlayBar, SongAudio, TheFooter, ScrollTop, TheHeader}
}
</script>
