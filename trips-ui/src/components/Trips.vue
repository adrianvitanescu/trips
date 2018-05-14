<template>
  <b-container fluid>
    <h4>Trips</h4>
    <b-row>
      <b-col cols="6">
        <b-form-select v-model="selected" :options="trips" @change="onChange"
                       class="mb-3"/>
      </b-col>
      <b-col cols="6">
        <b-button variant="secondary"
                  :disabled="!selected"
                  @click="editEvent">Edit
        </b-button>
        <b-button variant="primary" @click="addEvent">Add</b-button>
        <b-button variant="danger"
                  :disabled="!selected"
                  class="delete-button"
                  @click='deleteEvent'>Delete
        </b-button>
      </b-col>
    </b-row>

    <trip-details v-if="selectedTrip"
                  :trip="selectedTrip"></trip-details>
    <trip-modal
      :show="modalShow"
      :isDelete="isDeleteTrip"
      :trip="modalTrip"
      @close="onClose"></trip-modal>
  </b-container>
</template>

<script>
  import TripDetails from './TripDetails';
  import TripModal from './TripModal';

  export default {
    name: 'Trips',
    components: { TripModal, TripDetails },
    data() {
      return {
        allTrips: null,
        trips: [{ value: null, text: 'Please select some item' }],
        baseUrl: 'http://localhost:8088/api',
        selected: null,
        errors: [],
        userId: null,
        modalShow: false,
        selectedTrip: null,
        isDeleteTrip: null,
        modalTrip: null,
        defaultTrip: {
          id: null,
          tripName: null,
          user: null,
          date: null,
          impression: null,
        }
      };
    },
    methods: {
      getTrips() {
        this.$http.get(`${this.baseUrl}/trip?userId=${this.userId}`)
          .then(response => {
            this.allTrips = response.data;
            response.data.forEach(trip => {
              this.trips.push({ text: trip.tripName, value: trip.id });
            });
          }).catch(e => {
          this.errors.push(e);
        });
      },
      onChange(val) {
        if (val != null) {
          this.allTrips.forEach(trip => {
            if (trip.id === val) {
              this.selectedTrip = trip;
            }
          });
        }
      },
      editEvent() {
        this.modalTrip = this.selectedTrip;
        this.modalShow = true;
      },
      addEvent() {
        console.log('add');
        this.modalTrip = {
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
        };
        this.modalShow = true;
      },
      deleteEvent() {
        this.modalTrip = this.selectedTrip;
        this.modalShow = true;
        this.isDeleteTrip = true;
        console.log('delete');

      },
      onClose() {
        this.modalShow = false;
        this.isDeleteTrip = false;
      }
    },
    beforeMount() {
      this.userId = JSON.parse(localStorage.getItem('currentUser')).id;
      this.getTrips();
    },
    mounted() {
      this.$on('close:add', (trip) => {
        this.selectedTrip = trip;
        const newTrip = { text: trip.tripName, value: trip.id };
        this.trips.push(newTrip);
        this.selected = newTrip.value;
        this.modalShow = false;
      });
      this.$on('close:update', (updatedTrip) => {
        this.selectedTrip = updatedTrip;
        this.trips.forEach(trip => {
          if (trip.value === updatedTrip.id) {
            trip.text = updatedTrip.tripName;
          }
        });
        this.modalShow = false;
      });
      this.$on('close:delete', (id) => {
        this.trips.forEach(trip => {
          if (trip.value === id) {
            this.trips.splice(this.trips.indexOf(trip), 1);
          }
        });

        this.isDeleteTrip = false;
        this.selected = null;
        this.modalShow = false;
        this.selectedTrip = null;
      });
    }
  };


</script>

<style scoped>
  .delete-button {
    margin-left:50px;
  }
</style>
