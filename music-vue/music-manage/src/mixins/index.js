export const mixin = {
    methods:{
        notify(title,type){
            this.$notify({
                title:title,
                type:type
            })
        },
        //根据相对地址获取绝对地址
        getUrl(url){
            return `${this.$store.state.HOST}/${url}`
        },
        //性别映射
        changeSex(value){
            if(value==0)
            {
                return '女';
            }
            if(value==1)
            {
                return '男';
            }
            if(value==2)
            {
                return '组合';
            }
            if(value==3)
            {
                return '不明';
            }
            return value;
        },
        //生日映射
        attachBirth(val){
            return String(val).substr(0,10);
        },
        //上传图像之前的校验
        beforeAvatorUpload(file){
            const isJPG=(file.type === 'image/jpeg')||(file.type==='image/png');
            if(!isJPG){
                this.$message.error('头像图片只能是jpg或png格式！');
                return false;
            }
            const isLt2M = (file.size/1024/1024) <2;
            if(!isLt2M){
                this.$message.error('头像图片只能小于2M！');
                return false;
            }
            return  true;
        },
        //上传图片成功之后
        handleAvatorSuccess(res){
            let _this=this;
            if(res.code==1){
                _this.getData();
                _this.$notify({
                    title:'上传成功',
                    type:'success'
                });
            }else{
                _this.$notify({
                    title:'上传失败',
                    type:'error'
                });
            }
        },
        //弹出删除窗口
        handleDelete(id){
            this.idx=id;
            this.delVisible=true;
        },
        //把已经的项赋值
        handleSelectionChange(val){
            this.multipleSelection=val;
        },
        //批量删除已经选择的项
        delAll(){
            for(let item of this.multipleSelection){
                this.handleDelete(item.id);
                this.deleteRow();
            }
            this.multipleSelection=[];
        },
    }
}
