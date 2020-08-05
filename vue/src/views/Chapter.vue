<template>
  <div class="chapter">
    <!-- TODO: migrate this as well-->
    <h1>Chapter</h1>
    <div>
      It has the ID {{ chapter.id }}, was created on {{ chapter.createdOn }} and
      last modified on {{ chapter.lastModified }}.
    </div>

    <v-form>
      <v-text-field
        v-model="chapter.title"
        :rules="titleRules"
        label="Title"
        required
      ></v-text-field>

      <v-textarea v-model="chapter.content" label="Content"></v-textarea>

      <v-textarea v-model="chapter.summary" label="Summary"></v-textarea>

      <v-textarea v-model="chapter.notes" label="Notes"></v-textarea>

      <v-btn class="mr-4" @click="onSubmit">Update chapter</v-btn>
    </v-form>

    <!--        <b-card class="mt-3" header="Form Data Result">-->
    <!--            <pre class="m-0">{{ form }}</pre>-->
    <!--        </b-card>-->

    <h2>Suggested characters</h2>
    <v-simple-table>
      <thead>
        <th class="text-left">Name</th>
        <!-- <th>ID</th> -->
        <th class="text-left">Show Notes</th>
      </thead>
      <tbody>
        <tr
          is="character-item"
          v-bind:character="item"
          v-bind:key="item.id"
          v-for="item in suggestedCharacters"
        ></tr>
      </tbody>
    </v-simple-table>

    <h2>Suggested places</h2>
    <v-simple-table>
      <thead>
        <th class="text-left">Name</th>
        <!-- <th>ID</th> -->
        <th class="text-left">Show Notes</th>
      </thead>
      <tbody>
        <tr
          is="place-item"
          v-bind:key="item.id"
          v-bind:place="item"
          v-for="item in suggestedPlaces"
        ></tr>
      </tbody>
    </v-simple-table>
  </div>
</template>

<script>
// @ is an alias to /src
import CharacterItem from "@/components/CharacterItem.vue";
import PlaceItem from "@/components/PlaceItem.vue";
import axios from "axios";

export default {
  name: "Chapter",
  components: {
    CharacterItem,
    PlaceItem,
  },

  props: ["id"],

  data: () => ({
    chapter: {
      id: 12,
      title: "blubb",
      content: "blubbb blubb <b>bold</b>",
      summary: "sum sum summary",
      notes: "saddsads",
      suggestedCharacters: [121, 122],
      suggestedPlaces: [221, 222],
    },
    suggestedCharacters: [
      { id: 121, name: "Arya", notes: "Little girl" },
      { id: 122, name: "Jon", notes: "Bearded guy" },
    ],
    suggestedPlaces: [
      { id: 221, name: "Winterfell", notes: "Cold." },
      { id: 222, name: "Iron Thrones", notes: "Uncomfortable thing" },
    ],
    titleRules: [(v) => !!v || "Title is required"],
  }),

  mounted() {
    axios
      .get("http://localhost:8080/chapters/" + this.id)
      .then(function(chapterResponse) {
        // handle success
        console.log("Axios Success for Chapter");
        console.log(chapterResponse);

        this.chapter = chapterResponse.data;

        // get all suggested characters
        this.suggestedCharacters = [];
        for (var suggestedCharacterId of this.chapter.suggestedCharacters) {
          axios
            .get("http://localhost:8080/characters/" + suggestedCharacterId)
            .then(function(characterResponse) {
              // handle success
              console.log("Axios Success for Character");
              console.log(characterResponse);

              this.suggestedCharacters.push(characterResponse.data);
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
        }

        // get all suggested places
        this.suggestedPlaces = [];
        for (var suggestedPlaceId of this.chapter.suggestedPlaces) {
          axios
            .get("http://localhost:8080/places/" + suggestedPlaceId)
            .then(function(placeResponse) {
              // handle success
              console.log("Axios Success for Place");
              console.log(placeResponse);

              this.suggestedPlaces.push(placeResponse.data);
            })
            .catch(function(error) {
              // handle error
              console.log("Axios Error for Place");
              console.log(error);
            })
            .then(function() {
              // always executed
              console.log("Axios Always for Place");
            });
        }
      })
      .catch(function(error) {
        // handle error
        console.log("Axios Error for Chapter");
        console.log(error);
      })
      .then(function() {
        // always executed
        console.log("Axios Always for Chapter");
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
        .put("http://localhost:8080/chapters/" + this.chapter.id, {
          title: this.chapter.title,
          content: this.chapter.content,
          summary: this.chapter.summary,
          notes: this.chapter.notes,
        })
        .then((chapterResponse) => {
          this.chapter = chapterResponse.data;

          // TODO: proper axios.then/catch etc handling
          // get all suggested characters
          this.suggestedCharacters = [];
          for (var suggestedCharacterId of this.chapter.suggestedCharacters) {
            axios
              .get("http://localhost:8080/characters/" + suggestedCharacterId)
              .then((characterResponse) =>
                this.suggestedCharacters.push(characterResponse.data)
              );
          }

          // TODO: proper axios.then/catch etc handling
          // get all suggested places
          this.suggestedPlaces = [];
          for (var suggestedPlaceId of this.chapter.suggestedPlaces) {
            axios
              .get("http://localhost:8080/places/" + suggestedPlaceId)
              .then((placeResponse) =>
                this.suggestedPlaces.push(placeResponse.data)
              );
          }
        });
    },
  },
};
</script>
