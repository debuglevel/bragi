import Axios from "axios";

const RESOURCE_NAME = "/places";

export default {
  getAll() {
    return Axios.get(RESOURCE_NAME);
  },

  get(id) {
    return Axios.get(`${RESOURCE_NAME}/${id}`);
  },

  create(data) {
    return Axios.post(RESOURCE_NAME, data);
  },

  update(id, data) {
    return Axios.put(`${RESOURCE_NAME}/${id}`, data);
  },

  delete(id) {
    return Axios.delete(`${RESOURCE_NAME}/${id}`);
  },

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
