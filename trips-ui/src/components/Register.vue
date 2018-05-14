<template>
  <b-container fluid>
    <b-row style="margin-bottom:30px;">
      <b-col xs=12 sm=12 offset-sm="0" md=6 offset-md=3 cols="12">
        <h4>Register</h4>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm=12 offset-sm=0 md=6 offset-md=3 cols="12">
        <b-form @submit="submit">
          <b-form-group id="userName"
                        label="User name"
                        label-for="userName">
            <b-form-input id="userName"
                          v-model="userName"
                          required
                          placeholder="Enter user name">
            </b-form-input>
          </b-form-group>
          <b-form-group id="password"
                        label="Password"
                        label-for="password">
            <b-form-input id="password"
                          type="password"
                          v-model="password"
                          required
                          placeholder="Enter password">
            </b-form-input>
          </b-form-group>
          <b-form-group id="firstName"
                        label="First Name"
                        label-for="firstName">
            <b-form-input id="firstName"
                          v-model="firstName"
                          required
                          placeholder="Enter First Name">
            </b-form-input>
          </b-form-group>
          <b-form-group id="lastName"
                        label="Last Name"
                        label-for="lastName">
            <b-form-input id="lastName"
                          v-model="lastName"
                          required
                          placeholder="Enter Last Name">
            </b-form-input>
          </b-form-group>
          <b-form-group id="city"
                        label="City"
                        label-for="city">
            <b-form-input id="city"
                          v-model="city"
                          required
                          placeholder="Enter city">
            </b-form-input>
          </b-form-group>

          <b-form-group id="address"
                        label="Address"
                        label-for="address">
            <b-form-input id="address"
                          v-model="address"
                          required
                          placeholder="Enter Address">
            </b-form-input>
          </b-form-group>

          <b-form-group id="phone"
                        label="Phone"
                        label-for="phone">
            <b-form-input id="phone"
                          v-model="phone"
                          required
                          placeholder="Enter Phone">
            </b-form-input>
          </b-form-group>
          <b-button type="submit" variant="primary">Submit</b-button>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
  export default {
    name: 'Register',
    data() {
      return {
        baseUrl: 'http://localhost:8088/api',
        userName: null,
        password: null,
        firstName: null,
        lastName: null,
        city: null,
        phone: null,
        address: null,
        errors: []
      };
    },
    methods: {
      submit() {
        this.$http.post(`${this.baseUrl}/auth/signup`, {
          userName: this.userName,
          password: this.password,
          firstName: this.firstName,
          lastName: this.lastName,
          city: this.city,
          phone: this.phone,
          address: this.address,
        }).then(response => {
          this.signIn(this.userName, this.password);
        }).catch(e => {
          this.errors.push(e);
        });
      },
      signIn(userName, password) {
        this.$http.post(`${this.baseUrl}/auth/signin`, {
          userName: userName,
          password: password
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
