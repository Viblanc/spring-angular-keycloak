/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,ts}"],
  theme: {
    extend: {
      keyframes: {
        "slide-in": {
          "0%": { transform: "translateY(10%)", opacity: 0 },
          "100%": { transform: "translateY(0)", opacity: 1 },
        },
        "slide-out": {
          "0%": { transform: "translateY(0%)", opacity: 1 },
          "100%": { transform: "translateY(10%)", opacity: 0 },
        },
      },
      animation: {
        "slide-in": "slide-in 0.3s ease-in",
        "slide-out": "slide-out 0.2s linear",
      },
    },
  },
  plugins: [],
};
