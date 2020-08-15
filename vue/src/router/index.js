import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/",
    name: "Home",
    component: Home,
    meta: {
      title: "Home",
    },
  },
  {
    path: "/about",
    name: "About",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/About.vue"),
    meta: {
      title: "About",
    },
  },
  {
    path: "/sampledata",
    name: "Sampledata",
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "about" */ "../views/Sampledata.vue"),
    meta: {
      title: "Sample Data",
    },
  },
  {
    path: "/chapters",
    name: "Chapters",
    // route level code-splitting
    // this generates a separate chunk (chapters.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "chapters" */ "../views/Chapters.vue"),
    meta: {
      title: "Chapters",
    },
  },
  // dynamic segments start with a colon
  {
    path: "/chapters/:id",
    //component: User
    component: () =>
      import(/* webpackChunkName: "chapter" */ "../views/Chapter.vue"),
    props: true,
    meta: {
      title: "Chapter",
    },
  },
  {
    path: "/characters",
    name: "Characters",
    // route level code-splitting
    // this generates a separate chunk (characters.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "characters" */ "../views/Characters.vue"),
    meta: {
      title: "Characters",
    },
  },
  // dynamic segments start with a colon
  {
    path: "/characters/:id",
    component: () =>
      import(/* webpackChunkName: "chapter" */ "../views/Character.vue"),
    props: true,
    meta: {
      title: "Character",
    },
  },
  {
    path: "/places",
    name: "Places",
    // route level code-splitting
    // this generates a separate chunk (places.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () =>
      import(/* webpackChunkName: "places" */ "../views/Places.vue"),
    meta: {
      title: "Places",
    },
  },
  // dynamic segments start with a colon
  {
    path: "/places/:id",
    component: () =>
      import(/* webpackChunkName: "places" */ "../views/Place.vue"),
    props: true,
    meta: {
      title: "Place",
    },
  },
];

const router = new VueRouter({
  routes,
});

export default router;
