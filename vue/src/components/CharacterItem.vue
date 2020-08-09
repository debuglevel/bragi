<template>
  <tr>
    <td>
      <character-chip v-bind:character="character" />
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
import ColorService from "@/services/ColorService";
import CharacterChip from "@/components/CharacterChip.vue";

export default {
  name: "CharacterItem",
  components: {
    TextPreview,
    CharacterChip,
  },

  props: {
    character: {},
  },
  data: () => ({
    //
  }),
  methods: {
    containsKey(obj, key) {
      return Object.keys(obj).includes(key);
    },
  },
  computed: {
    background: function() {
      return ColorService.generateRandomHexColor(this.character.id);
    },
    hasPicture: function() {
      return this.containsKey(this.character, "picture");
    },
  },
};
</script>
