/** @type {import('tailwindcss').Config} */
import plugin from 'tailwindcss/plugin';

export default {
  content: ['./src/renderer/index.html', './src/renderer/src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        brand: {
          dark: 'oklch(0.20 0.05 250)',
          DEFAULT: 'oklch(0.24 0.05 250)',
          light: 'oklch(0.28 0.05 250)',
        },
      },
    },
  },
  plugins: [
    plugin(function ({ addComponents }) {
      addComponents({
        '.my-container': {
          width: '100%',
          border: '1px solid',
          borderRadius: '0.375rem', // md
        },
      });
    }),
  ],
};
