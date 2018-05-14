import Vue from 'vue';
import Router from 'vue-router';
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
import VueLocalStorage from 'vue-localstorage';
import VueResource from 'vue-resource';
import Login from '@/components/Login';
import Trips from '@/components/Trips';
import Profile from '@/components/Profile';
import Register from '@/components/Register';
import VueMapbox from 'vue-mapbox'
import Mapbox from 'mapbox-gl'


Vue.use(Router);
Vue.use(BootstrapVue);
Vue.use(VueLocalStorage);
Vue.use(VueResource);
Vue.use(VueMapbox, { mapboxgl: Mapbox })

Vue.http.interceptors.push((request, next) => {
  request.headers.set('Authorization', 'Bearer ' + localStorage.getItem('token'));
  request.headers.set('Accept', 'application/json');
  next();
});

const router = new Router({
  routes: [
    {
      path: '/',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Register
    },
    {
      path: '/trips',
      name: 'Trips',
      component: Trips
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile
    }
  ]
});
router.beforeEach((to, from, next) => {
  if (localStorage.getItem('token') === null) {
    if (to.name === 'Login' || to.name === 'Register') {
      next();
    } else {
      next({ name: 'Login' });
    }
  } else {
    next();
  }
});

export default router;
