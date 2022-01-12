<template>
  <div id="app" >
    <router-view></router-view>
  </div>
</template>

<script>

export default {
  name: 'app',
  components: {
  }
}
</script>

<style lang="less">
@import "./assets/styles/base.less";
  #app{
    height: 100%;
  }
</style>
