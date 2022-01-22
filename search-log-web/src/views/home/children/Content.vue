<template>

    <section class="home-content" :style="{left: isColspan?'200px':'64px'}">
        <el-backtop target=".home-content .content" ></el-backtop>

        <div class="content">
            <el-tabs type="card" :before-leave="beforeLeave" v-model="activeName" :closable="closable"  v-if="tabValList.length >0" @tab-remove="removeTab">
                <el-tab-pane :name="tabVal.path" :key="tabVal.path"  :label="tabVal.title" v-for="tabVal in tabValList" >
                    <keep-alive >
                        <router-view :ref="tabVal.path"></router-view>
                    </keep-alive>
                </el-tab-pane>

            </el-tabs>

        </div>

        <footer class="home-footer"></footer>
    </section>

</template>

<script>
    import {mapState} from "vuex";
    import {MUTIFY_TAB_VAL_LIST,MUTIFY_OPEN_TAB_VAL} from "@/store/mutation-type"


    export default {
        name: "Content",
        created() {

        },
        data() {
            return {
                activeName:null
            }
        },
        computed: {
            modelName() {
                return this.$route.name;
            },
            closable(){
                return this.tabValList.length >1
            },

            ...mapState(["isColspan"]),
            ...mapState(["tabValList"]),
            ...mapState(["openTabVal"])

        },
        methods:{
            removeTab(targetName){
                this.$store.commit(MUTIFY_TAB_VAL_LIST,{
                    type:"del",
                    path:targetName,
                    active:this.activeName == targetName
                });
                if(this.$refs[targetName].length >0){
                    this.$refs[targetName][0].$destroy()
                }
            },
            beforeLeave(activeName,oldActiveName){
                this.$router.replace(activeName)
            }
        },
        watch:{
            openTabVal(newName, oldName){
                if(newName != oldName ){
                    this.activeName=newName
                }
            },

        }

    }
</script>

<style lang="less" scoped>
    .home-content {
        position: absolute;
        display: flex;
        flex-direction: column;
        top: 50px;
        right: 0px;
        bottom: 0px;

        transition: left .3s ease-in-out;
        h4{
            padding: 10px;
        }
        .content {
            padding-left: 5px;
            padding-right: 5px;
            overflow-y: auto;
            flex: 1;

        }

        .home-footer {
            display: flex;
            flex-direction: column;
            bottom: 0px;
            height: 15px;
            background: #8cc5ff;
        }


    }




</style>