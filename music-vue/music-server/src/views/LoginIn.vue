<template>
  <div>
    <login-logo></login-logo>
    <div class="signUp">
      <div class="signUp-head">
        <span>账号登录</span>
      </div>
      <el-form :model="loginForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="loginForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="loginForm.password" placeholder="请输入密码" type="password"></el-input>
        </el-form-item>
        <div class="login-btn">
          <el-button @click="goSignUp()">注册</el-button>
          <el-button type="primary" @click="handleLoginIn">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import LoginLogo from "@/components/LoginLogo";
import {mixin} from "@/mixins";
import {LoginIn} from "../api/index";
import {mapMutations} from "vuex";
export default {
  name: "LoginIn",
  components: {LoginLogo},
  mixins:[mixin],
  data(){
    return{
      userToken:'',
      loginForm: {
        username: '',
        password: '',
      },
      rules:{
        username: [
          { required: true, trigger: 'blur',message:'请输入用户名' }
        ],
        password: [
          { required: true, trigger: 'blur', message:'请输入密码' }
        ],
      },
    }
  },
  mounted() {
    this.changeIndex('登录');
  },
  methods:{
    ...mapMutations(['changeLogin']),
    handleLoginIn(){
      let _this=this;
      let params=new URLSearchParams();
      params.append('username',this.loginForm.username);
      params.append('password',this.loginForm.password);
      LoginIn(params)
          .then(res=>{
            console.log(res);
            if(res.code==1){
              _this.userToken = res.token;
              _this.changeLogin({ token: this.userToken });
              _this.notify('登录成功','success');
              _this.$store.commit('setLoginIn',true);
              _this.$store.commit('setUserId',res.userMsg.id);
              _this.$store.commit('setUserName',res.userMsg.username);
              _this.$store.commit('setAvator',res.userMsg.avator);
              setTimeout(function (){
                _this.changeIndex('首页');
                _this.$router.push({path:'/'});
              },2000);
            }else{
              _this.notify('用户名或密码错误','error');
            }
          }).catch(err=>{
            console.log(err);
        _this.notify('用户名或密码错误','error');
      })
    },
    goSignUp(){
      this.changeIndex('注册');
      this.$router.push({path:'/sign-up'});
    },
    changeIndex(value){
      this.$store.commit('setActivateName',value);
    }
  }
}
</script>

<style scoped>
.signUp{
  position: absolute;
  top:60px;
  background-color: #eef1f6;
  border-radius: 10px;
  width:30%;
  margin-left: 60%;
  padding: 30px 30px;
}
.signUp-head{
  text-align: center;
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: 600;
}
</style>
