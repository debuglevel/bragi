<template>
  <div class="characters">
    <character-table v-bind:characters="characters" />

    <v-form>
      <v-container>
        <v-row>
          <v-col cols="12" md="4">
            <v-text-field
              v-model="form.title"
              :rules="titleRules"
              label="Character title"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="4">
            <v-btn class="mr-4" @click="onSubmit">Add character</v-btn>
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
import CharacterTable from "@/components/CharacterTable.vue";
import axios from "axios";

export default {
  name: "Characters",
  components: {
    CharacterTable,
  },

  data: () => ({
    characters: [
      { id: 1, name: "Character 1", notes: "Notes 1" },
      { id: 2, name: "Character 2", notes: "Notes 2" },
      { id: 3, name: "Character 3", notes: "Notes 3" },
    ],
    form: {
      title: "",
    },
    titleRules: [(v) => !!v || "Title is required"],
  }),
  mounted() {
    axios
      .get("http://localhost:8080/characters/")
      .then((response) => (this.characters = response.data));
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      //alert(JSON.stringify(this.form))
      axios
        .post("http://localhost:8080/characters/", { title: this.form.title })
        .then((response) => this.characters.push(response.data));
    },
  },
};
</script>
