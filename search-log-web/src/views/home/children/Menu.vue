<template>
    <el-menu class="home-menu"
             :default-active="activeMenu" :collapse="!isColspan"
             unique-opened router
             background-color="#545c64"
             text-color="#fff"
             active-text-color="#ffd04b">



        <el-submenu  :index="i+''" v-for="(frist,i) in menuConfig" :key="i">
            <template slot="title">
                <i :class="frist.icon"></i>
                <span>{{frist.title}}</span>
            </template>
            <el-menu-item v-for="(sub,i) in frist.subs" :index="sub.index" :key="sub.index">{{sub.title}} </el-menu-item>
        </el-submenu>



    </el-menu>
</template>

<script>
    import {mapState} from "vuex";
    import {menuConfig} from "@/config/config.js";
    export default {
        name: "Menu",
        data(){
            return {
                menuConfig:menuConfig,
            }
        },
        computed:{
            activeMenu(){
                return this.$route.path
            },
            ...mapState(["isColspan"])
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