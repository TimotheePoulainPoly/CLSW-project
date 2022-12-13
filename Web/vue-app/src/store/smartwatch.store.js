import {createStore} from 'vuex';
import axios from 'axios';

export default createStore({
    /** STATE */
    state() {
        return {
            datapoints: [
                /*{
                    altitude: 5,
                    heartrate: 0,
                    latitude: 0,
                    longitude: 0,
                    speed: 10,
                    timestamp: 0,
                }
                 */
            ]
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
        /*getDefaultXaxisLabels: (state) => {
            let numberOfxAxisLabels = 6;
            let datapoints = state.datapoints;
            console.log("datapoints :                 ", datapoints);
            let xAxisLabels = new Array(numberOfxAxisLabels);
            let timeDifference = Math.trunc((datapoints[datapoints.length - 1].timestamp - datapoints[0].timestamp) / 6);
            for (let k = 0; k < datapoints.length; k++) {
                console.log("TEST ", k, " :         ", datapoints[k].timestamp);
            }
            console.log("time difference : ", timeDifference);
            xAxisLabels.push(new Date(datapoints[0].timestamp));
            console.log(xAxisLabels[0]);
            for (let i = 1; i < xAxisLabels.length; i++) {
                let data = datapoints[i - 1].timestamp + timeDifference;
                console.log("                         DATA : ", data);
                xAxisLabels.push(new Date(data));
                console.log(xAxisLabels[i]);
            }
            return xAxisLabels;
        },*/


    },
    /** MUTATIONS */
    mutations: {
        setWatchDataMutation: (state, payload) => {
            for (let key in payload) {
                state.datapoints.push(payload[key]);
                /*state.datapoints[key].altitude = payload[key].altitude;
                state.datapoints[key].heartrate = payload[key].heartrate;
                state.datapoints[key].latitude = payload[key].latitude;
                state.datapoints[key].longitude = payload[key].longitude;
                state.datapoints[key].speed = payload[key].speed;
                state.datapoints[key].time = payload[key].time;
                 */
                /*
                   console.log("Key: " + key);
                   console.log("Value: " + payload[key]);
                 */
            }
            /*for (let i in state.datapoints) {
                console.log(state.datapoints[i]);
            }*/
            state.datapoints.sort(function(a,b){
                return a.timestamp - b.timestamp;
            });
            for (let i in state.datapoints) {
                console.log(state.datapoints[i].timestamp);
            }
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

                /*
                fetch('https://clsw-fe777-default-rtdb.europe-west1.firebasedatabase.app/data.json')
                    .then(response => {
                        if(response.ok) {
                            console.log("yessss");
                            response.json();
                        }
                    })
                    .then(data => {
                        // do something with the data
                        console.log("yyeeeesss 222");
                    });
                /*
                const snapshot = await db.collection('controllers').doc(id).get();
                let controller = snapshot.data();
                controller.id = snapshot.id
                context.commit('setHeartBeat');*/
            } catch (e) {
                console.log("Error getting firebase data : ", e);
            }
        },
    }
});