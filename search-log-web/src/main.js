import Vue from 'vue'
import App from './App.vue'
import router from "./router";
import './plugins/element.js'
import './plugins/vchart.js'
import layer from 'vue-layer'
import 'vue-layer/lib/vue-layer.css';
import store from './store'
import "./assets/styles/style.css"

Vue.config.productionTip = false;


Vue.prototype.$layer = layer(Vue);
Vue.prototype.$layer = layer(Vue, {
  msgtime: 3,//目前只有一项，即msg方法的默认消失时间，单位：秒
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
