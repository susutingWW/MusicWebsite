<template>
  <div class="table" style="height: 100%;width: 100%">
    <div class="container" style="height: 8%; display: flex;align-items: center;justify-content: center;">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll" style="margin-right: 150px">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="筛选关键字" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible=true" style="margin-left: 150px">添加歌单</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" height="84%" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column label="歌单图片" width="110" align="center">
        <template slot-scope="scope">
          <div class="songList-img">
            <img :src="getUrl(scope.row.pic)" style="width: 100%"/>
          </div>
          <el-upload :action="uploadUrl(scope.row.id)" :on-success="handleAvatorSuccess"
                     :before-upload="beforeAvatorUpload" :headers="headerObj">
            <el-button size="mini">更新图片</el-button>
          </el-upload>
        </template>
      </el-table-column>
      <el-table-column prop="title" label="标题" width="120" align="center">
      </el-table-column>
      <el-table-column label="简介">
        <template slot-scope="scope">
          <p style="height: 100px">{{scope.row.introduction}}</p>
        </template>
      </el-table-column>
      <el-table-column prop="style" label="风格" width="120" align="center"></el-table-column>
      <el-table-column label="歌曲管理" width="110" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="songEdit(scope.row.id)">歌曲管理
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="操做" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" @click="handleEdit(scope.row)">编辑</el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <div class="pagination">
      <el-pagination
          background
          Layout="total,prev,pager,next"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="tableData.length"
          @current-change="handleCurrentChange"></el-pagination>
    </div>
    <el-dialog title="添加歌单" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px">
        <el-form-item prop="title" label="标题" size="mini">
          <el-input v-model="registerForm.title" placeholder="歌单名"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="registerForm.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-radio-group v-model="registerForm.style">
            <el-radio label="华语"></el-radio>
            <el-radio label="粤语"></el-radio>
            <el-radio label="欧美"></el-radio>
            <el-radio label="日韩"></el-radio>
            <el-radio label="BGM"></el-radio>
            <el-radio label="轻音乐"></el-radio>
          </el-radio-group>
        </el-form-item>
<!--        <el-form-item prop="style" label="风格" size="mini">-->
<!--          <el-input v-model="registerForm.style" placeholder="风格"></el-input>-->
<!--        </el-form-item>-->
      </el-form>
      <span slot="footer">
         <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
         <el-button size="mini" @click="addSongList">确定</el-button>
       </span>
    </el-dialog>
    <el-dialog title="修改歌单" :visible.sync="editVisible" width="400px" center>
      <el-form :model="form" ref="form" label-width="80px">
        <el-form-item prop="title" label="标题" size="mini">
          <el-input v-model="form.title" placeholder="歌单名"></el-input>
        </el-form-item>
        <el-form-item prop="introduction" label="简介" size="mini">
          <el-input v-model="form.introduction" placeholder="简介" type="textarea"></el-input>
        </el-form-item>
        <el-form-item prop="style" label="风格" size="mini">
          <el-input v-model="form.style" placeholder="风格"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
         <el-button size="mini" @click="editVisible=false">取消</el-button>
         <el-button size="mini" @click="editSave">确定</el-button>
       </span>
    </el-dialog>
    <el-dialog title="删除歌单" :visible.sync="delVisible" width="300px" center>
      <div align="center">删除不可恢复，是否确认删除？</div>
      <span slot="footer">
         <el-button size="mini" @click="delVisible=false">取消</el-button>
         <el-button size="mini" @click="deleteRow">确定</el-button>
       </span>
    </el-dialog>
  </div>
</template>

<script>
import {setSongList,getAllSongList,updateSongList,delSongList} from "@/api";
import {mixin} from "@/mixins";
export default {
  name: "SongListPage",
  mixins:[mixin],
  data(){
    return{
      headerObj:{
        token:localStorage.getItem('token'),
      },
      centerDialogVisible:false,//添加弹窗
      editVisible:false,//编辑弹窗
      delVisible:false,//删除窗口
      registerForm:{
        title:'',
        style:'',
        introduction:'',
      },
      form:{
        id: '',
        title:'',
        style:'',
        introduction:'',
      },
      tableData:[],
      tempData:[],
      select_word:'',
      pageSize:5,
      currentPage:1,
      idx:-1,//当前选择
      multipleSelection:[]//哪些项打勾
    }
  },
  created() {
    this.getData();
  },
  computed:{
    //计算
    data(){
      return this.tableData.slice((this.currentPage-1)*this.pageSize,this.currentPage*this.pageSize)
    }
  },
  watch:{
    //搜索框发生变化，结果变化
    select_word:function (){
      if(this.select_word==''){
        this.tableData=this.tempData;
      }else{
        this.tableData=[];
        for(let item of this.tempData){
          if(item.title.includes(this.select_word)){
            this.tableData.push(item);
          }
        }
      }
    }
  },
  methods:{
    //添加歌单
    addSongList(){
      let params = new URLSearchParams();
      params.append('title',this.registerForm.title);
      params.append('style',this.registerForm.style);
      params.append('pic','img/songListPic/songList.jpg');
      params.append('introduction',this.registerForm.introduction);
      setSongList(params)
          .then(res=>{
            if(res.code==1){
              this.getData();
              this.notify("添加成功","success");
            }else{
              this.notify("添加失败","error");
            }
          }).catch(err=>{
        console.log(err);
      });
      this.centerDialogVisible=false;
    },
    //查询所有歌单
    getData(){
      this.tempData=[];
      this.tableData=[];
      getAllSongList().then(res =>{
        this.tableData=res;
        this.tempData=res;
        this.currentPage=1;
      })
    },
    //更新图片
    uploadUrl(id){
      return `${this.$store.state.HOST}/songList/updateSongListPic?id=${id}`
    },
    //获取当前页
    handleCurrentChange(val){
      this.currentPage=val;
    },
    //编辑数据
    handleEdit(row){
      this.editVisible=true;
      this.form={
        id:row.id,
        title:row.title,
        style:row.style,
        introduction:row.introduction,
      }
    },
    //报存编辑页面
    editSave() {
      let params = new URLSearchParams();
      params.append('id',this.form.id);
      params.append('title',this.form.title);
      params.append('style',this.form.style);
      params.append('introduction',this.form.introduction);
      updateSongList(params)
          .then(res=>{
            if(res.code==1){
              this.getData();
              this.notify("修改成功","success");
            }else{
              this.notify("修改失败","error");
            }
          }).catch(err=>{
        console.log(err);
      });
      this.editVisible=false;
    },
    //删除一个歌单
    deleteRow(){
      delSongList(this.idx)
          .then(res=>{
            if(res){
              this.getData();
              this.notify("删除成功","success");
            }else{
              this.notify("删除失败","error");
            }
          }).catch(err=>{
        console.log(err);
      });
      this.delVisible=false;
    },
    //转向歌曲管理
    songEdit(id){
      this.$router.push({path:`/listSong`,query:{id}});
    },
  }
}
</script>

<style scoped>
.handle-box{
  /*margin-bottom: 20px;*/
  text-align: center;
  padding: 10px;
}
.songList-img{
  width: 100%;
  height: 80px;
  border-radius: 5px;
  margin-bottom: 5px;
  overflow:hidden;
}
.handle-input{
  width: 300px;
  display: inline-block;
  margin-right: 20px;
}
.pagination{
  display: flex;
  height: 7%;
  align-items: center;
  justify-content: center;
}
</style>
