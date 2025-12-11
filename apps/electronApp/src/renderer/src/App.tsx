import './index.css';
import { Layout } from './layouts';
import { AppRoutes } from './router';

function App() {
  return (
    <div className="flex w-full flex-col bg_default h-lvh text-white">
      <Layout>
        <AppRoutes />
      </Layout>
    </div>
  );
}

export default App;
