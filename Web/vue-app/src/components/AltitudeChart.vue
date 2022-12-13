<template>
  <div id="second-chart">
    <span>altitudes : {{ altitudes }}</span>
    <apexchart type="scatter" height="350" :options="chartOptions" :series="altitudeSerie"></apexchart>
  </div>
</template>

<script>
export default {
  name: "AltitudeChart",
  props: {
    last24Hours: [],
    xAxisDefaultLabels : []
  },
  computed: {
    altitudes() {
      console.log("COMPUTED altitudes");
      return this.$store.getters.getAltitudes;
    },
    altitudeSerie: function () {
      return [
        {
          name: 'Altitude',
          data: this.getAltitudeDatapoints()
        },
      ];
    },
    chartOptions: function () {
      return {
        chart: {
          height: 350,
              type: 'scatter'
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth'
        },
        xaxis: {
          /*type: 'datetime',*/
          labels: {
            show: false,
          }
        },
        /*categories: null/*this.getDefaultXaxisLabels(), //["2022-12-10T19:00:00.000Z", "2022-12-10T20:00:00.000Z", "2022-12-10T21:00:00.000Z", "2022-12-10T22:00:00.000Z", "2022-12-10T23:00:00.000Z", "2022-12-11T00:00:00.000Z", "2022-12-11T01:00:00.000Z", "2022-12-11T02:00:00.000Z", "2022-12-11T03:00:00.000Z", "2022-12-11T04:00:00.000Z", "2022-12-11T05:00:00.000Z", "2022-12-11T06:00:00.000Z", "2022-12-11T07:00:00.000Z", "2022-12-11T08:00:00.000Z", "2022-12-11T09:00:00.000Z", "2022-12-11T10:00:00.000Z", "2022-12-11T11:00:00.000Z", "2022-12-11T12:00:00.000Z", "2022-12-11T13:00:00.000Z", "2022-12-11T14:00:00.000Z", "2022-12-11T15:00:00.000Z", "2022-12-11T16:00:00.000Z", "2022-12-11T17:00:00.000Z", "2022-12-11T18:00:00.000Z" ]
        // "2018-09-19T00:00:00.000Z", "2018-09-19T01:30:00.000Z", "2018-09-19T02:30:00.000Z", "2018-09-19T03:30:00.000Z", "2018-09-19T04:30:00.000Z", "2018-09-19T05:30:00.000Z", "2018-09-19T06:30:00.000Z"]
      },
        */
        tooltip: {
          x: {
            format: 'dd/MM/yy HH:mm:ss'
          }
        },
      }
    },
  },
  methods: {
    getAltitudeDatapoints() {
      //console.log("length : ", this.$store.getters.getDatapoints.length);
      let dataArray = new Array(this.$store.getters.getDatapoints.length);//[14, 8, 29, 11, 3, 55, 49, 20, 45, 32, 77, 78, 57, 59, 9, 59, 68, 67, 27, 57, 91, 77, 75, 70];
      for (let i = 0 ; i<dataArray.length ; i++) {
        //console.log("x : ",this.$store.getters.getDatapoints[i].timestamp);
        console.log("y : ", this.$store.getters.getDatapoints[i].altitude);
        dataArray[i] =
            {
              x: new Date(this.$store.getters.getDatapoints[i].timestamp).toISOString(),
              y: this.$store.getters.getDatapoints[i].altitude,
            };
      }
      return dataArray;
    }
  },
}
</script>


<style scoped>

</style>