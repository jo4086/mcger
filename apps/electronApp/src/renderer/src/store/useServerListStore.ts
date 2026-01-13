import { create } from 'zustand';

import type { ServerInfo } from '@/types/server';

type State = {
  servers: ServerInfo[];
  loadServers: () => Promise<void>;
};

export const useServerListStore = create<State>((set) => ({
  servers: [],
  loadServers: async () => {
    const servers = await window.api.invoke('server:list');
    set({ servers });
  },
}));
