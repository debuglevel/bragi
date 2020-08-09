<template>
  <div class="character">
    <!-- TODO: migrate this as well-->
    <h1>Character</h1>
    <div>
      It has the ID {{ character.id }}, was created on
      {{ character.createdOn }} and last modified on
      {{ character.lastModified }}.
    </div>

    <v-form>
      <v-text-field v-model="character.name" :rules="nameRules" label="Name" required></v-text-field>

      <!-- TODO: image is larger than it should be -->
      <v-img v-if="hasPicture" :src="character.picture" max-height="150px" contain />
      <v-file-input
        accept="image/*"
        placeholder="Change picture"
        label="Picture"
        @change="changePicture"
      />

      <v-textarea v-model="character.notes" label="Notes" auto-grow rows="1"></v-textarea>

      <!-- TODO: better than textarea, but not ideal (can be edited, but adding new items is not possible) -->
      <tree-view :data="character.aliases" :options="{ modifiable: true }"></tree-view>

      <!-- <v-textarea v-model="character.aliases" label="Aliases"></v-textarea> -->

      <v-btn class="mr-4" @click="onSubmit">Update character</v-btn>
    </v-form>

    <!--        <b-card class="mt-3" header="Form Data Result">-->
    <!--            <pre class="m-0">{{ form }}</pre>-->
    <!--        </b-card>-->
  </div>
</template>

<script>
// @ is an alias to /src
import CharacterService from "@/api-services/CharacterService";
import { TreeView } from "vue-json-tree-view"; // { } is somehow needed: https://github.com/michaelfitzhavey/vue-json-tree-view/issues/21#issuecomment-641537049

export default {
  name: "Character",
  components: {
    TreeView
  },

  props: ["id"],

  data: () => ({
    character: {
      id: 12,
      name: "Arya Stark",
      notes: "Arya is jst a <b>little</b> girl.",
      aliases: ["Arya", "Little girl"]
    },
    nameRules: [v => !!v || "Name is required"]
  }),

  mounted() {
    CharacterService.get(this.id)
      .then(characterResponse => {
        // handle success
        console.log("Axios Success for Character");
        console.log(characterResponse);

        this.character = characterResponse.data;
      })
      .catch(error => {
        // handle error
        console.log("Axios Error for Character");
        console.log(error);
      })
      .then(() => {
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
      CharacterService.update(this.character.id, {
        name: this.character.name,
        notes: this.character.notes,
        aliases: this.character.aliases,
        picture: this.character.picture
      }).then(characterResponse => {
        this.character = characterResponse.data;
      });
    },
    changePicture(file) {
      this.convertPictureToBase64(file);
    },
    convertPictureToBase64(file) {
      console.log("Converting file to Base64 data URL...");

      const reader = new FileReader();
      reader.onload = e => {
        console.log("Read file as data URL.");
        this.character.picture = e.target.result;
      };
      reader.readAsDataURL(file);
    },
    containsKey(obj, key) {
      return Object.keys(obj).includes(key);
    }
  },
  computed: {
    hasPicture: function() {
      return this.containsKey(this.character, "picture");
    }
  }
};
</script>
