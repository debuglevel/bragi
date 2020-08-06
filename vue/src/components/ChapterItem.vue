<template>
  <tr>
    <td>
      <router-link :to="'/chapters/' + chapter.id">
        {{
        chapter.title
        }}
      </router-link>
    </td>
    <!-- <td>{{ chapter.id }}</td> -->
    <td>
      <!--
      TODO: maybe a expension panel would also be good (https://vuetifyjs.com/en/components/expansion-panels/)
      -->
      <text-preview
        v-bind:caption="'Show summary'"
        v-bind:title="chapter.title"
        v-bind:text="chapter.summary"
      />
    </td>
  </tr>
</template>

<script>
import TextPreview from "@/components/TextPreview.vue";

export default {
  name: "ChapterItem",
  components: {
    TextPreview
  },

  props: {
    chapter: {}
  },
  data: () => ({
    //
  })
};
</script>
