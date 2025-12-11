import './index.css';
import { Layout } from './layouts';
import { AppRoutes } from './router';

function App() {
  return (
    <>
      <Layout>
        <AppRoutes />
      </Layout>
    </>
  );
}

export default App;
