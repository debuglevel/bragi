<template>
  <div id="placeChips">
    <place-chip v-bind:place="item" v-bind:key="item.id" v-for="item in places" />
  </div>
</template>

<script>
import PlaceChip from "@/components/PlaceChip.vue";

export default {
  name: "PlaceChips",
  components: {
    PlaceChip
  },

  props: {
    places: {}
  },
  data: () => ({
    //
  })
};
</script>
