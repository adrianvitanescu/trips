<template>
  <b-container fluid>
    <b-row>
      <b-col xs=12 sm=12 offset-sm="0" md=8 offset-md=2 cols="12">
        <h2 v-if="form" style="display:inline-block">{{form.firstName}} {{form.lastName}}'s Profile</h2>
        <b-button variant="primary" style="float:right;"
                  :disabled="isEnabled"
                  @click="isEnabled=true">Edit Profile
        </b-button>
      </b-col>
    </b-row>
    <b-row>
      <b-col sm=12 offset-sm=0 md=6 offset-md=3 cols="12">
        <b-form @submit="onSubmit" v-if="form">
          <b-form-group id="firstName"
                        label="First Name"
                        label-for="firstName">
            <b-form-input id="firstName"
                          v-model="form.firstName"
                          required
                          :disabled="!isEnabled"
                          placeholder="Enter First Name">
            </b-form-input>
          </b-form-group>
          <b-form-group id="lastName"
                        label="Last Name"
                        label-for="lastName">
            <b-form-input id="lastName"
                          v-model="form.lastName"
                          :disabled="!isEnabled"
                          required
                          placeholder="Enter Last Name">
            </b-form-input>
          </b-form-group>
          <b-form-group id="userName"
                        label="User Name"
                        label-for="userName">
            <b-form-input id="userName"
                          v-model="form.userName"
                          :disabled="!isEnabled"
                          required
                          placeholder="Enter User name">
            </b-form-input>
          </b-form-group>
          <b-form-group id="password"
                        label="Password"
                        label-for="password">
            <b-form-input id="password"
                          type="password"
                          v-model="form.password"
                          :disabled="!isEnabled"
                          required
                          placeholder="Enter Password">
            </b-form-input>
          </b-form-group>
          <b-form-group id="city"
                        label="City"
                        label-for="city">
            <b-form-input id="city"
                          v-model="form.city"
                          :disabled="!isEnabled"
                          required
                          placeholder="Enter city">
            </b-form-input>
          </b-form-group>

          <b-form-group id="address"
                        label="Address"
                        label-for="address">
            <b-form-input id="address"
                          v-model="form.address"
                          :disabled="!isEnabled"
                          required
                          placeholder="Enter Address">
            </b-form-input>
          </b-form-group>

          <b-form-group id="phone"
                        label="Phone"
                        label-for="phone">
            <b-form-input id="phone"
                          v-model="form.phone"
                          :disabled="!isEnabled"
                          required
                          placeholder="Enter Phone">
            </b-form-input>
            <b-row v-if="isEnabled" class="button-container">
              <b-button variant="secondary" @click="cancel">Cancel</b-button>
              <b-button type="submit" variant="primary">Save</b-button>
            </b-row>
          </b-form-group>
        </b-form>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
  export default {
    name: 'Profile',
    data() {
      return {
        baseUrl: 'http://localhost:8088/api',
        form: null,
        errors: [],
        isEnabled: false
      };
    },
    methods: {
      onSubmit() {
        console.log('submit');
        this.$http.put(`${this.baseUrl}/user/`, this.form).then(response => {
          this.form = response.data;
          localStorage.setItem('currentUser', JSON.stringify(response.data));
        }).catch(e => {
          this.errors.push(e);
        });
        this.isEnabled = false;
      },
      cancel() {
        this.isEnabled = false;
        this.form = JSON.parse(localStorage.getItem('currentUser'));
      },
    },
    beforeMount() {
      const userId = JSON.parse(localStorage.getItem('currentUser')).id;
      this.$http.get(`${this.baseUrl}/user/${userId}`).then(response => {
        this.form = response.data;
      }).catch(e => {
        this.errors.push(e);
      });

    }
  };
</script>

<style scoped>
  .button-container {
    margin-top: 30px;
    float:right;
  }
  .button-container > button:first-of-type {
    margin-right:20px;
  }
</style>
