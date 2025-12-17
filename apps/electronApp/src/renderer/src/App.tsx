import { useEffect } from 'react';

import './index.css';
import { Layout } from './layouts';
import { AppRoutes } from './router';
import { useAppConfigStore } from './store/useAppConfigStore';

function App() {
  const loadConfig = useAppConfigStore((s) => s.load);
  const config = useAppConfigStore((s) => s.config);

  useEffect(() => {
    loadConfig();
  }, []);

  // 1️⃣ config 아직 안 왔으면 아무것도 안 그림
  if (!config) {
    return <div>Loading...</div>;
  }

  return (
    <div className="flex w-full flex-col bg_default h-lvh text-white">
      <Layout>
        <AppRoutes />
      </Layout>
    </div>
  );
}

export default App;
