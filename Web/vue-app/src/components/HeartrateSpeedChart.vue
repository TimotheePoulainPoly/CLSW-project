<template>
  <apexchart type="scatter" :options="chartOptions" :series="series"></apexchart>
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
          data: this.getHeartrateDatapoints()
        },
        {
          name: 'Vitesse (km/h)',
          data: this.getSpeedDatapoints()
        },
      ];
    },
    chartOptions: function () {
      return {
        chart: {
          type: 'scatter',
        },
        dataLabels: {
          enabled: false
        },
        title: {
          text: "CARDIO ET VITESSE",
          align: 'left',
          margin: 10,
          offsetX: 0,
          offsetY: 0,
          floating: false,
          style: {
            fontSize: '17px',
            fontWeight: 'bold',
            fontFamily: 'font-family: Montserrat, Arial, sans-serif',
            color: 'white'
          },
        },
        xaxis: {
          type: 'datetime',
          title: {
            text: 'Temps',
            offsetY: 67,
            offsetX: 0,
            style: {
              color: 'white',
              /*fontSize: '12px',*/
              fontFamily: 'font-family: Montserrat, Arial, sans-serif',
              fontWeight: 800,
              /*cssClass: 'apexcharts-xaxis-title',*/
            },

          },
          labels: {
            show: true,
            datetimeUTC: false,
            style: {
              colors: 'white',
              /*fontSize: '12px',*/
              fontFamily: 'font-family: Montserrat, Arial, sans-serif',
              fontWeight: 800,
              /*cssClass: 'apexcharts-xaxis-label',*/
            },
          }
        },

        yaxis: {
          type: 'numeric',
          title: {
            text: 'Rythme Cardiaque Et Vitesse',
            offsetY: 0,
            offsetX: -5,
            style: {
              color: 'white',
              /*fontSize: '12px',*/
              fontFamily: 'font-family: Montserrat, Arial, sans-serif',
              fontWeight: 800,
            },
          },
          labels: {
            show: true,
            style: {
              colors: 'white',
              /*fontSize: '12px',*/
              fontFamily: 'font-family: Montserrat, Arial, sans-serif',
              fontWeight: 800,
              /*cssClass: 'apexcharts-xaxis-label',*/
            },
          }
        },
        legend: {
          offsetX: 0,
          offsetY: 5,
          fontFamily: 'font-family: Montserrat, Arial, sans-serif',
          fontWeight: 800,

          labels: {
            colors: 'white',
            useSeriesColors: false
          },
        },
        tooltip: {
          theme: 'dark',
          x: {
            format: 'ddd. MMM. yyyy. à  HH:mm:ss'
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
      let dataArray = new Array(this.$store.getters.getDatapoints.length);
      for (let i = 0; i < dataArray.length; i++) {
        //console.log("x : ",this.$store.getters.getDatapoints[i].timestamp);
        //console.log("y : ", this.$store.getters.getDatapoints[i].heartrate);
        dataArray[i] =
            {
              x: new Date(this.$store.getters.getDatapoints[i].timestamp).toISOString(),
              y: this.$store.getters.getDatapoints[i].heartrate.toFixed(2),
            };
      }
      return dataArray;
    },
    getSpeedDatapoints() {
      //console.log("length : ", this.$store.getters.getDatapoints.length);
      let dataArray = new Array(this.$store.getters.getDatapoints.length);
      for (let i = 0; i < dataArray.length; i++) {
        //console.log("x : ",this.$store.getters.getDatapoints[i].timestamp);
        //console.log("y : ", this.$store.getters.getDatapoints[i].speed);
        dataArray[i] =
            {
              x: new Date(this.$store.getters.getDatapoints[i].timestamp).toISOString(),
              y: this.$store.getters.getDatapoints[i].speed.toFixed(2),
            };
      }
      return dataArray;
    },
  }
}
</script>

<style scoped>

</style>