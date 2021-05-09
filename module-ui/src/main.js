import Vue from 'vue'
import App from './App'
import router from './router'
import CoreuiVue from '@coreui/vue'
import { iconsSet as icons } from './assets/icons/icons.js'
import 'regenerator-runtime'
import Highcharts from 'highcharts'
import VueHighcharts from 'highcharts-vue'
import highchartsMore from 'highcharts/highcharts-more'

Vue.config.performance = true;
Vue.use(CoreuiVue);
highchartsMore(Highcharts);
Vue.use(VueHighcharts, {Highcharts});

new Vue({
  el: '#app',
  router,
  icons,
  template: '<App/>',
  components: {
    App
  }
});
