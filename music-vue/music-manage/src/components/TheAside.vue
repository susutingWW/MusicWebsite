<template>
  <div class="sidebar">
    <el-menu class="sidebar-el-menu"
             :default-active="onRoutes"
             :collapse="collapse"
             background-color="#334256"
             text-color="#ffffff"
             active-text-color="#20a0ff"
             router>
      <template v-for="item in items">
        <template>
          <el-menu-item :index="item.index" :key="item.index" style="padding-left: 10px">
            <i :class="item.icon"></i>
            <span slot="title">{{item.title}}</span>
          </el-menu-item>
        </template>
      </template>

    </el-menu>
  </div>
</template>

<script>
import bus from "@/assets/js/bus";
export default {
  name: "TheAside",
  data(){
    return{
      collapse:false,
      items:[
        {
          icon: 'el-icon-s-home',
          index:"info",
          title:'系统首页'
        },
        {
          icon: 'el-icon-s-custom',
          index:"consumer",
          title:'用户管理'
        },
        {
          icon: 'el-icon-user-solid',
          index:"singer",
          title:'歌手管理'
        },
        {
          icon: 'el-icon-s-claim',
          index:"songList",
          title:'歌单管理'
        },
      ]
    }
  },
  computed:{
    onRoutes(){
      return this.$route.path.replace('/', '');
    }
  },
  created() {
    //通过bus进行组件间通信
    bus.$on('collapse',msg=>{
      this.collapse=msg;
    })
  }
}
</script>

<style scoped>
.sidebar{
  display: block;
  position: absolute;
  left:0;
  top:50px;
  bottom:0;
  background-color: #334256;
  overflow-y: scroll;
}

.sidebar::-webkit-scrollbar{
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse){
  width: 150px;
}
.el-menu--collapse  .el-submenu__title span{
  display: none;
}
</style>
