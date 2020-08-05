import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
  },
  {
    path: "/chapters",
    name: "Chapters",
    // route level code-splitting
    // this generates a separate chunk (chapters.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "chapters" */ "../views/Chapters.vue"),
  },
  // {
  //   path: "/characters",
  //   name: "Characters",
  //   // route level code-splitting
  //   // this generates a separate chunk (characters.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "characters" */ "../views/Characters.vue"),
  // },
  // {
  //   path: "/locations",
  //   name: "Locations",
  //   // route level code-splitting
  //   // this generates a separate chunk (locations.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () =>
  //     import(/* webpackChunkName: "locations" */ "../views/Locations.vue"),
  // },
];

const router = new VueRouter({
  routes,
});

export default router;
