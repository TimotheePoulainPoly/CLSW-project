<template>
  <!--{{ datapoints }}-->
  <HeartrateSpeedChart/><!-- v-bind:xAxisDefaultLabels="datapoints === undefined ?  null : defaultXaxisLabels"/>-->
  <br/><br/><br/><br/><br/>
  <!--<AltitudeChart/>v-bind:xAxisDefaultLabels="getDefaultXaxisLabels"/>-->
</template>


<script>
import HeartrateSpeedChart from "@/components/HeartrateSpeedChart.vue";
import AltitudeChart from "@/components/AltitudeChart.vue";
import {ref} from "vue";

export default {
  name: "Charts",
  components: {AltitudeChart, HeartrateSpeedChart},
  created() {
    console.log("CREATED ...");
    this.setWatchData();
  },
  /*
  data() {
    return {
      datapoints: this.$store.getters.getDatapoints
    };
  },
  watch: {
    datapoints(newValue, oldValue) {
      console.log(`datapoints changed from ${oldValue} to ${newValue}`);
      if (newValue.length != 0)
        return this.$store.getters.getDatapoints;
    }
  },
  */
  computed: {
    datapoints() {
      if (this.$store.getters.getDatapoints.length === 0) {
        return; // The datapoints array is empty, so do not compute a value
      } else {
        console.log(this.$store.getters.getDatapoints);
        return this.$store.getters.getDatapoints;
      }
    },
    /*
    defaultXaxisLabels() {
      if (this.$store.getters.getDatapoints == []) {
        return;
      } else {
        return this.getDefaultXaxisLabels();
      }
    },
    */
    roundToNextHour() {
      let currentDate = new Date();
      currentDate.setMinutes(currentDate.getMinutes() + 60);
      currentDate.setMinutes(0, 0, 0);
      return currentDate;
    },
    getLast24Hours() {
      let nextHourDate = this.roundToNextHour;
      let nextHourTimeStamp = nextHourDate.getTime();
      /*console.log("L'heure prochaine la plus proche est : ", nextHourDate);*/
      let last24Hours = [];
      for (let i = 0; i < 24; i++) {
        /*console.log(nextHourDate, i);*/
        let previousHour = nextHourTimeStamp - (1000 * 60 * 60 * i);
        /*console.log("après le setTime : ", previousHour, i);*/
        last24Hours.push(new Date(previousHour));
        /*console.log("le tableau : ", last24Hours[i]);*/
        //nextHourDate.setTime(nextHourDate.getTime() - (1000 * 60 * 60));
        //nextHourDate.setTime(nextHourDate.getTime() - (1000*60*60*(i+1)));
      }
      last24Hours.reverse();
      /*console.log(nextHourDate);*/
      /*for (let k = 0; k < 24; k++) {
        console.log("finalement :   ", last24Hours[k]);
      }
       */
      return last24Hours;
    },
  },
  methods: {
    setWatchData: function () {
      console.log("METHOD : setWatchData method called");
      /*this.$store.commit('setHeartBeat');*/
      this.$store.dispatch('setWatchDataAction');
    },
    getDefaultXaxisLabels() {
      let numberOfxAxisLabels = 6;
      //console.log("datapoints : ", datapoints);
      let xAxisLabels = new Array(numberOfxAxisLabels);
      let timeDifference = Math.trunc((this.datapoints[this.datapoints.length - 1].timestamp - this.datapoints[0].timestamp) / 6);
      for (let k = 0; k < this.datapoints.length; k++) {
        //console.log("TEST ", k, " : ", datapoints[k].timestamp);
      }
      //console.log("time difference : ", timeDifference);
      xAxisLabels.push(new Date(this.datapoints[0].timestamp));
      //console.log(xAxisLabels[0]);
      for (let i = 1; i < xAxisLabels.length; i++) {
        let data = this.datapoints[i - 1].timestamp + timeDifference;
        //console.log(" DATA : ", data);
        xAxisLabels.push(new Date(data));
        //console.log(xAxisLabels[i]);
      }
      return xAxisLabels;
    },
    /*
    displayTest() {
      let array = new Array(this.$store.getters.getDatapoints.length);
      console.log("DISPLAY ", this.$store.getters.getDatapoints.length);
      for (let i = 0; i < array.length; i++) {
        array[i] = this.$store.getters.getDatapoints[i].timestamp;
      }
      return array;
    }*/
  }
}
</script>


<style scoped>

</style>