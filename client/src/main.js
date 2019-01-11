import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false

import VueLogger from 'vuejs-logger';

const options = {
  isEnabled: true,
  logLevel : 'debug',
  stringifyArguments : false,
  showLogLevel : true,
  showMethodName : false,
  separator: '|',
  showConsoleColors: true
};

Vue.use(VueLogger, options);

/* eslint-disable no-new */
new Vue({
  /*el: '#app',
  template: '<App/>',
  components: { App }*/
  render: h => h(App),
}).$mount('#app')
