import { createApp } from 'vue'
import App from './App.vue'
import vuex from '../src/vuex/vuex'
import router from '../src/routes/router'


createApp(App).use(vuex).use(router).mount('#app')
