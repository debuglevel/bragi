<template>
  <v-menu bottom right transition="scale-transition" origin="top left">
    <template v-slot:activator="{ on }">
      <v-chip class="ma-1" pill v-on="on" :color="background">{{
        place.name
      }}</v-chip>
    </template>

    <v-card width="600">
      <v-list>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>{{ place.name }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content class="preformatted-newlines">{{
            place.notes
          }}</v-list-item-content>
        </v-list-item>

        <v-list-item :to="'/places/' + place.id">
          <v-list-item-action>
            <v-icon>mdi-account</v-icon>
          </v-list-item-action>
          <v-list-item-subtitle>Go to place</v-list-item-subtitle>
        </v-list-item>
      </v-list>
    </v-card>
  </v-menu>
</template>

<script>
import ColorService from "@/services/ColorService";

export default {
  name: "PlaceChip",
  components: {},

  props: {
    place: {},
  },
  data: () => ({
    //
  }),
  computed: {
    background: function() {
      return ColorService.generateRandomHexColor(this.place.id);
    },
  },
};
</script>

<style scoped>
.preformatted-newlines {
  white-space: pre-wrap;
}
</style>
