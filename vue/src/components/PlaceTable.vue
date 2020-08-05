<template>
  <v-simple-table>
    <thead>
      <th class="text-left">Name</th>
      <!-- <th>ID</th> -->
      <th class="text-left">Show Notes</th>
    </thead>
    <tbody>
      <tr
        is="place-item"
        v-bind:key="item.id"
        v-bind:place="item"
        v-for="item in places"
      ></tr>
    </tbody>
  </v-simple-table>
</template>

<script>
import PlaceItem from "@/components/PlaceItem.vue";

export default {
  name: "PlaceTable",
  components: {
    PlaceItem,
  },

  props: {
    places: {},
  },
  data: () => ({
    //
  }),
};
</script>
