<template>
  <div class="renderings">
    <h1>Exports</h1>
    <v-btn :href="htmlUrl">HTML</v-btn>
    <v-btn :href="odtUrl">ODT</v-btn>
  </div>
</template>


<script>
// @ is an alias to /src
import RenderingService from "@/api-services/RenderingService";

export default {
  name: "Renderings",
  components: {},

  data: () => ({
    //
  }),
  computed: {
    htmlUrl: function() {
      return RenderingService.getHtmlUrl();
    },
    odtUrl: function() {
      return RenderingService.getOdtUrl();
    }
  }
};
</script>
