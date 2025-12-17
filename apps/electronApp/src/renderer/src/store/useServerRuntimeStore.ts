import { create } from 'zustand';

type RuntimeState = {
  runningMap: Record<string, boolean>;
  pendingMap: Record<string, boolean>;
  toggleServer: (serverId: string) => Promise<void>;
};

const delay = (ms: number) => new Promise<void>((resolve) => setTimeout(resolve, ms));

export const useServerRuntimeStore = create<RuntimeState>((set, get) => ({
  runningMap: {},
  pendingMap: {},
  toggleServer: async (serverId) => {
    const { pendingMap, runningMap } = get();
    if (pendingMap[serverId]) return;

    try {
      set({
        pendingMap: { ...pendingMap, [serverId]: true },
      });

      await delay(300);

      set({
        runningMap: {
          ...runningMap,
          [serverId]: !runningMap[serverId],
        },
      });
    } catch (err) {
      console.error('err:', err);
    } finally {
      set({
        pendingMap: { ...get().pendingMap, [serverId]: false },
      });
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
