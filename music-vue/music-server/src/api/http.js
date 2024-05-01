import axios from 'axios';
axios.defaults.timeout = 5000;//超时时间
axios.defaults.withCredentials = true;//允许跨域
//响应头
axios.defaults.headers.post['Content-Type']='application/x-www-form-urlencoded;charset=UTF-8';
//基础url
axios.defaults.baseURL="http://localhost:8888";

axios.interceptors.request.use(
    config => {
        if (localStorage.getItem('token')) {
            config.headers['token'] = localStorage.getItem('token');
        }
        return config;
    },
    err => {
        return Promise.reject(err)
    });

//相应拦截器
axios.interceptors.response.use(
    response =>{
        //如果response里面的status是200，访问到接口
        if(response.status==200){
            return Promise.resolve(response);
        }else{
            return Promise.reject(response);
        }
    },
error => {
        if(error.response.status){
            switch (error.response.status){
                case 401:
                    router.replace({
                        path:'/',
                        query:{
                            redirect: router.currentRoute.fullPath
                        }
                    });
                    break;
                case 404:
                   break;
            }
            return Promise.reject(error.response);
        }
}
);

//封装get方法
export function get(url,params={}){
    return new Promise((resolve,reject)=>{
        axios.get(url,{params:params})
            .then(response=>{
                resolve(response.data);
            })
            .catch(err =>{
                reject(err);
            })
    });
}
//封装post方法
export function post(url,data={}) {
    return new Promise((resolve, reject) => {
        axios.post(url, data)
            .then(response => {
                resolve(response.data);
            })
            .catch(err => {
                reject(err);
            })
    });
}
