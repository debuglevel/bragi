<template>
  <div class="character">
    <!-- TODO: migrate this as well-->
    <h1>Character</h1>
    <div>
      It has the ID {{ character.id }}, was created on {{ character.createdOn }} and
      last modified on {{ character.lastModified }}.
    </div>

    <v-form>
      <v-text-field
        v-model="character.name"
        :rules="nameRules"
        label="Name"
        required
      ></v-text-field>

      <v-textarea v-model="character.notes" label="Notes"></v-textarea>

      <v-textarea v-model="character.aliases" label="Aliases"></v-textarea>

      <v-btn class="mr-4" @click="onSubmit">Update character</v-btn>
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
  name: "Character",
  components: {
  },

  props: ["id"],

  data: () => ({
    character: { 
        "id": 12, 
        "name": "Arya Stark", 
        "notes": "Arya is jst a <b>little</b> girl.", 
        aliases: ["Arya", "Little girl"] 
        },
    nameRules: [(v) => !!v || "Name is required"],
  }),

  mounted() {
    axios
      .get("http://localhost:8080/characters/" + this.id)
      .then(function(characterResponse) {
        // handle success
        console.log("Axios Success for Character");
        console.log(characterResponse);

        this.character = characterResponse.data;
      })
      .catch(function(error) {
        // handle error
        console.log("Axios Error for Character");
        console.log(error);
      })
      .then(function() {
        // always executed
        console.log("Axios Always for Character");
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
        .put("http://localhost:8080/characters/" + this.character.id, {
          name: this.character.name,
          notes: this.character.notes,
          aliases: this.character.aliases,
        })
        .then((characterResponse) => {
          this.character = characterResponse.data;
        });
    },
  },
};
</script>
