import { createApp } from 'vue'
import App from './App.vue'
import VueApexCharts from "vue3-apexcharts";
import store from './store/smartwatch.store'


createApp(App).use(store).use(VueApexCharts).mount('#app')
