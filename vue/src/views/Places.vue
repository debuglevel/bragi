<template>
  <div class="places">
    <h1>Places</h1>
    <place-table v-bind:places="places" />

    <v-form>
      <v-container>
        <v-row>
          <v-col cols="12" md="4">
            <v-text-field v-model="form.name" :rules="nameRules" label="Place name" required></v-text-field>
          </v-col>
          <v-col cols="12" md="4">
            <v-btn class="mr-4" @click="onSubmit">Add place</v-btn>
          </v-col>
        </v-row>
      </v-container>
    </v-form>

    <!--        <b-card class="mt-3" header="Form Data Result">-->
    <!--            <pre class="m-0">{{ form }}</pre>-->
    <!--        </b-card>-->
  </div>
</template>

<script>
// @ is an alias to /src
import PlaceService from "@/api-services/PlaceService";
import PlaceTable from "@/components/PlaceTable.vue";

export default {
  name: "Places",
  components: {
    PlaceTable
  },

  data: () => ({
    places: [
      { id: 1, name: "Place 1", notes: "Notes 1" },
      { id: 2, name: "Place 2", notes: "Notes 2" },
      { id: 3, name: "Place 3", notes: "Notes 3" }
    ],
    form: {
      name: ""
    },
    nameRules: [v => !!v || "Name is required"]
  }),
  mounted() {
    PlaceService.getAll()
      .then(response => {
        this.places = response.data;
      })
      .catch(error => {
        console.log(error.response.data);
      });
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      //alert(JSON.stringify(this.form))
      PlaceService.create({ name: this.form.name }).then(response =>
        this.places.push(response.data)
      );
    }
  }
};
</script>
