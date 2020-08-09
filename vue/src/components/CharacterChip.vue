<template>
  <v-menu bottom right transition="scale-transition" origin="top left">
    <template v-slot:activator="{ on }">
      <v-chip class="ma-1" pill v-on="on" :color="background">
        <v-avatar left v-if="hasPicture">
          <v-img :src="character.picture" />
        </v-avatar>
        {{ character.name }}</v-chip
      >
    </template>

    <v-card width="600">
      <v-list>
        <v-list-item>
          <v-list-item-avatar v-if="hasPicture" size="96">
            <v-img :src="character.picture" />
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>{{ character.name }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content class="preformatted-newlines">{{
            character.notes
          }}</v-list-item-content>
        </v-list-item>

        <v-list-item :to="'/characters/' + character.id">
          <v-list-item-action>
            <v-icon>mdi-account</v-icon>
          </v-list-item-action>
          <v-list-item-subtitle>Go to character</v-list-item-subtitle>
        </v-list-item>
      </v-list>
    </v-card>
  </v-menu>
</template>

<script>
import ColorService from "@/services/ColorService";

export default {
  name: "CharacterChip",
  components: {},

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

<style scoped>
.preformatted-newlines {
  white-space: pre-wrap;
}
</style>
