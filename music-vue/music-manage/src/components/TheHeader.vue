<template>
  <div class="header">
    <!--折叠图片-->
    <div class="collapse-btn" @click="collapseChange">
      <i class="el-icon-menu"></i>
    </div>
    <div class="logo">小王音乐后台管理</div>
    <div class="header-right">
      <div class="btn-fullscreen" @click="handleFullScreen">
        <el-tooltip :content="fullscreen?'取消全屏':'全屏'" placement="bottom">
          <i class="el-icon-rank"></i>
        </el-tooltip>
      </div>
      <div class="user-avator">
        <img src="../assets/img/user.jpg"/>
      </div>
      <el-dropdown class="user-name" trigger="click" @command="handleCommand">
        <span class="el-dropdown-link">
          {{userName}}
        </span>
        <i class="el-icon-caret-bottom"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          <el-dropdown-item command="updatePassword">修改密码</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>

    <el-dialog title="修改密码" :visible.sync="visible" width="300px" center>
      <el-form :model="form" ref="form" label-width="80px">
        <el-form-item prop="name" label="用户名" size="mini">
          <el-input v-model="form.name" :disabled="true" :placeholder="userName"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="新密码" size="mini">
          <el-input v-model="form.password" placeholder="请输入新密码" type="password"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
         <el-button size="mini" @click="visible=false">取消</el-button>
         <el-button size="mini" @click="editSave">确定</el-button>
       </span>
    </el-dialog>

  </div>
</template>

<script>
import bus from "@/assets/js/bus";
import {updatePassword} from "@/api";
import {mixin} from "@/mixins";
export default {
  name: "THeader",
  mixins:[mixin],
  data(){
    return{
      collapse:false,
      fullscreen:false,
      visible:false,
      form:{
        name:'',
        password:'',
      },
    }
  },
  computed:{
    userName(){
      return localStorage.getItem('userName');
    },
    id(){
      return localStorage.getItem('id');
    }
  },
  methods:{
    //侧边栏折叠
    collapseChange(){
      this.collapse = !this.collapse;
      bus.$emit('collapse',this.collapse);
    },
    //全屏事件
    handleFullScreen(){
      if(this.fullscreen){
        if(document.exitFullscreen()){
          document.exitFullscreen();
        }else if(document.webkitCancelFullScreen)
        {
          document.webkitCancelFullScreen();
        }else if(document.mozCancelFullScreen)
        {
          document.mozCancelFullScreen();
        }else if(document.msExitFullScreen){
          document.msExitFullScreen();
        }
      }else{
        let element = document.documentElement;
        if(element.requestFullscreen){
          element.requestFullscreen();
        }else if(element.webkitRequestFullscreen)
        {
          element.webkitRequestFullscreen();
        }else if(element.mozRequestFullscreen)
        {
          element.mozRequestFullscreen();
        }else if(element.msRequestFullscreen)
        {
          element.msRequestFullscreen();
        }
      }
      this.fullscreen=!this.fullscreen;
    },
    //
    handleCommand(command){
      if(command=="logout"){
        localStorage.removeItem('userName');
        localStorage.removeItem('id');
        this.$router.push("/");
      }else if(command=="updatePassword"){
        this.visible=true;
      }
    },
    editSave(){
      let params = new URLSearchParams();
      params.append('id',this.id);
      params.append('name',this.userName);
      params.append('password',this.form.password);
      console.log(this.id);
      console.log(this.userName);
      console.log(this.form.password);
      updatePassword(params)
          .then(res=>{
            if(res.code==1){
              this.notify("修改成功","success");
              this.visible=false;
              localStorage.removeItem('userName');
              localStorage.removeItem('id');
              this.$router.push("/");
            }else{
              this.notify("修改失败","error");
            }
          }).catch(err=>{
        console.log(err);
      });
    }
  }

}
</script>

<style scoped>
.header{
  position: relative;
  background-color: #253041;
  box-sizing: border-box;
  width: 100%;
  height: 50px;
  font-size: 15px;
  color: white;
}
.collapse-btn{
  font-size: 22px;
  float:left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 50px;
}
.sidebar >ul{
  height: 100%;
}

.header .logo{
  position: relative;
  float: left;
  line-height: 50px;
}
.header-right{
  float: right;
  padding-right: 20px;
  display: flex;
  height: 50px;
  align-items: center;
}
.btn-fullscreen{
  transform: rotate(45deg);
  font-size: 24px;
}
.user-avator{
  margin-left: 20px;
}
.user-avator img{
  display: block;
  width: 35px;
  height: 35px;
  border-radius: 50%;
}
.user-name{
  margin-left: 10px;
}
.el-dropdown-link{
  color:white;
  cursor: pointer;
}
</style>
