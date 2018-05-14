<template>
  <div id="app">
    <b-navbar toggleable="md" type="light" variant="info">
      <b-navbar-brand href="#">My Trip</b-navbar-brand>
      <b-navbar-toggle target="nav_collapse"></b-navbar-toggle>


      <b-collapse is-nav id="nav_collapse">
        <b-navbar-nav class="ml-auto" v-if="isLoggedIn">
          <b-nav-item>
            <router-link to="/trips">Trips</router-link>
          </b-nav-item>
          <b-nav-item>
            <router-link to="/profile">Profile</router-link>
          </b-nav-item>
          <b-nav-item @click="logout">Logout</b-nav-item>
        </b-navbar-nav>

        <b-navbar-nav class="ml-auto" v-if="!isLoggedIn">
          <b-nav-item>
            <router-link to="/">Login</router-link>
          </b-nav-item>
          <b-nav-item>
            <router-link to="/register">Register</router-link>
          </b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>

    <router-view></router-view>
  </div>
</template>

<script>

  export default {
    name: 'app',
    data() {
      return {
        isLoggedIn: false,
        token: null
      };
    },
    methods: {
      logout() {
        this.$router.push({ name: 'Login' });
        localStorage.removeItem('currentUser');
        localStorage.removeItem('token');
        this.isLoggedIn = false;
      }
    },
    beforeMount() {
      this.token = localStorage.getItem('token');
      this.isLoggedIn = this.token != null;
    },
    watch: {
      isLoggedIn: function (val) {
        this.isLoggedIn = val;
      }
    },
    mounted() {
      this.$on('login', () => {
        this.isLoggedIn = true;
      });
    }
  };
</script>

<style scoped>
  .navbar-light {
    background-color: #2A2C33 !important;
    margin-bottom: 40px;
  }

  .navbar-light a {
    color: #cfd2da;
  }

  .navbar-light a:hover {
    color: #fff;
    font-weight: 500;
    text-decoration: none;
  }

  .navbar-light .navbar-nav .nav-link {
    color: #cfd2da;
  }

  .navbar-light .navbar-nav .nav-link:hover {
    color: #fff;
    font-weight: 500;
    text-decoration: none;
  }
</style>
