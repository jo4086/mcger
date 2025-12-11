"use strict";
const electron = require("electron");
function invoke(channel, ...args) {
  return electron.ipcRenderer.invoke(channel, ...args);
}
function on(channel, listener) {
  const handler = (_, ...args) => {
    listener(...args);
  };
  electron.ipcRenderer.on(channel, handler);
  return () => electron.ipcRenderer.removeListener(channel, handler);
}
electron.contextBridge.exposeInMainWorld("electronApi", { invoke, on });
