<template>
  <div class="sampledata">
    <h1>Populate with sample data</h1>
    <v-card max-width="450" class="mx-auto">
      <v-card-title>Game of Thrones</v-card-title>
      <v-card-text>Populate database with some data from A Wiki of Ice and Fire</v-card-text>
      <v-card-actions>
        <v-btn @click="populateGot">Populate</v-btn>
      </v-card-actions>
    </v-card>

    <v-card max-width="450" class="mx-auto">
      <v-card-title>Book2Graph</v-card-title>
      <v-card-text>Populate database with a Book2Graph ODT file</v-card-text>
      <v-card-actions>
        <v-btn @click="populateBook2Graph">Populate</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>

<script>
// @ is an alias to /src
import SampleDataService from "@/api-services/SampleDataService";

export default {
  name: "Chapters",
  components: {},

  data: () => ({
    //
  }),
  methods: {
    populateGot(evt) {
      evt.preventDefault();
      SampleDataService.populateGot().then(response => alert(response.data));
    },
    populateBook2Graph(evt) {
      evt.preventDefault();
      SampleDataService.populateBook2Graph().then(response =>
        alert(response.data)
      );
    }
  }
};
</script>
