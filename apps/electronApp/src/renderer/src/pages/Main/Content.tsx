import { Status, Users, Logs, Settings } from './tabs';
import type { TabKey } from './types';

type Props = {
  activeTab: TabKey;
  serverId: string;
};

export const Content = ({ activeTab, serverId }: Props) => {
  switch (activeTab) {
    case 'status':
      return <Status serverId={serverId} />;
    case 'users':
      return <Users serverId={serverId} />;
    case 'logs':
      return <Logs serverId={serverId} />;
    case 'settings':
      return <Settings serverId={serverId} />;
  }
};
