import { resolve } from 'path';

import { app, BrowserWindow, ipcMain, Menu, session } from 'electron';

import { config } from '@/config';
import type { AppConfig } from '@/config/types';
import { bootstrapServers, readServers } from '@/serverStore';
import type { ServerInfo } from '@/types/server';

// function getServersPath() {
//   return resolve(app.getPath('userData'), 'servers.json');
// }
//
// function readServers(): ServerInfo[] {
//   const filePath = getServersPath();
//
//   if (!fs.existsSync(filePath)) {
//     // 최초 실행 시 기본값 생성
//     const initial: ServerInfo[] = [{ id: 'server-a', name: 'A 서버', host: '127.0.0.1', port: 8080 }];
//     fs.writeFileSync(filePath, JSON.stringify(initial, null, 2));
//     return initial;
//   }
//
//   return JSON.parse(fs.readFileSync(filePath, 'utf-8'));
// }

/* ------------------ CSP ------------------ */
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
        ],
      },
    });
  });
}

/* ------------------ Window ------------------ */
function createWindow() {
  const win = new BrowserWindow({
    width: 1200,
    height: 800,
    webPreferences: {
      preload: resolve(__dirname, '../../out/preload/index.cjs'),
      contextIsolation: true,
    },
    autoHideMenuBar: true,
  });

  Menu.setApplicationMenu(null);

  if (process.env.ELECTRON_RENDERER_URL) {
    win.loadURL(process.env.ELECTRON_RENDERER_URL);
  } else {
    win.loadFile(resolve(__dirname, '../renderer/index.html'));
  }

  if (!app.isPackaged) {
    const menu = Menu.buildFromTemplate([
      {
        label: 'View',
        submenu: [
          {
            label: 'Toggle DevTools',
            accelerator: 'Ctrl+Shift+I',
            role: 'toggleDevTools',
          },
        ],
      },
    ]);

    Menu.setApplicationMenu(menu);
  }
}

/* ------------------ IPC: App ------------------ */
ipcMain.handle('app:get-config', (): AppConfig => {
  return config;
});

/* ------------------ IPC: Server Runtime ------------------ */
// 서버 상태는 Main Process가 진실
const serverRuntimeMap: Record<string, boolean> = {};

ipcMain.handle('server:start', async (_e, serverId: string) => {
  console.log('[server:start]', serverId);
  serverRuntimeMap[serverId] = true;
  return true;
});

ipcMain.handle('server:stop', async (_e, serverId: string) => {
  console.log('[server:stop]', serverId);
  serverRuntimeMap[serverId] = false;
  return false;
});

ipcMain.handle('server:status', async (_e, serverId: string) => {
  return !!serverRuntimeMap[serverId];
});

ipcMain.handle('server:list', (): ServerInfo[] => {
  return readServers();
});

/* ------------------ App lifecycle ------------------ */
app
  .whenReady()
  .then(() => {
    setupCSP();
    bootstrapServers();
    createWindow();
  })
  .catch(console.error);
