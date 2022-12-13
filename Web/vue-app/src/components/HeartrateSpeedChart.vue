<template>
  <!--{{ datapoints }}-->
  <div id="chart">
    <!--
    <span>heartrates : {{ heartRates }} </span>
    <br/><br/>
    <span>and speeds : {{ speeds }}</span>
    -->
    <!--<span> : {{xAxisDefaultLabels}}</span>-->
    <apexchart type="scatter" height="300" :options="chartOptions" :series="series"></apexchart>
  </div>
</template>

<script>
export default {
  name: "HeartrateSpeedChart",
  props: {
    last24Hours: [],
    xAxisDefaultLabels: []
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
    heartRates() {
      console.log("COMPUTED heartrates");
      return this.$store.getters.getHeartRates;
    },
    speeds() {
      console.log("COMPUTED speeds");
      return this.$store.getters.getSpeeds;
    },
    series: function () {
      return [

        {
          name: 'Rythme Cardiaque (bpm)',
          data: this.getHeartrateDatapoints() // [14, 8, 29, 11, 3, 55, 49, 20, 45, 32, 77, 78, 57, 59, 9, 59, 68, 67, 27, 57, 91, 77, 75, 70]
        },
        {
          name: 'Vitesse (km/h)',
          data: this.getSpeedDatapoints() //[14, 59, 31, 82, 51, 10, 67, 26, 9, 74, 16, 50, 93, 15, 65, 84, 48, 56, 9, 87, 15, 12, 21, 11]
        },
      ];
    },
    chartOptions: function () {
      return {
        chart: {
          height: 300,
          type: 'scatter'
        },
        dataLabels: {
          enabled: false
        },
        stroke: {
          curve: 'smooth'
        },
        title: {
          text: "CARDIO ET VITESSE",
          align: 'left',
          margin: 10,
          offsetX: 0,
          offsetY: 0,
          floating: false,
          style: {
            fontSize:  '17px',
            fontWeight:  'bold',
            fontFamily:  undefined,
            color:  'black'
          },
        },

        xaxis: {
          type: 'datetime',
          title: {
            text: 'Temps',
            offsetY: 70,
            offsetX: 0,

          },
          labels: {
            show: true,
          }
        },
        /*categories: null/*this.getDefaultXaxisLabels(), //["2022-12-10T19:00:00.000Z", "2022-12-10T20:00:00.000Z", "2022-12-10T21:00:00.000Z", "2022-12-10T22:00:00.000Z", "2022-12-10T23:00:00.000Z", "2022-12-11T00:00:00.000Z", "2022-12-11T01:00:00.000Z", "2022-12-11T02:00:00.000Z", "2022-12-11T03:00:00.000Z", "2022-12-11T04:00:00.000Z", "2022-12-11T05:00:00.000Z", "2022-12-11T06:00:00.000Z", "2022-12-11T07:00:00.000Z", "2022-12-11T08:00:00.000Z", "2022-12-11T09:00:00.000Z", "2022-12-11T10:00:00.000Z", "2022-12-11T11:00:00.000Z", "2022-12-11T12:00:00.000Z", "2022-12-11T13:00:00.000Z", "2022-12-11T14:00:00.000Z", "2022-12-11T15:00:00.000Z", "2022-12-11T16:00:00.000Z", "2022-12-11T17:00:00.000Z", "2022-12-11T18:00:00.000Z" ]
        // "2018-09-19T00:00:00.000Z", "2018-09-19T01:30:00.000Z", "2018-09-19T02:30:00.000Z", "2018-09-19T03:30:00.000Z", "2018-09-19T04:30:00.000Z", "2018-09-19T05:30:00.000Z", "2018-09-19T06:30:00.000Z"]
      },
        */
        tooltip: {
          x: {
            format: 'dd/MM/yy HH:mm:ss'
          },
          y: {
            formatter: undefined,
            title: {
              formatter: (seriesName) => seriesName + " : ",
            },
          }
        }
      }
    },
  },
  methods: {
    getHeartrateDatapoints() {
      //console.log("length : ", this.$store.getters.getDatapoints.length);
      let dataArray = new Array(this.$store.getters.getDatapoints.length);//[14, 8, 29, 11, 3, 55, 49, 20, 45, 32, 77, 78, 57, 59, 9, 59, 68, 67, 27, 57, 91, 77, 75, 70];
      for (let i = 0; i < dataArray.length; i++) {
        //console.log("x : ",this.$store.getters.getDatapoints[i].timestamp);

        //console.log("y : ", this.$store.getters.getDatapoints[i].heartrate);
        dataArray[i] =
            {
              x: new Date(this.$store.getters.getDatapoints[i].timestamp).toISOString(),
              y: this.$store.getters.getDatapoints[i].heartrate,
            };
      }
      return dataArray;
    },
    getSpeedDatapoints() {
      //console.log("length : ", this.$store.getters.getDatapoints.length);
      let dataArray = new Array(this.$store.getters.getDatapoints.length);//[14, 8, 29, 11, 3, 55, 49, 20, 45, 32, 77, 78, 57, 59, 9, 59, 68, 67, 27, 57, 91, 77, 75, 70];
      for (let i = 0; i < dataArray.length; i++) {
        //console.log("x : ",this.$store.getters.getDatapoints[i].timestamp);
        //console.log("y : ", this.$store.getters.getDatapoints[i].speed);
        dataArray[i] =
            {
              x: new Date(this.$store.getters.getDatapoints[i].timestamp).toISOString(),
              y: this.$store.getters.getDatapoints[i].speed,
            };
      }
      return dataArray;
    },
  }
}
</script>

<style scoped>

</style>