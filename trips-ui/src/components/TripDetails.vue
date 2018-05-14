<template>
  <b-container v-if="trip" fluid class="trip-container">
    <h5>Impressions</h5>
    <b-row class="info-container" cols="12">
      <p class="info-interior">{{trip.impression}}</p>
    </b-row>
    <b-row style="margin-top:10px;">
      <b-col cols="8"   v-if="trip.photo[0] || trip.photo[1]">
        <h5>Photos</h5>
      </b-col>
      <b-col cols="4">
        <h5>Dates</h5>
      </b-col>
    </b-row>
    <b-row style="margin-top:10px;">
      <b-col cols="8" v-if="trip.photo[0] || trip.photo[1]">
        <b-row>
          <b-col cols="6" v-if="trip.photo[0]">
            <b-card :title="trip.photo[0].name"
                    :img-src="trip.photo[0].url"
                    img-alt="Img"
                    img-top>
              <p class="card-text">
                {{trip.photo[0].description}}
              </p>
            </b-card>
          </b-col>
          <b-col cols="6" v-if="trip.photo[1]">
            <b-card :title="trip.photo[1].name"
                    :img-src="trip.photo[1].url"
                    img-alt="Img"
                    img-top>
              <p class="card-text">
                {{trip.photo[1].description}}
              </p>
            </b-card>
          </b-col>
        </b-row>
      </b-col>
      <b-col cols="4">
        <b-row class="info-container center-content" cols="12">
          <p class="info-interior">{{trip.dateFrom}} - {{trip.dateTo}}</p>
        </b-row>
        <b-row class="location-container">
          <h5>Location:</h5>
        </b-row>
        <b-row>
          <mgl-map
            @map-load="mapLoaded"
            :accessToken="accessToken"
            :mapStyle.sync="mapStyle"
            :zoom="mapOptions.zoom"
            :center="mapOptions.center"
          />
        </b-row>
      </b-col>
    </b-row>

  </b-container>
</template>

<script>
  import { MglMap } from 'vue-mapbox';
  import { ACCESS_TOKEN, MAP_STYLE } from '../constant';

  export default {
    name: 'trip-details',
    components: { MglMap },
    props: {
      trip: {
        type: Object,
        required: true
      }
    },
    data() {
      return {
        baseUrl: 'http://localhost:8088/api',
        errors: [],
        photoUrl1: null,
        photoUrl2: null,
        accessToken: ACCESS_TOKEN, // your access token. Needed if you using Mapbox maps
        mapStyle: MAP_STYLE, // your map style
        mapOptions: {
          center: [-77.03238901390978, 38.913188059745586],
          zoom: 13
        }
      };
    },
    methods: {
      mapLoaded: function (map) {
        map.addLayer({
          'id': 'points',
          'type': 'symbol',
          'source': {
            'type': 'geojson',
            'data': {
              'type': 'FeatureCollection',
              'features': [{
                'type': 'Feature',
                'geometry': {
                  'type': 'Point',
                  'coordinates': [-77.03238901390978, 38.913188059745586]
                },
                'properties': {
                  'title': 'Mapbox DC',
                  'icon': 'monument'
                }
              }, {
                'type': 'Feature',
                'geometry': {
                  'type': 'Point',
                  'coordinates': [-122.414, 37.776]
                },
                'properties': {
                  'title': 'Mapbox SF',
                  'icon': 'harbor'
                }
              }]
            }
          },
          'layout': {
            'icon-image': '{icon}-15',
            'text-field': '{title}',
            'text-font': ['Open Sans Semibold', 'Arial Unicode MS Bold'],
            'text-offset': [0, 0.6],
            'text-anchor': 'top'
          }
        });
      }
    },
  };
</script>

<style scoped>
  .trip-container {
    margin-top: 10px;
    padding-left: 0;
    padding-right: 0;
    margin-bottom: 50px;
  }

  .info-container {
    margin-left: 0;
    margin-right: 0;
    border: 1px solid #ced4da;
    border-radius: 0.25rem;
  }

  .info-container.center-content {
    justify-content: center;
  }

  .info-container .info-interior {
    padding: 0.375rem 1.75rem 0.375rem 0.75rem;
    margin-bottom: 0;
  }

  .location-container {
    margin-left: 0;
    margin-right: 0;
    margin-top: 10px;
  }

  .mapboxgl-map {
    width: 100%;
    height: 500px;
  }
</style>
