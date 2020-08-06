<template>
  <tr>
    <td>
      <router-link :to="'/characters/' + character.id">
        {{
        character.name
        }}
      </router-link>
    </td>
    <!-- <td>{{ character.id }}</td> -->
    <td>
      <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
          <span v-bind="attrs" v-on="on">Hover me</span>
        </template>
        <span>{{ character.notes }}</span>
      </v-tooltip>
    </td>
  </tr>
</template>

<script>
export default {
  name: "CharacterItem",

  props: {
    character: {}
  },
  data: () => ({
    //
  })
};
</script>
