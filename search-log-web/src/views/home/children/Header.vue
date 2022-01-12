<template>
    <section class="header">
        <h3>Slog日志管理</h3>
        <i class="el-icon-menu" @click="mutifyColspan"></i>


    </section>
</template>

<script>
    import {mapState} from "vuex";
    import {MUTIFYCOLSPANVAL} from "@/store/mutation-type"

    export default {
        name: "Header",
        data(){
            return {

            }
        },
        computed:{
            ...mapState(["isColspan"])
        },
        methods:{
            mutifyColspan(){
                this.$store.commit(MUTIFYCOLSPANVAL);
            }
        }
    }
</script>

<style scoped lang="less">
    .header {
        position: relative;
        height: 50px;
        line-height: 50px;
        background: #3a8ee6;
        color: #ffffff;
        i{
            margin-left: 20px;
            font-size: 20px;
        }
        h3 {
            display: inline-block;
            margin-left: 30px;
        }

        .header-dropdown {
            margin-right: 30px;
            float: right;
        }
    }
</style>