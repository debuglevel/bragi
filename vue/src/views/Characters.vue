<template>
  <div class="characters">
    <character-table v-bind:characters="characters" />

    <v-form>
      <v-container>
        <v-row>
          <v-col cols="12" md="4">
            <v-text-field
              v-model="form.name"
              :rules="nameRules"
              label="Character name"
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
import CharacterService from "@/api-services/CharacterService";
import CharacterTable from "@/components/CharacterTable.vue";

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
      name: "",
    },
    nameRules: [(v) => !!v || "Name is required"],
  }),
  mounted() {
    CharacterService.getAll()
      .then((response) => {
        this.characters = response.data;
      })
      .catch((error) => {
        console.log(error.response.data);
      });
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      //alert(JSON.stringify(this.form))
      CharacterService.create({ name: this.form.name }).then((response) =>
        this.characters.push(response.data)
      );
    },
  },
};
</script>
