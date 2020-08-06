<template>
  <tr>
    <td>
      <router-link :to="'/characters/' + character.id">
        {{
        character.name
        }}
      </router-link>
    </td>
    <!-- <td>{{ character.id }}</td> -->
    <td>
      <text-preview
        v-bind:caption="'Show notes'"
        v-bind:title="character.name"
        v-bind:text="character.notes"
      />
    </td>
  </tr>
</template>

<script>
import TextPreview from "@/components/TextPreview.vue";

export default {
  name: "CharacterItem",
  components: {
    TextPreview
  },

  props: {
    character: {}
  },
  data: () => ({
    //
  })
};
</script>
