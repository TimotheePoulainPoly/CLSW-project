import {createStore} from 'vuex';
import axios from 'axios';

export default createStore({
    /** STATE */
    state() {
        return {
            datapoints: []
        }
    },
    /** GETTERS */
    getters: {
        getDatapoints: (state) => {
            return state.datapoints;
        },
        getAltitudes: (state) => {
            let altitudes = [];
            for (let i in state.datapoints) {
                altitudes.push(state.datapoints[i].altitude);
            }
            console.log("GETTERS altitudes = ", altitudes);
            return altitudes;
        },
        getHeartRates: (state) => {
            let heartrates = [];
            for (let i in state.datapoints) {
                heartrates.push(state.datapoints[i].heartrate);
            }
            console.log("GETTERS heartrates = ", heartrates);
            return heartrates;
        },
        getSpeeds: (state) => {
            let speeds = [];
            for (let i in state.datapoints) {
                speeds.push(state.datapoints[i].speed);
            }
            console.log("GETTERS speeds = ", speeds);
            return speeds;
        },
        getLatitudes: (state) => {
            let latitudes = [];
            for (let i in state.datapoints) {
                latitudes.push(state.datapoints[i].latitude);
            }
            console.log("GETTERS latitudes = ", latitudes);
            return latitudes;
        },
        getLongitudes: (state) => {
            let longitudes = [];
            for (let i in state.datapoints) {
                longitudes.push(state.datapoints[i].longitude);
            }
            console.log("GETTERS longitudes = ", longitudes);
            return longitudes;
        },
    },
    /** MUTATIONS */
    mutations: {
        setWatchDataMutation: (state, payload) => {
            for (let key in payload) {
                state.datapoints.push(payload[key]);
            }
            state.datapoints.sort(function(a,b){
                return a.timestamp - b.timestamp;
            });
            console.log("MUTATION : firebase data saved.");
        },
    },
    /** ACTIONS */
    actions: {
        setWatchDataAction(context) {
            try {
                axios.get('https://clsw-fe777-default-rtdb.europe-west1.firebasedatabase.app/data.json')
                    .then(response => {
                        console.log("ACTION : fetching the data from firebase ...");
                        context.commit('setWatchDataMutation', response.data);
                        /*console.log(response.data);*/
                    })
            } catch (e) {
                console.log("Error getting firebase data : ", e);
            }
        },
    }
});