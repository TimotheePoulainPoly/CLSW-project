<template>
  <div id="second-chart">
    <span>altitudes : {{ altitudes }}</span>
    <apexchart type="line" height="350" :options="chartOptions" :series="altitudeSerie"></apexchart>
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
          height: 300,
          /*width: 500,*/
          type: 'line',
          dropShadow: {
            enabled: true,
            color: '#000',
            top: 18,
            left: 7,
            blur: 10,
            opacity: 0.2
          },
          toolbar: {
            show: false
          }
        },
        colors: ['#77B6EA', '#545454'],
        dataLabels: {
          enabled: true,
        },
        stroke: {
          curve: 'smooth'
        },
        title: {
          text: 'Altitude',
          align: 'left'
        },
        grid: {
          borderColor: '#e7e7e7',
          row: {
            colors: ['#f3f3f3', 'transparent'], // takes an array which will be repeated on columns
            opacity: 0.5
          },
        },
        markers: {
          size: 1
        },
        xaxis: {
          type: 'datetime',
          categories: this.xAxisDefaultLabels,
          title: {
            text: 'Temps'
          }
        },
        yaxis: {
          title: {
            text: 'Altitude'
          },
          min: 5,
          max: 40
        },
        legend: {
          position: 'top',
          horizontalAlign: 'right',
          floating: true,
          offsetY: -25,
          offsetX: -5
        },

        tooltip: {
          x: {
            format: 'dd/MM/yy HH:mm'
          }
        }

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
              x: new Date(this.$store.getters.getDatapoints[i].timestamp).toString(),
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