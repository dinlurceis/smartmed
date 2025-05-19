/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./index.html", "./src/**/*.{js,jsx}"],
  theme: {
    extend: {
      colors: {
        primary: "#0F766E",
        secondary: "#0EA5E9",
        accent: "#8B5CF6",
      },
    },
  },
  plugins: [],
};
