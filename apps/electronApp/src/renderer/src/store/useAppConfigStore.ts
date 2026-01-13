import { create } from 'zustand';

import type { AppConfig } from '@/config/types';

type State = {
  config: AppConfig | null;
  load: () => Promise<void>;
};

export const useAppConfigStore = create<State>((set) => ({
  config: null,
  load: async () => {
    const config = await window.api.invoke('app:get-config');
    set({ config });
  },
}));
