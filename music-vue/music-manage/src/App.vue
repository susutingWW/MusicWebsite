<template>
  <div id="app">
<!--    <SongAudio></SongAudio>-->
    <router-view/>
  </div>
</template>

<script>
// import SongAudio from "@/components/SongAudio";
export default {
  name:'App',
  components:{
    // SongAudio
  }
}
</script>
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
