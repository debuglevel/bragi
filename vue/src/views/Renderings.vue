<template>
  <div class="renderings">
    <h1>Exports</h1>
    <v-card class="mx-auto" max-width="400">
      <v-card-title>HTML</v-card-title>
      <v-card-text>Export as HTML to be viewed in a Browser.</v-card-text>
      <v-card-actions>
        <v-btn :href="htmlUrl" class="primary">Download</v-btn>
      </v-card-actions>
    </v-card>

    <v-card class="mx-auto" max-width="400">
      <v-card-title>OpenDocument</v-card-title>
      <v-card-text>Export as ODT to be viewer or edited in Open/LibreOffice.</v-card-text>
      <v-card-actions>
        <v-btn :href="odtUrl" class="primary">Download</v-btn>
      </v-card-actions>
    </v-card>
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
