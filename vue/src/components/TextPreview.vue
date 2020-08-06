<template>
  <v-dialog v-model="dialog" width="500">
    <template v-slot:activator="{ on, attrs }">
      <v-btn v-bind="attrs" v-on="on">{{ caption }}</v-btn>
    </template>

    <v-card>
      <v-card-title>{{ title }}</v-card-title>

      <v-card-text>{{ text }}</v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="primary" text @click="dialog = false">Close</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  name: "TextPreview",

  props: {
    caption: {},
    title: {},
    text: {}
  },
  data: () => ({
    dialog: false
  })
};
</script>
