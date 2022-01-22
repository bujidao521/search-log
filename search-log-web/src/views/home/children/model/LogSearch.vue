<template>
    <div class="log-search">
        <el-form :label-position="'right'" label-width="55px" :model="param" ref="logSearchForm" class="demo-form-inline"  onsubmit="return false">

                <el-row>
                    <el-col :span="8">
                        <el-form-item label="应用">
                            <el-select v-model="param.appId"
                                       filterable
                                       remote
                                       style="width: 90%"
                                       :remote-method="getlistOption"
                                       placeholder="请选择应用">
                                <el-option
                                        v-for="item in this.listOption"
                                        :key="item.id"
                                        :label="item.appName"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="文件">
                            <el-autocomplete
                                    style="width: 90%"
                                    v-model="param.fileName"
                                    :fetch-suggestions="(queryString,cb)=> {return querySearch(queryString,cb,'fileName')}"
                                    placeholder="可以使用模糊匹配*"
                            ></el-autocomplete>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="行数">
                            <el-input-number   v-model="param.tailNum" controls-position="right" placeholder="文件后n行" :step="500"  :min="1" ></el-input-number>
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="8">
                        <el-form-item label="关键字" prop="logSearchkeyword">
                            <el-autocomplete
                                    style="width: 90%"
                                    v-model="param.keyword"
                                    placeholder="支持正则表达式"
                                    :fetch-suggestions="(queryString,cb)=> {return querySearch(queryString,cb,'keyword')}"
                            ></el-autocomplete>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-form-item label="命令">
                            <el-autocomplete
                                    style="width: 90%"
                                    v-model="param.upLine"
                                    placeholder="C5前后5行;A5后5行等"
                                    :fetch-suggestions="(queryString,cb)=> {return querySearch(queryString,cb,'upLine')}"
                            ></el-autocomplete>
                        </el-form-item>
                    </el-col>
                    <el-col :span="8">
                        <el-button style="margin-left: 15px" type="primary"  @click="logSearch">查询</el-button>
                    </el-col>
                </el-row>






        </el-form>
        <el-form :inline="true" :model="param" class="demo-form-inline">
            <el-form-item>
                <el-button type="primary"  @click="fileNameSearch" :disabled="isEmpty(param.appId)">查文件</el-button>
            </el-form-item>
        </el-form>
        <el-card class="log-search-card log-search-card-top" v-if="fileNameList && fileNameList.length >0" v-loading="fileNameLoading" element-loading-spinner="el-icon-loading"
                 element-loading-background="rgba(0, 0, 0, 0.8)"  element-loading-text="拼命加载中">
            <el-collapse v-model="fileActiveNames" >
                <el-collapse-item v-if="fileNameList && fileNameList.length >0"  title="系统文件名称" name="0">
                    <p v-for="(fileName,index) in fileNameList">
                        <el-link type="primary" @click="param.fileName = fileName" :underline="false" >{{fileName}}</el-link>
                    </p>
                </el-collapse-item>
            </el-collapse>
        </el-card>
        <el-card class="log-search-card" v-loading="loading"  element-loading-spinner="el-icon-loading"
                 element-loading-background="rgba(0, 0, 0, 0.8)"  element-loading-text="拼命加载中">
            <el-collapse v-model="activeNames" >

                <el-collapse-item v-if="logInfoList.length >0"  v-for="(logInfo,index) in logInfoList" :title="'主机:'+logInfo.shellHost +' '+logInfo.logs.length +'行'" :name="index">
                    <p v-for="log in logInfo.logs" v-html="log" style="margin-top: 10px;margin-bottom: 10px;line-height: 19px"></p>
                </el-collapse-item>

                <div v-if="!logInfoList ||logInfoList.length ==0" style="height:60vh">
                    <h3>未查询到日志</h3>
                </div>
            </el-collapse>
        </el-card>
    </div>
</template>

