<template>
<div class="the-header">
  <div @click="goHome" style="height: 100%;align-items: center;display: flex;cursor: pointer">
    <img src="../assets/img/icon/music.png" style="height: 30px;width: 30px;margin-left: 20px;"/>
    <span style="margin-left: 10px;font-weight: bolder">music</span>
  </div>
  <ul class="navbar" style="color: darkgray;">
    <li :class="{active: item.name===activeName}" v-for="item in navMsg" :key="item.path"
        style="display: inline;margin-left: 20px;cursor: pointer" @click="goPage(item.path,item.name)">
      {{item.name}}
    </li>
    <li style="display: inline;">
      <el-input
          v-model="keywords"
          placeholder="搜索音乐"
          prefix-icon="el-icon-search"
          style="width: 200px;margin-right: 10px;margin-left: 50px"
          @keydown.enter="goSearch" size="mini"></el-input>
      <el-button icon="el-icon-search" type="info" @click="goSearch" size="mini">
        搜索
      </el-button>
    </li>
    <li v-show="!loginIn" :class="{active: item.name===activeName}" v-for="item in loginMsg" :key="item.path"
        style="display: inline;margin-left: 20px;cursor: pointer;" @click="goPage(item.path,item.name)">
      {{item.name}}
    </li>
  </ul>
  <div class="header-right" v-show="loginIn">
    <div id="user" style="margin-left: 200px" @click="disPlay=!disPlay">
      <img :src="attachImageUrl(avator)" style="width: 35px;height: 35px;border-radius: 50%;">
    </div>
    <ul class="menu" :style="{ display: disPlay ? 'block' : 'none' }">
      <li v-for="(item,index) in menuList" :key="index" @click="goMenuList(item.path)">
        {{item.name}}
      </li>
    </ul>
  </div>
</div>
</template>

<script>
import {navMsg} from '@/assets/data/header';
import {mapGetters} from 'vuex';
import {loginMsg,menuList} from "@/assets/data/header";
export default {
  name: "TheHeader",
  data(){
    return{
      navMsg:[],
      keywords:'',
      loginMsg:[],
      menuList:[],
      disPlay:false,
    }
  },
  computed:{
    ...mapGetters([
        'activeName',
        'loginIn',
        'avator',
    ])
  },
  created() {
    this.navMsg=navMsg;
    this.loginMsg=loginMsg;
    this.menuList=menuList;
  },
  mounted() {

  },
  methods:{
    goHome(){
      this.$router.push({path: '/'});
    },
    goPage(path,name){
      this.$store.commit('setActivateName',name);
      this.$router.push({path: path});
    },
    goSearch(){
      this.$router.push({path:'/search',query:{keywords: this.keywords}})
    },
    //获取图片地址
    attachImageUrl(srcUrl){
      return srcUrl? this.$store.state.configure.HOST +'/'+ srcUrl : '../assets/img/user.jpg';
    },
    goMenuList(path){
      if(path==0){
        this.$store.commit('setLoginIn',false);
        localStorage.removeItem('token');
        this.$router.go(0);
      }else{
        this.$router.push({path:path});
      }
    },
  }
}
</script>

<style scoped>
.the-header{
  height: 50px;
  display: flex;
  align-items: center;
  background-color: #eef1f6;
  box-shadow:0 0 5px #000;
}
.navbar{
  list-style: none;
}
.active {
  color: #2c3e50;
}
.header-search{
  display: inline;
}
.menu{
  list-style: none;
  background-color: #eef1f6;
  border-radius: 10px;
  width: 100px;
  position: absolute;
  z-index: 99;
  top: 40px;
  right: 290px;
  padding: 0;
  box-shadow: rgba(60, 64, 67, 0.3) 0px 1px 2px 0px, rgba(60, 64, 67, 0.15) 0px 1px 3px 1px;
}
.menu li{
  display: flex;
  height: 40px;
  margin:auto;
  align-items: center;
  justify-content: center;
  color: #9c9fa4;
  cursor: pointer;
}
.menu li:hover{
  color: #544f4f;
  font-weight: bolder;
}
</style>
