import { useEffect, useMemo, useState } from 'react';

import { ToggleBtn } from '@/renderer/src/components/shared';
import { useServerRuntimeStore } from '@/renderer/src/store/useServerRuntimeStore';
import type { ServerInfo } from '@/types/server';

interface Props {
  server: ServerInfo;
  onSelect: (_server: ServerInfo) => void;
}

export function ServerCard({ server, onSelect }: Props) {
  const { runningMap, pendingMap, startedAtMap, toggleServer } = useServerRuntimeStore();
  const isRunning = !!runningMap[server.id];
  const isPending = !!pendingMap[server.id];
  const startedAt = startedAtMap[server.id];
  const [now, setNow] = useState(() => Date.now());

  useEffect(() => {
    if (!isRunning || !startedAt) return;
    const intervalId = window.setInterval(() => setNow(Date.now()), 1000);
    return () => window.clearInterval(intervalId);
  }, [isRunning, startedAt]);

  const runtimeLabel = useMemo(() => {
    const runtimeMs = isRunning && startedAt ? Math.max(0, now - startedAt) : 0;
    const totalSeconds = Math.floor(runtimeMs / 1000);
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    const seconds = totalSeconds % 60;

    const pad = (value: number) => String(value).padStart(2, '0');
    return `${pad(hours)}:${pad(minutes)}:${pad(seconds)}`;
  }, [isRunning, startedAt, now]);

  return (
    <div className="border rounded-lg p-4 flex flex-col gap-2">
      <div className="text-lg font-bold">{server.name}</div>
      <div className="text-sm text-gray-500">
        {server.host}:{server.port}
      </div>
      <div className="text-sm text-gray-600">상태: {isRunning ? 'running' : 'stopped'}</div>
      <div className="text-sm text-gray-600">가동 시간: {runtimeLabel}</div>

      <div className="mt-2 flex items-center gap-2">
        <button className="px-3 py-1 rounded bg-blue-500 text-white text-lg border-white border" onClick={() => onSelect(server)}>
          선택
        </button>
        <ToggleBtn
          isRunning={isRunning}
          onToggle={() => toggleServer(server.id)}
          className="px-5 py-1 rounded border border-white w-20 h-full text-white text-lg border-border"
          disabled={isPending}
        />
      </div>
    </div>
  );
}
