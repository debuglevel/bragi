export default {
  /**
   * Gets the picture URL from an absolute URL.
   * @param {*} url
   * @param {*} opts Object with maxHeight and/or maxWidth set to resize the picture; original picture if omitted.
   */
  getPictureEndpointUrl(url, opts) {
    // gets /characters/<UUID>/picture and transforms it to http://<HOST>/places/<UUID>/picture
    if (!opts["maxWidth"] && !opts["maxHeight"]) {
      return `${process.env.VUE_APP_API_ENDPOINT}${url}`;
    } else {
      let maxWidth = opts["maxWidth"];
      let maxHeight = opts["maxHeight"];
      return `${process.env.VUE_APP_API_ENDPOINT}${url}?maxWidth=${maxWidth}&maxHeight=${maxHeight}`;
    }
  },

  /**
   * Gets the endpoint URL for an absolute URL; return the value for data URLs.
   * @param {*} pictureUrl an absolute URL (without protocol) or data URL
   * @param {*} opts Object with maxHeight and/or maxWidth set to resize the picture; original picture if omitted; ignored on data URLs.
   */
  getPictureUrl(pictureUrl, maxWidth, maxHeight) {
    if (pictureUrl.startsWith("data:image")) {
      // seems to be a data URL, return value
      return pictureUrl;
    } else if (pictureUrl.startsWith("/")) {
      // seems to be an absolute URL, prepend API endpoint URL
      return this.getPictureEndpointUrl(pictureUrl, {
        maxWidth: maxWidth,
        maxHeight: maxHeight,
      });
    } else {
      // no idea, just return value
      return pictureUrl;
    }
  },
};
