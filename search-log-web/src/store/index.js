import Vue from 'vue'
import Vuex from 'vuex'
import {MUTIFYCOLSPANVAL} from "./mutation-type"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isColspan:true,
  },
  mutations: {
    [MUTIFYCOLSPANVAL](state,payload){
      state.isColspan = !state.isColspan;
    }
  },
  actions: {
  },
  modules: {
  }
})
