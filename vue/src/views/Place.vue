<template>
  <div class="place">
    <!-- TODO: migrate this as well-->
    <h1>Place</h1>
    <div>
      It has the ID {{ place.id }}, was created on {{ place.createdOn }} and
      last modified on {{ place.lastModified }}.
    </div>

    <v-form>
      <v-text-field v-model="place.name" :rules="nameRules" label="Name" required></v-text-field>

      <v-textarea v-model="place.notes" label="Notes" auto-grow></v-textarea>

      <v-textarea v-model="place.aliases" label="Aliases"></v-textarea>

      <v-btn class="mr-4" @click="onSubmit">Update place</v-btn>
    </v-form>

    <!--        <b-card class="mt-3" header="Form Data Result">-->
    <!--            <pre class="m-0">{{ form }}</pre>-->
    <!--        </b-card>-->
  </div>
</template>

<script>
// @ is an alias to /src
import axios from "axios";

export default {
  name: "Place",
  components: {},

  props: ["id"],

  data: () => ({
    place: {
      id: 12,
      name: "The Wall",
      notes: "Some large bricks of ice.",
      aliases: ["Wall"]
    },
    nameRules: [v => !!v || "Name is required"]
  }),

  mounted() {
    axios
      .get("http://localhost:8080/places/" + this.id)
      .then(placeResponse => {
        // handle success
        console.log("Axios Success for Place");
        console.log(placeResponse);

        this.place = placeResponse.data;
      })
      .catch(error => {
        // handle error
        console.log("Axios Error for Place");
        console.log(error);
      })
      .then(() => {
        // always executed
        console.log("Axios Always for Place");
      });
  },
  created() {
    console.log("ID: " + this.id);
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      //alert(JSON.stringify(this.form))
      axios
        .put("http://localhost:8080/places/" + this.place.id, {
          name: this.place.name,
          notes: this.place.notes,
          aliases: this.place.aliases
        })
        .then(placeResponse => {
          this.place = placeResponse.data;
        });
    }
  }
};
</script>
