import Axios from "axios";

const RESOURCE_NAME = "/sampledata";

export default {
  getAll() {
    return Axios.get(RESOURCE_NAME);
  },

  populateGot() {
    return Axios.get(`${RESOURCE_NAME}/got`);
  },

  populateBook2Graph() {
    return Axios.get(`${RESOURCE_NAME}/book2graph`);
  },
};
