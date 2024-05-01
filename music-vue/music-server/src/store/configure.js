const configure = {
    state:{
        HOST:'http://127.0.0.1:8888',
        activeName:'',//当前菜单
        showAside:false,//
        loginIn:false,
        token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
    },
    getters:{
        activeName:state => {
            let activeName = state.activeName
            if(!activeName){
                activeName = JSON.parse(window.sessionStorage.getItem('activeName'))
            }
            return activeName
        },
        showAside:state => {
            let showAside = state.showAside
            if(!showAside){
                showAside = JSON.parse(window.sessionStorage.getItem('showAside'))
            }
            return showAside
        },
        loginIn:state => {
            let loginIn = state.loginIn
            if(!loginIn){
                loginIn = JSON.parse(window.sessionStorage.getItem('loginIn'))
            }
            return loginIn
        },
    },
    mutations:{
        setActivateName:(state,activeName)=>{
            state.activeName=activeName
            window.sessionStorage.setItem('activeName',JSON.stringify(activeName))
        },
        setShowAside:(state,showAside)=>{
            state.showAside=showAside
            window.sessionStorage.setItem('showAside',JSON.stringify(showAside))
        },
        setLoginIn:(state,loginIn)=>{
            state.loginIn=loginIn
            window.sessionStorage.setItem('loginIn',JSON.stringify(loginIn))
        },
        changeLogin (state, user) {
            state.token = user.token;
            localStorage.setItem('token', user.token);
        }
    }
}
export default configure