<script>
    import {LogSearch,AppInfolistOption,SearchFileName} from "@/api"

    export default {
        name: "LogSearch",
        mounted() {
            this.getlistOption("");
            if(this.$route.query.appId){
                this.param.appId = Number.parseInt(this.$route.query.appId)
            }
            for(name in this.cacheData.cacheKey){
                this.getCache(name);
            }
        },
        methods:{
            getCache(name){
                let keywordCache = localStorage.getItem("logSearch" + name);
                keywordCache = JSON.parse(keywordCache)
                if(!keywordCache){
                    keywordCache =[]
                }
                this.cacheData.cacheItem[name] =keywordCache;
            },
            setCache(name){
                if(!this.param[name] ||!this.param[name].trim()){
                    return;
                }
                let restaurants = localStorage.getItem("logSearch"+name);
                let cacheMap = {"value":this.param[name].trim()};
                let cacheList = JSON.parse(restaurants)
                if(restaurants && restaurants.indexOf(JSON.stringify(cacheMap)) > -1){
                    for (let index in cacheList){
                        if(this.param[name].trim() == cacheList[index]["value"] && index ==0){
                            return;
                        }else if(this.param[name].trim() == cacheList[index]["value"]){
                            cacheList.splice(index, 1);
                            break
                        }
                    }
                }
                if(!cacheList ){
                    cacheList = []
                }
                cacheList.unshift(cacheMap);
                if(cacheList.length > this.cacheData.cacheKey[name]["num"]){
                    cacheList.pop()
                }
                this.cacheData.cacheItem[name] = cacheList;
                localStorage.setItem("logSearch"+name,JSON.stringify(cacheList));
            },
            isEmpty(obj){
                if(typeof obj == "undefined" || obj == null || obj == ""){
                    return true;
                }else{
                    return false;
                }

            },

            querySearch(queryString,cb,name) {
                var results = queryString ?  this.cacheData.cacheItem[name].filter(this.createFilter(queryString)) : this.cacheData.cacheItem[name];
                // 调用 callback 返回建议列表的数据
                cb(results);
            },
            createFilter(queryString) {
                return (restaurant) => {
                    return (restaurant.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0);
                };
            },
            fileNameSearch(){
                this.fileNameLoading = true;
                SearchFileName(this.param.appId).then(result=>{
                    this.fileNameLoading = false;
                    if(result && result.codeStatus == 200){
                        this.fileNameList = result.data;
                        if(!this.fileNameList || this.fileNameList.length ==0){
                            this.$success('提示', "不存在文件,请修改实例目录！");
                        }
                    }
                })
            },
            logSearch(){
                this.loading = true;
                LogSearch(this.param).then(result=>{
                    this.loading = false;
                    if(result && result.codeStatus == 200){
                        if( this.logInfoList && this.logInfoList.length >0){
                            this.activeNames = [0]
                        }
                        this.logInfoList = result.data;
                        //缓存数据
                        for(name in this.cacheData.cacheKey){
                            this.setCache(name);
                        }
                    }

                })
            },
            getRemote(keyword){
                this.getlistOption(keyword)
            },
            getlistOption(keyword){
                this.optionLoading = true;
                AppInfolistOption(keyword).then(result=>{
                    this.optionLoading =false;
                    if(result && result.codeStatus == 200){
                        this.listOption = result.data;
                    }
                })

            },

        },

        data(){
            return {
                keyword:"",//查询应用关键词
                logInfoList:[],
                fileNameList:[],
                listOption:[],
                activeNames:[0],
                fileActiveNames:["0"],
                optionLoading:false,
                fileNameLoading:false,
                loading:false,
                cacheData:{
                    cacheItem:{},
                    cacheKey:{
                        "keyword":{ num:10},
                        "fileName":{num:10},
                        "upLine":{num:5},

                    }
                },

                param:{
                    appId:"",
                    keyword:"",
                    tailNum:"1000",
                    upLine:"",
                    fileName:""
                }

            }
        },

    }
</script>

<style scoped>
    .log-search-card-top{
        margin-bottom: 20px;
    }
</style>