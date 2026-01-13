export {};

declare global {
  interface Window {
    api: {
      invoke: <K extends keyof IPCRequestMap>(channel: K, ...args: IPCRequestMap[K]['args']) => Promise<IPCRequestMap[K]['return']>;
      on: <K extends keyof IPCEventMap>(channel: K, listener: (...args: IPCEventMap[K]) => void) => () => void;
    };
  }
}
