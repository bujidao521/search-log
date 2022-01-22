import Vue from 'vue'
import Vuex from 'vuex'
import {MUTIFY_COLSPAN_VAL,MUTIFY_TAB_VAL_LIST,MUTIFY_OPEN_TAB_VAL} from "./mutation-type"

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isColspan:true,
    tabValList:[],
    openTabVal:null
  },
  mutations: {
    [MUTIFY_COLSPAN_VAL](state,payload){
      state.isColspan = !state.isColspan;
    },
    [MUTIFY_TAB_VAL_LIST](state,payload){
      let index = -1;
      state.tabValList.forEach((val,i)=>{
        if(val.path == payload.path){
          index =i;
          return;
        }
      });

      if(payload.type=="del"){
        //查找对应的index
        if(index > -1){
          state.tabValList.splice(index,1)
          if(state.tabValList.length >0){
            console.log(payload)
            if(payload.active){
              if(index == 0){
                state.openTabVal = state.tabValList[0].path
              }else {
                state.openTabVal = state.tabValList[index-1].path
              }

            }
          }else {
            state.openTabVal = null
          }
        }
      }else {
        if(index < 0){
          delete payload.type;//删除类型字段
          state.tabValList.push(payload)
        }
        state.openTabVal = payload.path;
      }
    },
    [MUTIFY_OPEN_TAB_VAL](state,payload){
      //切换打开的页签
      state.openTabVal = payload
    }
  },
  actions: {
  },
  modules: {
  }
})
