<template>

    <section class="home-content" :style="{left: isColspan?'200px':'64px'}">
        <el-backtop target=".home-content .content" ></el-backtop>
        <h4>{{modelName}}</h4>
        <div class="content">

            <router-view></router-view>

        </div>

        <footer class="home-footer"></footer>
    </section>

</template>

<script>
    import {mapState} from "vuex";

    export default {
        name: "Content",
        created() {
        },
        computed: {
            modelName() {
                return this.$route.name;
            },
            ...mapState(["isColspan"])
        },

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
            padding: 10px;
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