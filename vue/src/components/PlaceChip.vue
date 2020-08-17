<template>
  <v-menu bottom right transition="scale-transition" origin="top left">
    <template v-slot:activator="{ on }">
      <v-chip class="ma-1" pill v-on="on" :color="background">
        <v-avatar left v-if="hasPicture">
          <v-img :src="getPictureUrl(96, 96)" />
        </v-avatar>
        {{
        place.name
        }}
      </v-chip>
    </template>

    <v-card width="600">
      <v-list>
        <v-list-item>
          <v-list-item-avatar v-if="hasPicture" size="96">
            <v-img :src="getPictureUrl()" />
          </v-list-item-avatar>

          <v-list-item-content>
            <v-list-item-title>{{ place.name }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-content class="preformatted-newlines">
            {{
            place.notes
            }}
          </v-list-item-content>
        </v-list-item>

        <v-list-item :to="'/places/' + place.id">
          <v-list-item-action>
            <v-icon>mdi-map-marker</v-icon>
          </v-list-item-action>
          <v-list-item-subtitle>Go to place</v-list-item-subtitle>
        </v-list-item>
      </v-list>
    </v-card>
  </v-menu>
</template>

<script>
import ColorService from "@/services/ColorService";
import PictureService from "@/services/PictureService";

export default {
  name: "PlaceChip",
  components: {},

  props: {
    place: {}
  },
  data: () => ({
    //
  }),
  methods: {
    containsKey(obj, key) {
      return Object.keys(obj).includes(key);
    },
    getPictureUrl(maxWidth, maxHeight) {
      if (this.place.picture.startsWith("data:image")) {
        // seems to be a data URL, return value
        return this.place.picture;
      } else if (this.place.picture.startsWith("/")) {
        // seems to be a URL, prepend API URL
        return PictureService.getPictureUrl(this.place.picture, {
          maxWidth: maxWidth,
          maxHeight: maxHeight
        });
      } else {
        // no idea, just return value
        return this.place.picture;
      }
    }
  },
  computed: {
    background: function() {
      return ColorService.generateRandomHexColor(this.place.id);
    },
    hasPicture: function() {
      return this.containsKey(this.place, "picture");
    }
  }
};
</script>

<style scoped>
.preformatted-newlines {
  white-space: pre-wrap;
}
</style>
