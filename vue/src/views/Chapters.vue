<template>
  <div class="chapters">
    <v-simple-table>
      <thead>
        <th class="text-left">Title</th>
        <!-- <th class="text-left">ID</th> -->
        <th class="text-left">Show Content</th>
      </thead>
      <tbody>
        <tr
          is="chapter-item"
          v-bind:chapter="item"
          v-bind:key="item.id"
          v-for="item in chapters"
        ></tr>
      </tbody>
    </v-simple-table>

    <v-form>
      <v-container>
        <v-row>
          <v-col cols="12" md="4">
            <v-text-field
              v-model="form.title"
              :rules="titleRules"
              label="Chapter title"
              required
            ></v-text-field>
          </v-col>
          <v-col cols="12" md="4">
            <v-btn class="mr-4" @click="onSubmit">Add chapter</v-btn>
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
import ChapterItem from "@/components/ChapterItem.vue";
import axios from "axios";

export default {
  name: "Chapters",
  components: {
    ChapterItem,
  },

  data: () => ({
    chapters: [
      // { "id": 12, "title": "blubb" }
      { id: 1, title: "Chapter 1", summary: "Summary 1" },
      { id: 2, title: "Chapter 2", summary: "Summary 2" },
      { id: 3, title: "Chapter 3", summary: "Summary 3" },
    ],
    form: {
      title: "",
    },
    titleRules: [(v) => !!v || "Title is required"],
  }),
  mounted() {
    axios
      .get("http://localhost:8080/chapters/")
      .then((response) => (this.chapters = response.data));
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      //alert(JSON.stringify(this.form))
      axios
        .post("http://localhost:8080/chapters/", { title: this.form.title })
        .then((response) => this.chapters.push(response.data));
    },
  },
};
</script>
