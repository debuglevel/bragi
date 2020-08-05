<template>
  <tr>
    <td>
      <a v-bind:href="'chapter.html?id=' + chapter.id">{{ chapter.title }}</a>
    </td>
    <!-- <td>{{ chapter.id }}</td> -->
    <td>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <span v-bind="attrs" v-on="on">Hover me for summary</span>
        </template>
        <span>{{ chapter.summary }}</span>
      </v-tooltip>
    </td>
  </tr>
</template>

<script>
export default {
  name: "ChapterItem",

  props: {
    chapter: {},
  },
  data: () => ({
    //
  }),
};
</script>
