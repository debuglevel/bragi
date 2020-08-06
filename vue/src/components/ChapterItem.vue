<template>
  <tr>
    <td>
      <router-link :to="'chapters/' + chapter.id">
        {{
        chapter.title
        }}
      </router-link>
    </td>
    <!-- <td>{{ chapter.id }}</td> -->
    <td>
      <!--
      TODO: dialog should be used instead of tooltip (https://vuetifyjs.com/en/components/dialogs/) 
      TODO: same for Character and Place
      XXX: create an own ShowNotes component or something like that for consistent usage
      TODO: maybe a expension panel would also be good (https://vuetifyjs.com/en/components/expansion-panels/)
      -->
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <span v-bind="attrs" v-on="on">Hover me</span>
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
    chapter: {}
  },
  data: () => ({
    //
  })
};
</script>
