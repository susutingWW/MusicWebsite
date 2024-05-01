<template>
<div class="search-songs">
  <album-content :songList="listOfSongs"></album-content>
</div>
</template>

<script>
import {mapGetters} from "vuex";
import {mixin} from "@/mixins";
import AlbumContent from "@/components/AlbumContent";
export default {
  name: "SearchSongs",
  components: {AlbumContent},
  mixins:[mixin],
  computed:{
    ...mapGetters([
        'listOfSongs'
    ])
  },
  mounted() {
    this.getSong();
  }
}
</script>

<style scoped>

</style>
