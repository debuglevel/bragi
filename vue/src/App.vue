<template>
  <v-app>
    <!--<v-system-bar app></v-system-bar> -->

    <v-navigation-drawer app expand-on-hover>
      <v-list dense nav>
        <v-list-item v-for="item in navigationItems" :key="item.title" :to="item.link" link>
          <v-list-item-icon>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-item-icon>

          <v-list-item-content>
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <!--
    <v-app-bar app>
      <v-toolbar-title>{{ title }}</v-toolbar-title>
      <v-spacer></v-spacer>
    </v-app-bar>
    -->

    <!-- Sizes your content based upon application components -->
    <v-main>
      <!-- Provides the application the proper gutter -->
      <v-container fluid>
        <!-- If using vue-router -->
        <router-view />
      </v-container>
    </v-main>

    <v-footer app>Built with ❤ by debuglevel.</v-footer>
  </v-app>
</template>

<script>
export default {
  name: "App",

  components: {},

  watch: {
    $route(to) {
      this.title = "Bragi: " + to.meta.title || "Bragi";
      document.title = this.title;
    }
  },

  data: () => ({
    title: "Bragi",
    navigationItems: [
      { title: "Home", icon: "mdi-home", link: "/" },
      {
        title: "Chapters",
        icon: "mdi-text-box-outline",
        link: "/chapters"
      },
      { title: "Characters", icon: "mdi-account", link: "/characters" },
      {
        title: "Places",
        icon: "mdi-map-marker",
        link: "/places"
      },
      { title: "About", icon: "mdi-information", link: "/about" }
    ]
  })
};
</script>
