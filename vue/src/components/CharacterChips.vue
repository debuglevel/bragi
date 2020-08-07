<template>
  <div id="characterChips">
    <character-chip v-bind:character="item" v-bind:key="item.id" v-for="item in characters" />
  </div>
</template>

<script>
import CharacterChip from "@/components/CharacterChip.vue";

export default {
  name: "CharacterChips",
  components: {
    CharacterChip
  },

  props: {
    characters: {}
  },
  data: () => ({
    //
  })
};
</script>
