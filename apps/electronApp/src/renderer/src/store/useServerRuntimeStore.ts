import { create } from 'zustand';

type RuntimeState = {
  runningMap: Record<string, boolean>;
  pendingMap: Record<string, boolean>;
  startedAtMap: Record<string, number | null>;
  toggleServer: (_serverId: string) => Promise<void>;
};

const delay = (ms: number) => new Promise<void>((resolve) => setTimeout(resolve, ms));

export const useServerRuntimeStore = create<RuntimeState>((set, get) => ({
  runningMap: {},
  pendingMap: {},
  startedAtMap: {},
  toggleServer: async (serverId) => {
    const { pendingMap, runningMap: _runningMap } = get();
    if (pendingMap[serverId]) return;

    try {
      set({
        pendingMap: { ...pendingMap, [serverId]: true },
      });

      await delay(300);

      set((state) => {
        const isRunning = !!state.runningMap[serverId];

        return {
          runningMap: {
            ...state.runningMap,
            [serverId]: !isRunning,
          },
          startedAtMap: {
            ...state.startedAtMap,
            [serverId]: isRunning ? null : Date.now(),
          },
        };
      });
    } catch (err) {
      console.error('err:', err);
    } finally {
      set((state) => ({
        pendingMap: { ...state.pendingMap, [serverId]: false },
      }));
    }
  },
}));

// set((state) => ({
//   pendingMap: {
//     ...state.pendingMap,
//     [serverId]: true,
//   },
// }));
//
// // 예시: IPC / API 자리
// await delay(300);
//
// set((state) => ({
//   runningMap: {
//     ...state.runningMap,
//     [serverId]: !state.runningMap[serverId],
//   },
// }));
