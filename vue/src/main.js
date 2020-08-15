import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import vuetify from "./plugins/vuetify";
import Axios from "axios";

Vue.config.productionTip = false;

// TODO: a ConfigService or something would probably be nice to put all config variables into.
Axios.defaults.baseURL = process.env.VUE_APP_API_ENDPOINT;
console.log("Using endpoint: " + Axios.defaults.baseURL);

new Vue({
  router,
  vuetify,
  render: (h) => h(App),
}).$mount("#app");
