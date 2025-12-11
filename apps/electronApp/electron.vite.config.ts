import { resolve } from 'path';

import tailwindcss from '@tailwindcss/vite';
import react from '@vitejs/plugin-react';
import { defineConfig } from 'electron-vite';
import tsconfigPaths from 'vite-tsconfig-paths';

export default defineConfig({
  main: {
    plugins: [tsconfigPaths()],
    build: {
      outDir: 'dist/main',
      externalizeDeps: true,
    },
    resolve: {
      alias: [
        { find: '@', replacement: resolve(__dirname, 'src') },
        { find: '@main', replacement: resolve(__dirname, 'src/main') },
        { find: '@preload', replacement: resolve(__dirname, 'src/preload') },
        { find: '@config', replacement: resolve(__dirname, 'src/config') },
        { find: '@renderer', replacement: resolve(__dirname, 'src/renderer/src') },
        { find: '@types', replacement: resolve(__dirname, 'src/types') },
      ],
    },
  },
  preload: {
    plugins: [tsconfigPaths()],
    build: {
      externalizeDeps: true,
      lib: {
        entry: resolve('src/preload/index.ts'),
        formats: ['cjs'],
      },
    },
    resolve: {
      alias: [
        { find: '@', replacement: resolve(__dirname, 'src') },
        { find: '@main', replacement: resolve(__dirname, 'src/main') },
        { find: '@preload', replacement: resolve(__dirname, 'src/preload') },
        { find: '@config', replacement: resolve(__dirname, 'src/config') },
        { find: '@renderer', replacement: resolve(__dirname, 'src/renderer/src') },
        { find: '@types', replacement: resolve(__dirname, 'src/types') },
      ],
    },
  },
  renderer: {
    resolve: {
      alias: [
        { find: '@', replacement: resolve(__dirname, 'src') },
        { find: '@main', replacement: resolve(__dirname, 'src/main') },
        { find: '@preload', replacement: resolve(__dirname, 'src/preload') },
        { find: '@config', replacement: resolve(__dirname, 'src/config') },
        { find: '@renderer', replacement: resolve(__dirname, 'src/renderer/src') },
        { find: '@types', replacement: resolve(__dirname, 'src/types') },
      ],
    },
    plugins: [react(), tsconfigPaths(), tailwindcss()],
    build: {
      outDir: 'dist/renderer',
      rollupOptions: {
        input: resolve('src/renderer/index.html'),
      },
    },
  },
});
