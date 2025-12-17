import type { ServerInfo } from '@/types/server';

interface Props {
  server: ServerInfo;
  onSelect: (_server: ServerInfo) => void;
}

export function ServerCard({ server, onSelect }: Props) {
  return (
    <div className="border rounded-lg p-4 flex flex-col gap-2">
      <div className="text-lg font-bold">{server.name}</div>
      <div className="text-sm text-gray-500">
        {server.host}:{server.port}
      </div>

      <button className="mt-2 px-3 py-1 rounded bg-blue-500 text-white" onClick={() => onSelect(server)}>
        선택
      </button>
    </div>
  );
}
