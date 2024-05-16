import { createStore } from "vuex";
import createPersistedState from 'vuex-persistedstate'

export default createStore({
  state: {
    companyName : null,
    loginId : null,
    logined : false,
    samplePages : false,
  },
  getters: {
    getCompanyName(state){
      return state.companyName;
    },
    getLoginId(state){
      return state.loginId;
    },
    getLogined(state){
      return state.logined;
    },
    getSamplePages(state){
      return state.samplePages;
    }
  },
  mutations: {
    setCompanyName(state, companyName){
      state.companyName = companyName;
    },
    setLoginId(state, loginId){
      state.loginId = loginId;
    },
    setLogined(state, logined){
      state.logined = logined;
    },
    setSamplePages(state, samplePages){
      state.samplePages = samplePages;
    }
  },
  actions: {},
  modules: {},
  plugins: [
    createPersistedState({})
  ],
});
