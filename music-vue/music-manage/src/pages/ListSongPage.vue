<template>
  <div class="table" style="height: 100%;width: 100%">
    <div class="crumbs" style="height: 8%;display: flex;align-items: center;justify-content: center;">
      <i class="el-icon-tickets"></i>歌单歌曲信息
    </div>
    <div class="container" style="height: 8%; display: flex;align-items: center;justify-content: center;">
      <div class="handle-box">
        <el-button type="primary" size="mini" @click="delAll" style="margin-right: 150px">批量删除</el-button>
        <el-input v-model="select_word" size="mini" placeholder="请输入歌曲名" class="handle-input"></el-input>
        <el-button type="primary" size="mini" @click="centerDialogVisible=true" style="margin-left: 150px">添加歌曲</el-button>
      </div>
    </div>
    <el-table size="mini" border style="width:100%" height="83%" :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="name" label="歌手-歌名" align="center"></el-table-column>
      <el-table-column label="操做" width="150" align="center">
        <template slot-scope="scope">
          <el-button size="mini" type="danger" @click="handleDelete(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog title="添加歌曲" :visible.sync="centerDialogVisible" width="400px" center>
      <el-form :model="registerForm" ref="registerForm" label-width="80px" action="" id="tf">
        <el-form-item prop="singerName" label="歌手名" size="mini">
          <el-input v-model="registerForm.singerName" placeholder="歌手名"></el-input>
        </el-form-item>
        <el-form-item prop="songName" label="歌曲名" size="mini">
          <el-input v-model="registerForm.songName" placeholder="歌曲名"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer">
         <el-button size="mini" @click="centerDialogVisible=false">取消</el-button>
         <el-button size="mini" @click="getSongId">确定</el-button>
       </span>
    </el-dialog>

    <el-dialog title="删除歌曲" :visible.sync="delVisible" width="300px" center>
      <div align="center">删除不可恢复，是否确认删除？</div>
      <span slot="footer">
         <el-button size="mini" @click="delVisible=false">取消</el-button>
         <el-button size="mini" @click="deleteRow">确定</el-button>
       </span>
    </el-dialog>
  </div>
</template>

<script>
import {delListSong, listSongAdd, listSongDetail, songOfSongId, songOfSongName} from "@/api";
import {mixin} from "@/mixins";
export default {
  name: "ListSongPage",
  mixins:[mixin],
  data(){
    return{
      centerDialogVisible:false,//添加弹窗
      delVisible:false,//删除窗口
      registerForm:{
        singerName:'',
        songName:'',
      },
      tableData:[],
      tempData:[],
      select_word:'',
      idx:-1,//当前选择
      multipleSelection:[],//哪些项打勾
      songListId:'',
    }
  },
  created() {
    this.songListId=this.$route.query.id;
    this.getData();
  },
  watch:{
    //搜索框发生变化，结果变化
    select_word:function (){
      if(this.select_word==''){
        this.tableData=this.tempData;
      }else{
        this.tableData=[];
        for(let item of this.tempData){
          if(item.name.includes(this.select_word)){
            this.tableData.push(item);
          }
        }
      }
    }
  },
  methods:{
    //添加歌手
    addSong(songId){
      let params = new URLSearchParams();
      params.append('songId',songId);
      params.append('songListId',this.songListId);
      listSongAdd(params)
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
    //查询所有歌手
    getData(){
      this.tempData=[];
      this.tableData=[];
      listSongDetail(this.songListId).then(res =>{
        for(let item of res){
          this.getSong(item.songId);
        }
      })
    },
    //歌曲id查询歌曲对象，放到tableData
    getSong(id){
      songOfSongId(id)
          .then(res=>{
            this.tempData.push(res);
            this.tableData.push(res)
          }).catch(err=>{
        console.log(err);
      });
    },
    getSongId(){
      let _this = this;
      var songOfName=_this.registerForm.singerName+'-'+_this.registerForm.songName;
      songOfSongName(songOfName).then(
          res=>{
            this.addSong(res[0].id);
          }
      )
    },
    //删除一名歌手
    deleteRow(){
      delListSong(this.idx,this.songListId)
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
  }
}
</script>

<style scoped>
.handle-box{
  /*margin-bottom: 20px;*/
  text-align: center;
  padding: 10px;
}
.song-img{
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
::-webkit-scrollbar {
  display: none;
}
.play{
  position: absolute;
  z-index: 100;
  width: 80px;
  height: 80px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  top: 25px;
  left: 15px;
  font-size: 35px;
  color: white;
}
</style>

