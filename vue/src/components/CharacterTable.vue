<template>
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
        v-for="item in characters"
      ></tr>
    </tbody>
  </v-simple-table>
</template>

<script>
import CharacterItem from "@/components/CharacterItem.vue";

export default {
  name: "CharacterTable",
  components: {
    CharacterItem,
  },

  props: {
    characters: {},
  },
  data: () => ({
    //
  }),
};
</script>
