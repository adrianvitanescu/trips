import Vue from '../js/vue';
import Router from '../js/vue-router';
import Trips from '../../templates/Trips';

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Trips',
      component: Trips
    }
  ]
});