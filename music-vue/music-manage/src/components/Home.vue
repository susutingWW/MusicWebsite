<template>
  <div>
    <the-header></the-header>
    <the-aside></the-aside>
    <div class="content-box" :class="{'content-collapse':collapse}">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
import TheHeader from "@/components/TheHeader";
import TheAside from "@/components/TheAside";
import bus from "@/assets/js/bus";
export default {
  name: "Home.vue",
  components:{
    TheHeader,
    TheAside
  },
  data(){
    return{
      collapse: false
    }
  },
  created() {
    bus.$on('collapse',msg=>{
      this.collapse=msg;
    })
  }
}
</script>

<style scoped>

</style>
