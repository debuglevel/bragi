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
    <td>
      <place-chips v-bind:places="suggestedPlaces" />
    </td>
  </tr>
</template>

<script>
import TextPreview from "@/components/TextPreview.vue";
import CharacterChips from "@/components/CharacterChips.vue";
import CharacterService from "@/api-services/CharacterService";
import PlaceChips from "@/components/PlaceChips.vue";
import PlaceService from "@/api-services/PlaceService";

export default {
  name: "ChapterItem",
  components: {
    TextPreview,
    CharacterChips,
    PlaceChips,
  },

  props: {
    chapter: {},
    suggestedCharacters: [],
    suggestedPlaces: [],
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

    // get all suggested places
    this.suggestedPlaces = [];
    for (var suggestedPlaceId of this.chapter.suggestedPlaces) {
      PlaceService.get(suggestedPlaceId)
        .then((placeResponse) => {
          // handle success
          console.log("Axios Success for Place");
          console.log(placeResponse);

          this.suggestedPlaces.push(placeResponse.data);
        })
        .catch((error) => {
          // handle error
          console.log("Axios Error for Place");
          console.log(error);
        })
        .then(() => {
          // always executed
          console.log("Axios Always for Place");
        });
    }
  },
};
</script>
