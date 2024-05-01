<template>
   <div style="height: 100%;width: 100%">
     <el-row :gutter="10" class="mgb20" style="margin-left: 0;margin-right: 0">
       <el-col :span="6">
         <el-card style="margin-top: 5px">
           <div class="grid-content">
             <div class="grid-cont-center">
               <div style="font-size: 30px;font-weight: bold;">{{ consumerCount }}</div>
               <div>用户总数</div>
             </div>
           </div>
         </el-card>
       </el-col>
       <el-col :span="6">
         <el-card style="margin-top: 5px">
           <div class="grid-content">
             <div class="grid-cont-center">
               <div style="font-size: 30px;font-weight: bold;">{{ songCount }}</div>
               <div>歌曲总数</div>
             </div>
           </div>
         </el-card>
       </el-col>
       <el-col :span="6">
         <el-card style="margin-top: 5px">
           <div class="grid-content">
             <div class="grid-cont-center">
               <div style="font-size: 30px;font-weight: bold;">{{ singerCount }}</div>
               <div>歌手数量</div>
             </div>
           </div>
         </el-card>
       </el-col>
       <el-col :span="6">
         <el-card style="margin-top: 5px">
           <div class="grid-content">
             <div class="grid-cont-center">
               <div style="font-size: 30px;font-weight: bold;">{{ songListCount }}</div>
               <div>歌单数量</div>
             </div>
           </div>
         </el-card>
       </el-col>
     </el-row>

     <el-row :gutter="10" class="mgb20" style="margin: 0">
       <el-col :span="12">
         <h3>用户性别比例</h3>
         <div style="background-color: white">
           <ve-pie :data="consumerSex" :theme="options"></ve-pie>
         </div>
       </el-col>
       <el-col :span="12">
         <h3>歌曲类型分布</h3>
         <div style="background-color: white">
           <ve-histogram :data="songStyle"></ve-histogram>
         </div>
       </el-col>
     </el-row>
     <el-row :gutter="10" class="mgb20" style="margin: 0">
       <el-col :span="12">
         <h3>歌手性别比例</h3>
         <div style="background-color: white">
           <ve-pie :data="singerSex"></ve-pie>
         </div>
       </el-col>
     </el-row>
   </div>
</template>

<script>
import {getAllConsumer, allSong, getAllSinger, getAllSongList} from "@/api";
export default {
  name: "InfoPage",
  data(){
    return{
      consumerCount:0,
      songCount:0,
      singerCount:0,
      songListCount:0,
      consumer:[],
      consumerSex:{
        columns:['性别','总数'],
        rows:[
          {'性别':'男','总数':0},
          {'性别':'女','总数':0},
        ]
      },
      options:{
        color:['#87cefa','#ffc0cb']
      },
      songStyle:{
        columns:['风格','总数'],
        rows:[
          {'风格':'华语','总数':0},
          {'风格':'粤语','总数':0},
          {'风格':'欧美','总数':0},
          {'风格':'日韩','总数':0},
          {'风格':'BGM','总数':0},
          {'风格':'轻音乐','总数':0},
        ]
      },
      singerSex:{
        columns:['性别','总数'],
        rows:[
          {'性别':'女','总数':0},
          {'性别':'男','总数':0},
          {'性别':'组合','总数':0},
          {'性别':'不明','总数':0},
        ]
      },
    }
  },
  created:{

  },
  mounted(){
    this.getConsumer();
    this.getSong();
    this.getSinger();
    this.getSongList();
  },
  methods:{
    getConsumer(){
      getAllConsumer().then(res =>{
        this.consumer=res;
        this.consumerCount=res.length;
        this.consumerSex.rows[0]['总数']=this.setSex(1,this.consumer);
        this.consumerSex.rows[1]['总数']=this.setSex(0,this.consumer);
      })
    },
    setSex(sex,val){
      let count=0;
      for(let item of val){
        if(sex==item.sex){
          count++;
        }
      }
      return count;
    },
    getSong(){
      allSong().then(res =>{
        this.songCount=res.length;
      })
    },
    getSinger(){
      getAllSinger().then(res =>{
        this.singerCount=res.length;
        this.singerSex.rows[0]['总数']=this.setSex(0,res);
        this.singerSex.rows[1]['总数']=this.setSex(1,res);
        this.singerSex.rows[2]['总数']=this.setSex(2,res);
        this.singerSex.rows[3]['总数']=this.setSex(3,res);
      })
    },
    getSongList(){
      getAllSongList().then(res => {
        this.songListCount=res.length;
        for(let item of res){
          this.getByStyle(item.style);
        }
      })
    },
    getByStyle(style){
      for(let item of this.songStyle.rows){
        if(style.includes(item['风格'])){
          item['总数']++;
        }
      }
    },
  }
}
</script>
<style scoped>
.grid-content{
  display: flex;
  align-items: center;
  height: 50px;
}
.grid-cont-center{
  flex: 1;
  text-align: center;
  font-size: 14px;
  color: darkgray;
}
</style>
