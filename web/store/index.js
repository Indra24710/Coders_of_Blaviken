import Vuex from 'vuex';
import {auth} from '~/plugins/firebase.js';
import {create} from 'lodash';

const createStore = () => {
  return new Vuex.Store({
    state: {
      user: '',
      locales: ['en', 'hi'],
      locale: 'en'
    },
    getters: {
      user() {
        return state.user;
      },
      isAuthenticated() {
        return !!state.user;
      },
    },
    mutations: {
      setUser(state, payload) {
        state.user = payload;
      },
      SET_LANG(state, locale) {
        if (state.locales.indexOf(locale) !== -1) {
          state.locale = locale
        }
      }
    },
    actions: {
      signInWithEmail({commit}, {email, password}) {
        return auth.signInWithEmailAndPassword(email, password);
      },
      signOut() {
        return auth.signOut();
      },
    },
  });
};

export default createStore;
