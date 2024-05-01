<template>
<div class="scroll-top" @click="returnTop">
  <div class="box-in">
    <i class="el-icon-caret-top"></i>
  </div>
</div>
</template>

<script>
export default {
  name: "scroll-top",
  methods:{
    returnTop(){
      document.documentElement.scrollTop=document.body.scrollTop=0;
    }
  }
}
</script>

<style scoped>
.scroll-top{
  position: fixed;
  bottom: 80px;
  right: 10px;
  height: 50px;
  width: 50px;
  background-color: #eef1f6;
  border-radius: 20px;
  cursor: pointer;
  box-shadow: rgba(100, 100, 111, 0.2) 0px 7px 29px 0px;
}
.box-in{
  height: 100%;
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 30px;
}
.scroll-top:hover{
  background-color: #ced2d9;
}
</style>
