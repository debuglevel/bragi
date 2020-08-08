<template>
  <tr>
    <td>
      <router-link :to="'/chapters/' + chapter.id">
        {{ chapter.title }}
      </router-link>
    </td>
    <!-- <td>{{ chapter.id }}</td> -->
    <td>
      <!--
      TODO: maybe a expension panel would also be good (https://vuetifyjs.com/en/components/expansion-panels/)
      -->
      <text-preview
        v-bind:caption="'Show summary'"
        v-bind:title="chapter.title"
        v-bind:text="chapter.summary"
      />
    </td>
    <td>
      <character-chips v-bind:characters="suggestedCharacters" />
    </td>
  </tr>
</template>

<script>
import TextPreview from "@/components/TextPreview.vue";
import CharacterChips from "@/components/CharacterChips.vue";
import CharacterService from "@/api-services/CharacterService";

export default {
  name: "ChapterItem",
  components: {
    TextPreview,
    CharacterChips,
  },

  props: {
    chapter: {},
    suggestedCharacters: [],
  },
  data: () => ({
    //
  }),
  mounted() {
    // get all suggested characters
    this.suggestedCharacters = [];
    for (var suggestedCharacterId of this.chapter.suggestedCharacters) {
      CharacterService.get(suggestedCharacterId)
        .then((characterResponse) => {
          // handle success
          console.log("Axios Success for Character");
          console.log(characterResponse);

          this.suggestedCharacters.push(characterResponse.data);
        })
        .catch((error) => {
          // handle error
          console.log("Axios Error for Character");
          console.log(error);
        })
        .then(() => {
          // always executed
          console.log("Axios Always for Character");
        });
    }
  },
};
</script>
