<template>
<div class="upload">
  <p style="color: #9c9fa4;font-weight: bolder;font-size: 20px">修改头像</p>
  <el-divider><i class="el-icon-folder-opened"></i></el-divider>
  <div class="section" style="margin-top: 50px">
    <el-upload drag :action="uploadUrl()" :show-file-list="false" :on-success="handleAvatorSuccess"
               :before-upload="beforeAvatorUpload" :headers="headerObj">
      <i class="el-icon-upload"></i>
      <div>
        将文件拖到此处，或修改图像
      </div>
      <div slot="tip" style="margin-top: 20px">
        只能上传jpg或png格式图片，且大小不能超过10MB。
      </div>
    </el-upload>
  </div>
</div>
</template>

<script>
import {mixin} from "@/mixins";
import {mapGetters} from "vuex";
export default {
  name: "Upload",
  mixins:[mixin],
  computed:{
    ...mapGetters([
        'userId',
    ])
  },
  data(){
    return{
      headerObj:{
        token:localStorage.getItem('token'),
      },
    }
  },
  methods:{
    uploadUrl(){
      return `${this.$store.state.configure.HOST}/consumer/updateConsumerPic?id=${this.userId}`
    },
    handleAvatorSuccess(res,file){
      if(res.code==1){
        this.$store.commit('setAvator',res.avator);
        this.notify('修改成功','success');
      }else{
        this.notify('修改失败','error');
      }
    },
    beforeAvatorUpload(file){
      const isJPG=file.type=='image/jpeg';
      const isLt10M = file.size /1024/1024<10;
      if(!isJPG){
        this.notify('上传的头像只能是JPG格式','error');
        return false;
      }
      if(!isLt10M){
        this.notify('上传的头像不能大于10MB','error');
        return false;
      }
      return true;
    }
  }
}
</script>

<style scoped>

</style>
