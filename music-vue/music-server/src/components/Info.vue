<template>
  <div>
    <div class="signUp">
      <div class="signUp-head">
        <span>编辑个人资料</span>
      </div>
      <el-form :model="registerForm" ref="registerForm" label-width="70px" class="demo-ruleForm" :rules="rules">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input v-model="registerForm.password" placeholder="请输入密码" type="password"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="phone" label="手机">
          <el-input v-model="registerForm.phoneNum" placeholder="请输入手机号"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="registerForm.email" placeholder="请输入邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日">
          <el-date-picker type="date" v-model="registerForm.birth" placeholder="选择日期" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名">
          <el-input v-model="registerForm.introduction" placeholder="签名"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区">
          <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%">
            <el-option v-for="item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <div class="login-btn">
          <el-button @click="goBack(-1)">取消</el-button>
          <el-button type="primary" @click="saveMsg">保存</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {rules,cities} from "@/assets/data/form";
import {mixin} from "@/mixins";
import {getUserOfId, SignUp, updateUserMsg} from "../api/index";
import {mapGetters} from "vuex";
export default {
  name: "Info",
  mixins:[mixin],
  data(){
    return{
      registerForm:{
        username:'',
        password:'',
        sex:'',
        phoneNum:'',
        email:'',
        birth:'',
        introduction:'',
        location:'',
      },
      cities:[],
      rules:{}
    }
  },
  computed:{
    ...mapGetters([
        'userId'
    ])
  },
  created() {
    this.rules=rules;
    this.cities=cities;
  },
  mounted() {
    this.getMsg(this.userId);
  },
  methods:{
    getMsg(userId){
      getUserOfId(userId).then(res=>{
        this.registerForm.username=res.username;
        this.registerForm.password=res.password;
        this.registerForm.sex=res.sex;
        this.registerForm.phoneNum=res.phoneNum;
        this.registerForm.email=res.email;
        this.registerForm.birth=res.birth;
        this.registerForm.location=res.location;
        this.registerForm.introduction=res.introduction;
      }).catch(err=>{
        console.log(err);
      })
    },
    saveMsg(){
      let _this=this;
      let d = new Date(this.registerForm.birth);
      console.log(d);
      let datetime=d.getFullYear()+'-'+(d.getMonth()+1)+'-'+d.getDate();
      let params=new URLSearchParams();

      params.append('id',this.userId);
      params.append('username',this.registerForm.username);
      params.append('password',this.registerForm.password);
      params.append('sex',this.registerForm.sex);
      params.append('phoneNum',this.registerForm.phoneNum);
      params.append('email',this.registerForm.email);
      params.append('birth',datetime);
      params.append('introduction',this.registerForm.introduction);
      params.append('location',this.registerForm.location);
      updateUserMsg(params)
          .then(res=>{
            if(res.code==1){
              _this.$store.commit('setUserName',this.registerForm.username);
              _this.notify('修改成功','success');
              setTimeout(function (){
                _this.$router.push({path:'/'});
              },2000);
            }else{
              _this.notify('修改失败','error');
            }
          }).catch(err=>{
        _this.notify('修改失败','error');
      })
    },
    goBack(index){
      this.$router.go(index)
    }
  }
}
</script>

<style scoped>
.signUp{
  top:60px;
  background-color: #f2f2f8;
  border-radius: 10px;
  padding: 30px 30px;
  margin-top: 30px;
  margin-left: 30px;
  margin-right: 30px;
}
.signUp-head{
  text-align: center;
  margin-bottom: 10px;
  font-size: 20px;
  font-weight: 600;
}
</style>
