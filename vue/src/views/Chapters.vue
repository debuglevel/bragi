<template>
  <div class="chapters">
    <h1>Chapters</h1>
    <chapter-table v-bind:chapters="chapters" />

    <v-form>
      <v-container>
        <v-row>
          <v-col cols="12" md="4">
            <v-text-field v-model="form.title" :rules="titleRules" label="Chapter title" required></v-text-field>
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
import ChapterService from "@/api-services/ChapterService";
import ChapterTable from "@/components/ChapterTable.vue";

export default {
  name: "Chapters",
  components: {
    ChapterTable
  },

  data: () => ({
    chapters: [
      { id: 1, title: "Chapter 1", summary: "Summary 1" },
      { id: 2, title: "Chapter 2", summary: "Summary 2" },
      { id: 3, title: "Chapter 3", summary: "Summary 3" }
    ],
    form: {
      title: ""
    },
    titleRules: [v => !!v || "Title is required"]
  }),
  mounted() {
    ChapterService.getAll()
      .then(response => {
        this.chapters = response.data;
      })
      .catch(error => {
        console.log(error.response.data);
      });
  },
  methods: {
    onSubmit(evt) {
      evt.preventDefault();
      //alert(JSON.stringify(this.form))
      ChapterService.create({ title: this.form.title }).then(response =>
        this.chapters.push(response.data)
      );
    }
  }
};
</script>
