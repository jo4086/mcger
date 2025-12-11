import { defineConfig } from 'tsup';

export default defineConfig({
  entry: ['src/index.ts'],
  format: ['cjs', 'esm'],
  outDir: 'dist',
  clean: true,
  sourcemap: true,
  platform: 'node',
  target: 'node20',

  skipNodeModulesBundle: true,
  shims: false,
  dts: true,
});
