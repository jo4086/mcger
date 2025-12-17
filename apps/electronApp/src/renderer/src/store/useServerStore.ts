import { create } from 'zustand';

export type ServerInfo = {
  id: string;
  name: string;
};

type ServerState = {
  currentServer: ServerInfo | null;
  setServer: (server: ServerInfo) => void;
  clearServer: () => void;
};

export const useServerStore = create<ServerState>((set) => ({
  currentServer: null,
  setServer: (server) => set({ currentServer: server }),
  clearServer: () => set({ currentServer: null }),
}));
