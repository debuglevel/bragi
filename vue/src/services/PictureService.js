export default {
  /**
   * Gets the picture URL from an absolute URL.
   * @param {*} url
   * @param {*} opts Object with maxHeight and/or maxWidth set to resize the picture; original picture if omitted.
   */
  getPictureUrl(url, opts) {
    // gets /characters/<UUID>/picture and transforms it to http://<HOST>/places/<UUID>/picture
    if (!opts["maxWidth"] && !opts["maxHeight"]) {
      return `${process.env.VUE_APP_API_ENDPOINT}${url}`;
    } else {
      let maxWidth = opts["maxWidth"];
      let maxHeight = opts["maxHeight"];
      return `${process.env.VUE_APP_API_ENDPOINT}${url}?maxWidth=${maxWidth}&maxHeight=${maxHeight}`;
    }
  },
};
