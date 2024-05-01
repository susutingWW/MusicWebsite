import {likeSongOfName} from "@/api";

export const mixin={
    methods:{
        notify(title,type){
            this.$notify({
                title:title,
                type:type
            })
        },
        //获取图片地址
        attachImageUrl(srcUrl){
            return srcUrl? this.$store.state.configure.HOST +'/'+ srcUrl : '../assets/img/user.jpg';
        },
        //根据歌手名字模糊搜索
        getSong(){
            if(!this.$route.query.keywords){
                this.$store.commit('setListOfSongs',[]);
                this.notify('您输入的内容为空','warning');
            }else{
                likeSongOfName(this.$route.query.keywords).then(res=>{
                    if(!res.length){
                        this.$store.commit('setListOfSongs',[]);
                        this.notify('系统暂无歌曲','warning');
                    }else{
                        this.$store.commit('setListOfSongs',res);
                    }
                }).catch(err=>{
                    console.log(err);
                })
            }
        },
        //获取名字前半部分
        replaceLName(str){
            let arr=str.split('-');
            return arr[0];
        },
        //后半部分
        replaceFName(str){
            let arr=str.split('-');
            return arr[1];
        },
        //播放
        toPlay:function (id,url,pic,index,name,lyric){
            this.$store.commit('setId',id);
            this.$store.commit('setUrl',this.$store.state.configure.HOST+'/'+url);
            this.$store.commit('setPicUrl',this.$store.state.configure.HOST+'/'+pic);
            this.$store.commit('setListIndex',index);
            this.$store.commit('setTitle',this.replaceFName(name));
            this.$store.commit('setArtist',this.replaceLName(name));
            this.$store.commit('setLyric',this.parseLyric(lyric));
        },
        //解析歌词
        parseLyric(text){
            let lines=text.split("\n");
            let pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
            let result=[];
            if(!(/\[.+\]/.test(text)))
            {
                return [[0,text]];
            }
            while(!pattern.test(lines[0])){
                lines=lines.slice(1);
            }
            for(let item of lines){
                let time=item.match(pattern);
                let value = item.replace(pattern,'');
                for(let item1 of time){
                    let t=item1.slice(1,-1).split(":");
                    if(value!=''){
                        result.push([parseInt(t[0],10)*60+parseFloat(t[1]),value]);
                    }
                }
            }
           result.sort(function (a,b){
               return a[0]-b[0];
           });
            return result;
        },
        //获取生日
        attachBirth(val){
            return val.substr(0,10);
        }
    }
}
