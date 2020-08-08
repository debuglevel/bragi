import rcolor from "rcolor";

export default {
  generateRandomHexColor(seed) {
    //console.log("Seed: " + seed);
    let hash = Math.abs(this.hashCode(seed));
    //console.log("Hash:" + hash);
    let pseudoRandom = hash / 2147483647;
    //console.log("Pseudorandom: " + pseudoRandom);

    const goldenRatio = 0.618;

    let color = rcolor({
      hue: (pseudoRandom + goldenRatio) % 1,
      saturation: 0.5,
      value: 0.95,
    });

    return color;
  },

  hashCode(string) {
    var hash = 0,
      i,
      chr;

    for (i = 0; i < string.length; i++) {
      chr = string.charCodeAt(i);
      hash = (hash << 5) - hash + chr;
      hash |= 0; // Convert to 32bit integer
    }

    return hash;
  },
};
