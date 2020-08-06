<template>
  <tr>
    <td>
      <router-link :to="'/places/' + place.id">{{ place.name }}</router-link>
    </td>
    <!-- <td>{{ place.id }}</td> -->
    <td>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <span v-bind="attrs" v-on="on">Hover me</span>
        </template>
        <span>{{ place.notes }}</span>
      </v-tooltip>
    </td>
  </tr>
</template>

<script>
export default {
  name: "PlaceItem",

  props: {
    place: {}
  },
  data: () => ({
    //
  })
};
</script>
