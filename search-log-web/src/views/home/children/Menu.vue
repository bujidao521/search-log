<template>
    <el-menu class="home-menu" 
             :default-active="activeMenu" :collapse="!isColspan"
             unique-opened router
             background-color="#545c64"
             text-color="#fff"
             active-text-color="#ffd04b">



        <el-submenu  :index="i+''" v-for="(frist,i) in menuConfig" :key="i" >
            <template slot="title">
                <i :class="frist.icon"></i>
                <span>{{frist.title}}</span>
            </template>
            <el-menu-item v-for="(sub,i) in frist.subs"  :index="sub.index" :key="sub.index">{{sub.title }} </el-menu-item>
        </el-submenu>



    </el-menu>
</template>

<script>
    import {mapState} from "vuex";
    import {menuConfig} from "@/config/config.js";
    import {MUTIFY_TAB_VAL_LIST} from "@/store/mutation-type"

    export default {
        name: "Menu",
        data(){
            return {
                menuConfig:menuConfig,
            }
        },
        mounted() {
            //this.$router.replace(this.$route.path)
            this.mutifyTabLValist("add",this.$route.path)
        },
        computed:{
            activeMenu(){
                return this.$route.path
            },
            ...mapState(["isColspan"])
        },
        methods:{
            mutifyTabLValist(type,path){
                let title = path;
                this.menuConfig.forEach((sub,index)=>{
                    sub.subs.forEach((val,index)=>{
                        if(val.index == path){
                            title = val.title
                        }
                    })
                })
                let param = {
                    type,
                    title:title,
                    path:path,

                };
                this.$store.commit(MUTIFY_TAB_VAL_LIST,param);
            },

        },
        watch:{
            //监控活动的菜单，进行页签切换
            activeMenu(newName, oldName){
                if(newName != oldName){
                    //增加页签，并打开对应页签
                    this.mutifyTabLValist("add",this.$route.path)
                }
            }
        },

        created() {


        }
    }
</script>

<style scoped>
    .home-menu{
        position: absolute;
        top:50px;
        left: 0px;
        bottom: 0px;
        overflow-y: auto;
        /*transition: left .3s ease-in-out;*/


    }
    .home-menu:not(.el-menu--collapse) {
        width: 200px;

    }
</style>