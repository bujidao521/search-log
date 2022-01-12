<template>
    <div>


        <el-button type="primary" @click="onInsert">新增应用</el-button>

        <el-table
                :data="appInfo.records"
                style="width: 100%">
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>
            <el-table-column
                    prop="appName"
                    label="应用名称"
                   >

            </el-table-column>

            <el-table-column
                    prop="logPath"
                    label="日志路径">
            </el-table-column>
            <el-table-column
                    width="120"
                    prop="instanceCount"
                    label="实例数量">
            </el-table-column>

            <el-table-column
                    prop="options"
                    label="操作">
                <template slot-scope="scope" >
                    <el-button  type="primary"   @click="onEdit(scope.row)">修改</el-button>
                    <el-button  type="primary"   @click="onDelete(scope.row.id)">删除</el-button>
                    <el-button  type="primary"   @click="onView(scope.row)">查看实例</el-button>
                    <el-button v-if="scope.row.instanceCount >0"  type="primary"   @click="searchLog(scope.row.id)">查询日志</el-button>
                </template>
            </el-table-column>

        </el-table>
        <el-pagination
                      @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                :current-page="appInfo.current"
                :page-sizes="[10, 20, 30, 40]"
                :page-size="appInfo.size"
                layout="total, sizes, prev, pager, next, jumper"
                :total="appInfo.total">
        </el-pagination>
        <!--编辑弹出-->

        <!--卖出弹出-->
        <el-dialog title="应用信息"  :visible.sync="editVisible" width="400px" >
            <el-form :model="formData">

                <el-form-item label="应用名称:"  label-width="90px"  >
                    <el-input
                              maxlength="15"
                              placeholder="请输入名称"
                              v-model="formData.appName">

                    </el-input>
                </el-form-item>


                <el-form-item label="日志路径:"  label-width="90px">
                    <el-input
                            maxlength="70"
                            placeholder="请输入路径"
                            v-model="formData.logPath">

                    </el-input>
                </el-form-item>

                <el-form-item>
                    <el-button type="primary"  @click="onSaveOrUpdate">保存</el-button>
                </el-form-item>
            </el-form>
        </el-dialog>
    </div>
</template>

<script>
    import {AppInfoList,AppInfoSaveOrUpdate,AppInfoDelete} from "@/api"
    import AppInstance from "./component/AppInstance";
    export default {
        name: "AppInfo",
        mounted() {
            this.queryAppInfo();
        },
        methods:{

            searchLog(id){
              this.$router.replace(`/LogSearch?appId=${id}`);
            },
            handleSizeChange(size){
                this.appInfo.size = size;
                this.queryAppInfo()
            },
            handleCurrentChange(current){
                this.appInfo.current = current;
                this.queryAppInfo();
            },
            queryAppInfo(){
                var param = {
                    page:{current:this.appInfo.current,size:this.appInfo.size},
                };

                AppInfoList(param).then(result=>{
                    if(result && result.codeStatus == 200){
                        this.appInfo = result.data;
                    }
                })
            },
            onDelete(id){
                AppInfoDelete(id).then(result=>{
                    if(result && result.codeStatus == 200){
                        this.$success('提示', "删除成功！");
                        this.queryAppInfo();
                    }
                })
            },
            //增加
            onInsert(){
                this.editVisible = true;
                this.formData = {
                    id:"",
                    appName:"",
                    logPath:""
                };
            },
            //点击查看
            onView(row){
                let data = row.list;
                this.$layer.iframe({
                    content: {
                        content: AppInstance,
                        parent: this,
                        data: { iframeData: data,appId:row.id }
                    },
                    area: ['1000px', '600px'],
                    title: "实例信息",
                    maxmin: true,
                    shade: false,
                    shadeClose: false,
                    cancel: () => { //关闭弹窗事件

                    }
                });

            },
            //点击查看
            onEdit(row){
                this.editVisible = true;
                this.formData =JSON.parse(JSON.stringify(row));

            },
            //点击修改，保存

            onSaveOrUpdate(){
                AppInfoSaveOrUpdate(this.formData).then(result=>{
                    this.editVisible = false;
                    if(result && result.codeStatus == 200) {
                        this.queryAppInfo();
                        this.$success('提示', "保存成功！");
                    }
                })
            },



        },

        data(){
            return {
                editVisible:false,
                loading: false,
                appInfo:{
                    total:0,
                    size: 10,
                    current:1,
                    records:[],
                },
                searchKey:"",
                formData:{
                    id:"",
                    appName:"",
                    logPath:"",
                },

            }
        }
    }
</script>

<style scoped>

</style>