<template>
    <div class="content">

        <m-header></m-header>
        <m-menu/>
        <m-content></m-content>


    </div>
</template>

<script>
    import MHeader from "./children/Header";
    import MMenu from "./children/Menu";
    import MContent from "./children/Content";
    export default {
        name: "HomePage",
        components:{
            MHeader ,MMenu ,MContent
        }
    }
</script>

<style lang="less" scoped>
    .content {
        height: 100%;
    }
</style>