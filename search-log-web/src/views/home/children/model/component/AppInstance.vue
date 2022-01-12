<template>
    <div style="width: 100%">
        <el-button type="primary" @click="onInsert">新增实例</el-button>

        <el-table
                :data="instanseData.records"
                  style="width: 100%">
            <el-table-column
                    type="index"
                    width="50">
            </el-table-column>
            <el-table-column
                    sortable
                    prop="shellHost"
                    label="实例IP">

            </el-table-column>
            <el-table-column
                    sortable
                    prop="shellUser"
                    label="登录用户">

            </el-table-column>
            <el-table-column
                    sortable
                    prop="logPath"
                    label="日志路径">
            </el-table-column>
            <el-table-column
                    prop="options"
                    label="操作">
                <template slot-scope="scope"   >
                    <el-button  type="primary"   @click="onEdit(scope.row)">修改</el-button>
                    <el-button  type="primary"   @click="onDelete(scope.row.id)">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <el-pagination
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
                :current-page="instanseData.current"
                :page-sizes="[10, 20, 30, 40]"
                :page-size="instanseData.size"
                layout="total, sizes, prev, pager, next, jumper"
                :total="instanseData.total">
        </el-pagination>

        <!--卖出弹出-->
        <el-dialog title="实例信息"  :visible.sync="editVisible" :modal="false">
            <el-form :model="formData">

                <el-form-item label="主机IP"  label-width="20%">
                    <el-input
                            maxlength="20"
                            placeholder="请输入实例IP"
                            v-model="formData.shellHost">

                    </el-input>
                </el-form-item>
                <el-form-item label="登录用户"  label-width="20%">
                    <el-input
                            maxlength="20"
                            placeholder="请输入登录用户"
                            v-model="formData.shellUser">

                    </el-input>
                </el-form-item>
                <el-form-item label="登录密码"  label-width="20%">
                    <el-input
                            maxlength="20"
                            show-password
                            placeholder="请输入登录密码"
                            v-model="formData.shellPass">

                    </el-input>
                </el-form-item>

                <el-form-item label="日志路径"  label-width="20%">
                    <el-input
                            maxlength="70"
                            placeholder="请输入内容"
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
    import {InstanceList,InstanceSaveOrUpdate,InstanceDelete} from "@/api"


    export default {
        name: "AppInstance",
        mounted() {
            this.getInstanceList();
        },

        methods:{
            handleSizeChange(size){
                this.instanseData.size = size;
                this.getInstanceList()
            },
            handleCurrentChange(current){
                this.instanseData.current = current;
                this.getInstanceList();
            },
            getInstanceList(){
                var param = {
                    page:{current:this.instanseData.current,size:this.instanseData.size},
                    appId:this.appId
                };
                InstanceList(param).then(result=>{
                    if(result && result.codeStatus == 200) {
                        this.instanseData = result.data;
                    }
                })
            },
            onDelete(id){
                InstanceDelete(id).then(result=>{
                    if(result && result.codeStatus == 200) {
                        this.getInstanceList();
                        this.$success('提示', "删除成功！");
                    }


                })
            },
            //增加
            onInsert(){
                this.editVisible = true;
                this.formData = {
                    id:"",
                    appId:"",
                    logPath:"",
                    shellHost:"",
                    shellUser:"",
                    shellPass:"",
                };
            },
            //点击查看
            onEdit(row){
                this.editVisible = true;
                this.formData =JSON.parse(JSON.stringify(row));

            },
            onSaveOrUpdate(){
                this.formData.appId =this.appId
                InstanceSaveOrUpdate(this.formData).then(result=>{
                    if(result && result.codeStatus == 200) {
                        this.editVisible = false;
                        this.getInstanceList();
                        this.$success('提示', "保存成功！");
                    }
                })
            },
        },
        props: {

            layerid: {//自动注入的layerid
                type: String,
                default: ''
            },
            appId: {//自动注入的layerid
                type: Number,
                default: ''
            },
            iframeData: {//传递的数据
                default: () => {
                    return [];
                }
            },


        },
        data(){

            return {
                editVisible:false,
                search: '',
                instanseData:{
                    total:0,
                    size: 10,
                    current:1,
                    records:[],
                },
                formData:{
                    id:"",
                    appId:"",
                    logPath:"",
                    shellHost:"",
                    shellUser:"",
                    shellPass:"",
                },
            }
        },
        watch: {

        }
    }
</script>

<style scoped>

</style>