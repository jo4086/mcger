import { contextBridge, ipcRenderer } from 'electron';

import type { AppConfig } from '@/config/types';

/* ---------- 1) IPC 요청 타입 ---------- */
interface IPCRequestMap {
  'app:get-version': {
    args: [];
    return: string;
  };
  'app:get-config': {
    args: [];
    return: AppConfig;
  };
  'server:start': {
    args: [serverName: string];
    return: boolean;
  };
  'server:stop': {
    args: [serverName: string];
    return: boolean;
  };
  'server:status': {
    args: [serverName: string];
    return: boolean;
  };
}

/* ---------- 2) invoke ---------- */
function invoke<K extends keyof IPCRequestMap>(channel: K, ...args: IPCRequestMap[K]['args']): Promise<IPCRequestMap[K]['return']> {
  return ipcRenderer.invoke(channel, ...args);
}

/* ---------- 3) IPC 이벤트 타입 ---------- */
interface IPCEventMap {
  'server:started': [serverName: string];
  'server:stopped': [serverName: string];
}

/* ---------- 4) on ---------- */
function on<K extends keyof IPCEventMap>(channel: K, listener: (...args: IPCEventMap[K]) => void) {
  const handler = (_: Electron.IpcRendererEvent, ...args: IPCEventMap[K]) => {
    listener(...args);
  };

  ipcRenderer.on(channel, handler);
  return () => ipcRenderer.removeListener(channel, handler);
}

/* ---------- 5) expose ---------- */
contextBridge.exposeInMainWorld('api', {
  invoke,
  on,
});
