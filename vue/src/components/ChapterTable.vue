<template>
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
</template>

<script>
import ChapterItem from "@/components/ChapterItem.vue";

export default {
  name: "ChapterTable",
  components: {
    ChapterItem,
  },

  props: {
    chapters: {},
  },
  data: () => ({
    //
  }),
};
</script>
