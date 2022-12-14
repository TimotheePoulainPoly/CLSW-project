<template>
  <div class="dashboard">
    <div class="charts-container">
      <div class="card">
        <HeartrateSpeedChart/>
      </div>
      <div class="card">
        <AltitudeChart/>
      </div>
    </div>
    <div class="map-card">
      <CustomMap/>
    </div>
  </div>
</template>

<script>
import CustomMap from "@/components/CustomMap.vue";
import HeartrateSpeedChart from "@/components/HeartrateSpeedChart.vue";
import AltitudeChart from "@/components/AltitudeChart.vue";

export default {
  name: "Charts",
  components: {CustomMap, AltitudeChart, HeartrateSpeedChart},
  created() {
    console.log("CREATED ...");
    this.setWatchData();
  },
  computed: {
    datapoints() {
      if (this.$store.getters.getDatapoints.length === 0) {
        return; // The datapoints array is empty, so do not compute a value
      } else {
        console.log(this.$store.getters.getDatapoints);
        return this.$store.getters.getDatapoints;
      }
    },
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
      } */
      return last24Hours;
    },
  },
  methods: {
    setWatchData: function () {
      console.log("METHOD : setWatchData method called");
      /*this.$store.commit('setHeartBeat');*/
      this.$store.dispatch('setWatchDataAction');
    },
    /*
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
     */
  }
}
</script>

<style>
.dashboard {
  /*background: red;*/
  display: flex;
  flex-direction: column;
  margin: 0 4% 0 4%;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.charts-container {
  /*background: grey;*/
  display: flex;
  /*margin: 4%;*/
  justify-content: center;
  align-items: center;
  width: 100%;
}

.card {
  /*box-shadow:  0 6px 20px 0 rgba(40, 38, 38, 0.85);*/
  /*background: #35363A; // chrome's dark theme */
  background: #242f3e;
  border-radius: 15px;
  padding: 2% 4% 2% 3%;
  margin: 2% 1% 1% 1%;
  width: 33%;
}

.map-card {
  /*box-shadow:  0 6px 20px 0 rgba(40, 38, 38, 0.85);*/
  /*background: #35363A; // chrome's dark theme */
  background: #242f3e;
  border-radius: 15px;
  padding: 1%;
  margin: 0.5% 0.5% 2% 0.5%;
  width: 80%;
}
</style>