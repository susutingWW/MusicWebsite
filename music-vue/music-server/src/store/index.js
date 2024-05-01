import Vue from 'vue'
import Vuex from 'vuex'
import configure from './configure'
import user from './user'
import song from './song'

Vue.use(Vuex)

const store = new Vuex.Store({
  state:{
    token: localStorage.getItem('token') ? localStorage.getItem('token') : ''
  },
  modules:{
    configure,
    user,
    song
  },
  mutations:{
    changeLogin (state, user) {
      state.token = user.token;
      localStorage.setItem('token', user.token);
    }
  }
})
export default store
