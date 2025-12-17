import type React from 'react';
import { useState } from 'react';

import { ToggleBtn } from '../../components/shared';
import { TabItem } from '../../components/TabItem';
import { Tabs } from '../../components/Tabs';
import { useServerRuntimeStore } from '../../store/useServerRuntimeStore';
import { useServerStore } from '../../store/useServerStore';

import { Content } from './Content';
import type { TabKey } from './types';

const tabItems: { key: TabKey; label: string }[] = [
  { key: 'status', label: '상태' },
  { key: 'users', label: '유저' },
  { key: 'logs', label: '로그' },
  { key: 'settings', label: '설정' },
];

export const Main = () => {
  const server = useServerStore((s) => s.currentServer);
  const [isRunning, setIsRunning] = useState<boolean>(false);
  const [isPending, setIsPending] = useState<boolean>(false);
  const [activeTab, _setActiveTab] = useState<TabKey>('status');

  const handleToggle = async () => {
    if (isPending) return;
    try {
      setIsPending(true);
      await new Promise((r) => setTimeout(r, 300));
      setIsRunning((prev) => !prev);
    } catch (err) {
      console.error('err:', err);
    } finally {
      setIsPending(false);
    }
  };

  const { toggleServer: _toggleServer, runningMap: _runningMap, pendingMap: _pendingMap } = useServerRuntimeStore();

  if (!server) return null;

  return (
    <div className="bg-sky-600 m-[0 auto] flex flex-col w-full justify-center">
      <Container className="flex px-3 py-1.5 justify-between">
        <Title className="bg-fuchsia-300 flex-1 flex justify-center items-center">{server.name}</Title>
        <div className="flex-5 flex gap-5 justify-end">
          <ToggleBtn isRunning={isRunning} onToggle={handleToggle} className="px-5 py-2 rounded-[8px]  border border-black w-24 text-xl" disabled={isPending} />
        </div>
      </Container>

      <Tabs>
        {tabItems.map(({ key, label }) => {
          return (
            <TabItem key={key} tabId={key} className="w-30">
              {label}
            </TabItem>
          );
        })}
      </Tabs>
      <Content activeTab={activeTab} serverId={server.id} />
    </div>
  );
};

export default Main;

const Container = ({ children, className }: { children: React.ReactNode; className?: string }) => {
  return <div className={className}>{children}</div>;
};

const Title = ({ children, className }: { children?: React.ReactNode; className?: string }) => {
  return <div className={className}>{children}</div>;
};
