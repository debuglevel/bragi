<template>
  <tr>
    <td>
      <router-link :to="'/places/' + place.id">{{ place.name }}</router-link>
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

export default {
  name: "PlaceItem",
  components: {
    TextPreview
  },

  props: {
    place: {}
  },
  data: () => ({
    //
  })
};
</script>
