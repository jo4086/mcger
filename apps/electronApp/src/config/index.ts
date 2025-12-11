import { type AppConfig } from './types';

export const config = {
  runStatus: 'development',
  enableDevTools: true,
  apiBaseUrl: 'http://localhost:5173',
} satisfies AppConfig;
