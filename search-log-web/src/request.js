import axios from "axios";
import qs from "qs"

// 设置提交数据时的格式
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
const request = axios.create({
    timeout:50000000

})
request.interceptors.request.use(config=>{
    //前置拦截
    if(config.method=="post"){
    }
    return config;
},error => {
    //前置错误
    return Promise.reject("访问失败")
})

request.interceptors.response.use(response=>{
    return response.data;
},error => {
    return Promise.reject(error)
})
export default request;