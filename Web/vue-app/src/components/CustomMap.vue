<template>
  <GoogleMap api-key="AIzaSyDmAiqU5K62eyziujPM4gdbCDGlulOgzFY" style="width: 100%; height: 400px" :center="center"
             :zoom="15" :styles="dark()">
    <MarkerCluster>
      <Marker v-for="(datapoint, i) in datapoints"
              :options="{ position: { lat: datapoint.latitude, lng: datapoint.longitude }}" :key="i">
        <InfoWindow>
          <div id="content">
            <div id="siteNotice"></div>
            <h2>
              {{new Date(datapoint.timestamp).toLocaleString('fr-FR', { weekday: 'short', year: 'numeric', month: 'short', day: 'numeric' })}}
            </h2>
            <h3> À {{new Date(datapoint.timestamp).toLocaleTimeString()}}</h3>
            <div id="bodyContent">
              <h4>Rythme cardiaque : {{ datapoint.heartrate.toFixed(2) }} (bpm)</h4>
              <h4>Vitesse : {{ datapoint.speed.toFixed(2) }} (km/h)</h4>
              <h4>Altitude : {{ datapoint.altitude.toFixed(2) }} (m)</h4>
            </div>
          </div>
        </InfoWindow>
      </Marker>
    </MarkerCluster>
  </GoogleMap>
</template>

<script>
import {GoogleMap, InfoWindow, Marker, MarkerCluster} from "vue3-google-map";
import {dark} from "vue3-google-map/themes";

export default {
  name: "CustomMap",
  components: {GoogleMap, Marker, MarkerCluster, InfoWindow},

  computed: {
    center() {
      let center = {lat: 43.61521833333333, lng: 7.071561666666667};
      return center;
    },
    locations() {
      let latitudes = this.$store.getters.getLatitudes;
      let longitudes = this.$store.getters.getLongitudes;
      let locations = new Array(latitudes.length);
      for (let i = 0; i < locations.length; i++) {
        locations[i] =
            {
              lat: latitudes[i],
              lng: longitudes[i]
            }
      }
      return locations;
    },
    datapoints() {
      return this.$store.getters.getDatapoints;
    },
  },
  methods: {
    dark() {
      return dark
    }
  },
}
</script>

<style scoped>

</style>


