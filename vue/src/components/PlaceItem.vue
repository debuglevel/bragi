<template>
  <tr>
    <td>
      <place-chip v-bind:place="place" />
    </td>
    <!-- <td>{{ place.id }}</td> -->
    <td>
      <text-preview
        v-bind:caption="'Show notes'"
        v-bind:title="place.name"
        v-bind:text="place.notes"
      />
    </td>
  </tr>
</template>

<script>
import TextPreview from "@/components/TextPreview.vue";
import ColorService from "@/services/ColorService";
import PlaceChip from "@/components/PlaceChip.vue";

export default {
  name: "PlaceItem",
  components: {
    TextPreview,
    PlaceChip
  },

  props: {
    place: {}
  },
  data: () => ({
    //
  }),
  methods: {
    containsKey(obj, key) {
      return Object.keys(obj).includes(key);
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
