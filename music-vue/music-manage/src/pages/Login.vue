<template>
  <div class="login-wrap">
    <div class="ms-title">小王音乐后台管理系统</div>
    <div class="ms-login">
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm">
        <el-form-item prop="username">
          <el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input type="password" v-model="ruleForm.password" placeholder="密码"></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button type="primary" @click="submitForm">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {getLoginStatus} from "../api/index";
import {mixin} from "../mixins/index";
import { mapMutations } from 'vuex';
export default {
  mixins:[mixin],
  name: "Login",
  data(){
    return{
      userToken:'',
      ruleForm:{
        username:'admin',
        password:'123'
      },
      rules:{
        username:[
          {required:true,message:"请输入用户名",trigger:"blur"}
        ],
        password:[
          {required:true,message:"请输入密码",trigger:"blur"}
        ]
      }
    };
  },
  methods:{
    ...mapMutations(['changeLogin']),
    submitForm(){
      let params = new URLSearchParams();
      params.append("name",this.ruleForm.username);
      params.append("password",this.ruleForm.password);
      getLoginStatus(params)
          .then((res)=>{
            console.log(res)
            if(res.code==1){
              //存放username
              localStorage.setItem('userName',this.ruleForm.username);
              localStorage.setItem('id',res.id);
              this.userToken = res.token;
              this.changeLogin({ token: this.userToken });
              this.$router.push("/info");
              this.notify("登录成功","success");
            }else{
              this.notify("登录失败","error");
            }
          });
    }
  }
}
</script>

<style scoped>
.login-wrap{
  position: relative;
  background: url("../assets/img/background.jpg") center;
  background-size: cover;
  width: 100%;
  height: 100%;
}
.ms-title{
  position: absolute;
  top: 50%;
  width: 100%;
  margin-top: -180px;
  text-align: center;
  font-size: 40px;
  font-weight: 600;
  color: white;
}
.ms-login{
  position: absolute;
  width: 300px;
  height: 160px;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  margin: auto;
  padding: 40px;
  border-radius: 10px;
  background-color: #2c3e50;

}
.login-btn{
  text-align: center;
}
.login-btn button{
  width: 100%;
  height: 36px;
}
</style>
