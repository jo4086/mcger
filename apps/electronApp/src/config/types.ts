export type RunStatus = 'development' | 'staging' | 'production';

export interface AppConfig {
  runStatus: RunStatus;
  enableDevTools: boolean;
  apiBaseUrl: string;
}
