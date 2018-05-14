<template>
  <b-form fluid>
    <b-row style="margin-bottom:30px;">
      <b-col xs=12 sm=12 offset-sm="0" md=6 offset-md=3 cols="12">
        <h4>Login</h4>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm=12 offset-sm=0 md=6 offset-md=3 cols="12">
        <b-form-group id="userName"
                      label="User name"
                      label-for="userName">
          <b-input class="mb-2 mr-sm-2 mb-sm-0" id="userName" placeholder="Jane Doe"
                   v-model="userName"
          />
        </b-form-group>
        <b-form-group id="password"
                      label="Password"
                      label-for="password">
          <b-input id="pass" placeholder="password" type="password"
                   v-model="password"/>
        </b-form-group>
        <b-form-checkbox class="mb-2 mr-sm-2 mb-sm-0">Remember me</b-form-checkbox>
        <b-button variant="primary" v-on:click="submit">Login</b-button>
      </b-col>
    </b-row>
  </b-form>

</template>

<script>

  export default {
    name: 'Login',
    data() {
      return {
        userName: null,
        password: null,
        errors: [],
        baseUrl: 'http://localhost:8088/api'
      };
    },
    methods: {
      submit() {
        this.$http.post(`${this.baseUrl}/auth/signin`, {
          userName: this.userName,
          password: this.password
        }).then(response => {
          localStorage.setItem('token', response.data.accessToken);
          this.$http.get(`${this.baseUrl}/user?userName=${this.userName}`).then(response => {
            localStorage.setItem('currentUser', JSON.stringify(response.data));
            this.$router.push({ name: 'Trips' });
            this.$parent.$emit('login');
          }).catch(e => {
            this.errors.push(e);
          });

        }).catch(e => {
          this.errors.push(e);
        });

      }
    }
  };


</script>

<style scoped>

</style>
