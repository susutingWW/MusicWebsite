import Vue from 'vue'
import Vuex from 'vuex'
Vue.use(Vuex)

const store = new Vuex.Store({
    state:{
        HOST:'http://127.0.0.1:8888',
        isPlay:false,
        url:'',//歌曲地址
        id:'',//歌曲id
        token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
    },
    getters:{
        isPlay:state => state.isPlay,
        url:state => state.url,
        id:state => state.id,
    },
    mutations:{
        setIsPlay:(state,isPlay)=>{state.isPlay=isPlay},
        setUrl:(state,url) => {state.url=url},
        setId:(state,id) => {state.id=id},
        changeLogin (state, user) {
            state.token = user.token;
            localStorage.setItem('token', user.token);
        }
    }
})

export default store
