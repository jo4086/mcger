import { useEffect } from 'react';

import { navigatePromise } from '../../router/navigation';
import { useServerListStore } from '../../store/useServerListStore';
import { useServerStore } from '../../store/useServerStore';

import { ServerCard } from './ServerCard';

import type { ServerInfo } from '@/types/server';

export function Home() {
  const { servers, loadServers } = useServerListStore();
  const setServer = useServerStore((s) => s.setServer);

  useEffect(() => {
    loadServers();
  }, []);

  const selectServer = async (server: ServerInfo) => {
    setServer({ id: server.id, name: server.name });

    const navigate = await navigatePromise;
    navigate('/main');
  };

  return (
    <div className="px-4 grid grid-cols-1 gap-4">
      {servers.map((server) => (
        <ServerCard key={server.id} server={server} onSelect={selectServer} />
      ))}
    </div>
  );

  return (
    <div>
      <h2>서버 선택</h2>
      {servers.map((server) => (
        <button key={server.id} onClick={() => selectServer(server)}>
          {server.name} ({server.host}:{server.port})
        </button>
      ))}
    </div>
  );
}
