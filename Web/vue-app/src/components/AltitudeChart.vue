<template>
  <apexchart type="scatter" :options="chartOptions" :series="altitudeSerie"></apexchart>
</template>

<script>
export default {
  name: "AltitudeChart",
  props: {
    last24Hours: [],
    xAxisDefaultLabels: []
  },
  computed: {
    altitudes() {
      console.log("COMPUTED altitudes");
      return this.$store.getters.getAltitudes;
    },
    altitudeSerie: function () {
      return [
        {
          name: 'Altitude (m)',
          data: this.getAltitudeDatapoints()
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
          text: "ALTITUDE",
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
            },
          }
        },
        tooltip: {
          theme: 'dark',
          x: {
            format: 'ddd. dd MMM. yyyy. à  HH:mm:ss'
          },
          y: {
            formatter: undefined,
            title: {
              formatter: (seriesName) => seriesName + " : ",
            },
          }
        },
        legend: {
          show: true,
          showForSingleSeries: false,
          offsetX: 0,
          offsetY: 5,
          fontFamily: 'font-family: Montserrat, Arial, sans-serif',
          fontWeight: 800,
          labels: {
            colors: 'white',
            useSeriesColors: false
          },
        },
        yaxis: {
          type: 'numeric',
          title: {
            text: 'Altitude (m)',
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
            },
          }
        },
      }
    },
  },
  methods: {
    getAltitudeDatapoints() {
      //console.log("length : ", this.$store.getters.getDatapoints.length);
      let dataArray = new Array(this.$store.getters.getDatapoints.length);//[14, 8, 29, 11, 3, 55, 49, 20, 45, 32, 77, 78, 57, 59, 9, 59, 68, 67, 27, 57, 91, 77, 75, 70];
      for (let i = 0; i < dataArray.length; i++) {
        //console.log("x : ",this.$store.getters.getDatapoints[i].timestamp);
        //console.log("y : ", this.$store.getters.getDatapoints[i].altitude);
        dataArray[i] =
            {
              x: new Date(this.$store.getters.getDatapoints[i].timestamp).toISOString(),
              y: this.$store.getters.getDatapoints[i].altitude.toFixed(2),
            };
      }
      return dataArray;
    }
  },
}
</script>

<style scoped>

</style>