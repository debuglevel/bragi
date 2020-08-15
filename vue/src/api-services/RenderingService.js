const RESOURCE_NAME = "/renderings";

export default {
  getHtmlUrl() {
    return `${process.env.VUE_APP_API_ENDPOINT}${RESOURCE_NAME}/html`;
  },

  getOdtUrl() {
    return `${process.env.VUE_APP_API_ENDPOINT}${RESOURCE_NAME}/odt`;
  },
};
