import { contextBridge, ipcRenderer } from 'electron';

import type { AppConfig } from '@/config/types';

// 1) IPC 채널별 타입 정의
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
}

// 2) 타입 안전한 invoke
function invoke<K extends keyof IPCRequestMap>(channel: K, ...args: IPCRequestMap[K]['args']): Promise<IPCRequestMap[K]['return']> {
  return ipcRenderer.invoke(channel, ...args);
}

interface IPCEventMap {
  'server:started': [serverName: string];
  'server:stopped': [serverName: string];
}

// 3) **no-any + 타입가드 기반 on**
function on<K extends keyof IPCEventMap>(channel: K, listener: (...args: IPCEventMap[K]) => void) {
  const handler = (_: Electron.IpcRendererEvent, ...args: IPCEventMap[K]) => {
    listener(...args);
  };

  ipcRenderer.on(channel, handler);

  return () => ipcRenderer.removeListener(channel, handler);
}

// 3) **no-any + 타입가드 기반 on**
// function on<K extends keyof IPCRequestMap>(channel: K, listener: (...args: IPCRequestMap[K]['args']) => void) {
//   const handler = (_event: Electron.IpcRendererEvent, ...raw: unknown[]) => {
//     // 타입가드: raw를 정확한 args 타입으로 변환
//     const args = raw as IPCRequestMap[K]['args'];
//     listener(...args);
//   };
//
//   ipcRenderer.on(channel, handler);
//
//   return () => {
//     ipcRenderer.removeListener(channel, handler);
//   };
// }

// 4) renderer에 노출
contextBridge.exposeInMainWorld('electronApi', { invoke, on });

