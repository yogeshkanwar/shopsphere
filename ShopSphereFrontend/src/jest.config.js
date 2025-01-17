export const transform = {
  "^.+\\.[tj]sx?$": "babel-jest",
};

// If you want to export the whole config
const config = {
  transform,
  // other Jest config options
};

export default config;

  