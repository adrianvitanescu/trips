<template>
  <b-modal hide-footer @close="close" :hide-header-close="noClose"
           v-model="showModal" :no-close-on-backdrop="noClose" :no-close-on-esc="noClose">
    <div slot="modal-header" class="w-100">
      <b-row cols="12" v-if="isDelete" style="padding-left:15px;">
        <h5 style="margin-bottom:0;">{{trip.tripName}}</h5>
      </b-row>
      <b-row cols="12" style="padding-left:15px;" v-if="!isDelete && trip">
        <h5 style="margin-bottom:0;" v-if="trip.id">{{trip.tripName}}</h5>
        <h5 style="margin-bottom:0;" v-if="!trip.id">Create trip</h5>
      </b-row>

    </div>
    <b-form @submit="save" v-if="!isDelete && trip">
      <b-form-group id="name"
                    label="Trip name"
                    label-for="tripName">
        <b-form-input id="tripName"
                      placeholder="Enter trip Name"
                      v-model="form.tripName"
                      required>
        </b-form-input>
      </b-form-group>
      <b-form-group id="date"
                    label="Dates from - to"
                    label-for="tripName">
        <date-picker v-model="form.date" lang="en"
                     id="date" required
                     :range="true"></date-picker>
      </b-form-group>
      <b-form-group id="impression"
                    label="Impressions"
                    label-for="impression">
        <b-form-textarea id="impression"
                         v-model.trim="form.impression"
                         placeholder="Enter your impressions"
                         required
                         :rows="3"></b-form-textarea>
      </b-form-group>
      <h4>Photo 1</h4>
      <b-form-group id="photoUrl1"
                    label="Photo Url"
                    label-for="photoUrl1">
        <b-form-input id="photoUrl1"
                      placeholder="Enter photo url"
                      v-model="form.photo1.url">
        </b-form-input>
      </b-form-group>
      <b-form-group id="photoTitle1"
                    label="Photo Title"
                    label-for="photoTitle1">
        <b-form-input id="photoTitle1"
                      placeholder="Enter photo name"
                      v-model="form.photo1.name">
        </b-form-input>
      </b-form-group>
      <b-form-group id="photoDescription1"
                    label="Photo Description"
                    label-for="photoDescription1">
        <b-form-textarea id="photoDescription1"
                         v-model.trim="form.photo1.description"
                         placeholder="Enter description"
                         :rows="3"></b-form-textarea>
      </b-form-group>
      <h4>Photo 2</h4>
      <b-form-group id="photoUrl2"
                    label="Photo Url"
                    label-for="photoUrl1">
        <b-form-input id="photoUrl2"
                      placeholder="Enter photo url"
                      v-model="form.photo2.url">
        </b-form-input>
      </b-form-group>
      <b-form-group id="photoTitle2"
                    label="Photo Title"
                    label-for="photoTitle2">
        <b-form-input id="photoTitle2"
                      placeholder="Enter photo url"
                      v-model="form.photo2.name">
        </b-form-input>
      </b-form-group>
      <b-form-group id="photoDescription2"
                    label="Photo Description"
                    label-for="photoDescription2">
        <b-form-textarea id="photoDescription2"
                         v-model.trim="form.photo2.description"
                         placeholder="Enter description"
                         :rows="3"></b-form-textarea>
      </b-form-group>
      <b-col class="button-container" v-if="!isDelete">
        <b-button variant="primary" type="submit">Save</b-button>
        <b-button variant="secondary" @click="cancel">Cancel</b-button>

      </b-col>
    </b-form>


    <b-row v-if="isDelete && trip" style="padding-left:15px; padding-right:15px;">
      Are you sure you want to delete {{trip.tripName}} ?
    </b-row>

    <b-row>
      <b-col v-if="isDelete" class="button-container" style="padding-right:15px; margin-bottom:0; margin-top: 15px;">
        <b-button variant="danger" @click="deleteTrip">Delete</b-button>
        <b-button variant="secondary" @click="close">Cancel</b-button>
      </b-col>

    </b-row>

  </b-modal>
</template>

<script>
  import DatePicker from 'vue2-datepicker';

  export default {
    name: 'trip-modal',
    components: { DatePicker },
    props: {
      show: {
        type: Boolean,
        required: true
      },
      isDelete: {
        type: Boolean
      },
      trip: {
        type: Object,
      }
    },
    data() {
      return {
        baseUrl: 'http://localhost:8088/api',
        noClose: true,
        showModal: false,
        form: {
          id: null,
          tripName: null,
          user: null,
          date: null,
          impression: null,
          photo1: {
            url: null,
            name: null,
            description: null
          },
          photo2: {
            url: null,
            name: null,
            description: null
          }
        },
        errors: []
      };
    },
    methods: {
      close: function () {
        this.$emit('close');
      },
      cancel: function(){
        this.$emit('close');
      },
      save: function (evt) {
        evt.preventDefault();
        let obj = {
          photo: []
        };
        obj.id = this.trip.id;
        obj.tripName = this.form.tripName;
        obj.dateFrom = this.form.date[0];
        obj.dateTo = this.form.date[1];
        obj.impression = this.form.impression;
        obj.userId = JSON.parse(localStorage.getItem('currentUser')).id;
        if (this.form.photo1.url != null || this.form.photo1.name != null || this.form.photo1.description !== null) {
          obj.photo.push(this.form.photo1);
        }
        if (this.form.photo2.url != null || this.form.photo2.name != null || this.form.photo2.description !== null) {
          obj.photo.push(this.form.photo2);
        }
        if (obj.photo.length === 0) {
          obj.photo = null;
        }

        this.$http.post(`${this.baseUrl}/trip`, obj).then(response => {
          if (!obj.id) {
            this.$parent.$emit('close:add', response.data);
          } else {
            this.$parent.$emit('close:update', response.data);
          }
        }).catch(e => {
          this.errors.push(e);
        });
      },
      deleteTrip: function () {
        this.$http.delete(`${this.baseUrl}/trip/${this.trip.id}`).then(response => {
          this.$parent.$emit('close:delete', response.data);
        });
      }
    },
    watch: {
      show: function (val) {
        this.showModal = val;
      },
      trip: function (val) {
        this.form = {
          id: val.id,
          tripName: val.tripName,
          date: [val.dateFrom, val.dateTo],
          impression: val.impression,
          photo1: val.photo1,
          photo2: val.photo2,

        };

      }
    }
  };
</script>

<style scoped>
  .mx-datepicker {
    width: 100% !important;
  }

  .mx-datepicker .mx-input input {
    line-height: 1.5 !important;
    height: 25px !important;
    padding: 0.375rem 0.75rem !important;
  }

  .button-container {
    padding-right:0;
  }

  .button-container button {
    float: right;
  }

  .button-container button:last-child {
    margin-right: 10px;
  }
</style>
