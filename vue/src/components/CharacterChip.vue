<template>
  <v-menu v-model="menu" bottom right transition="scale-transition" origin="top left">
    <template v-slot:activator="{ on }">
      <v-chip class="ma-1" pill v-on="on">{{character.name}}</v-chip>
    </template>

    <v-card width="600">
      <v-list>
        <v-list-item>
          <v-list-item-content>
            <v-list-item-title>{{character.name}}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item>
          <v-list-item-subtitle>{{character.notes}}</v-list-item-subtitle>
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
export default {
  name: "CharacterItem",
  components: {},

  props: {
    character: {}
  },
  data: () => ({
    //
  })
};
</script>
