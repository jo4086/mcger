import { resolve } from 'path';

import { app, BrowserWindow, ipcMain, session } from 'electron';

import { config } from '@/config';
import type { AppConfig } from '@/config/types';

function setupCSP() {
  const isDev = !app.isPackaged;

  session.defaultSession.webRequest.onHeadersReceived((details, callback) => {
    callback({
      responseHeaders: {
        ...details.responseHeaders,
        'Content-Security-Policy': [
          isDev
            ? [
                "default-src 'self'",
                "script-src 'self' 'unsafe-eval' 'unsafe-inline' http://localhost:5173",
                "style-src 'self' 'unsafe-inline'",
                "connect-src 'self' ws://localhost:5173 http://localhost:5173",
                "img-src 'self' data:",
              ].join('; ')
            : ["default-src 'self'", "script-src 'self'", "style-src 'self' 'unsafe-inline'", "img-src 'self' data:"].join('; '),

          // isDev
          //   ? "default-src 'self' script-src 'self' 'unsafe-eval' style-src 'self' 'unsafe-inline' connect-src 'self' ws:;"
          //   : "default-src 'self' script-src 'self'; style-src 'self' 'unsafe-inline' img-src 'self' data:;",
        ],
      },
    });
  });
}

function createWindow() {
  const win = new BrowserWindow({
    width: 1200,
    height: 800,
    webPreferences: {
      preload: resolve(__dirname, '../../out/preload/index.cjs'), // 🔥 핵심
      contextIsolation: true,
    },
  });

  if (process.env.ELECTRON_RENDERER_URL) {
    win.loadURL(process.env.ELECTRON_RENDERER_URL);
  } else {
    win.loadFile(resolve(__dirname, '../renderer/index.html'));
  }
}

ipcMain.handle('app:get-config', (): AppConfig => {
  return config;
});

//
// ipcMain.handle('ping', () => {
//   return 'pong';
// });

app
  .whenReady()
  .then(() => {
    setupCSP();
    createWindow();
  })
  .catch(console.error);
