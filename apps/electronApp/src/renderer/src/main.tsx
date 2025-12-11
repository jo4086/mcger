import React from 'react';
import { createRoot } from 'react-dom/client';
import { HashRouter } from 'react-router-dom';

// console.log('ping result:', window.api.ping());

import App from './App';

const root = document.getElementById('root');

if (!root) {
  throw new Error('root element not found');
}

createRoot(root).render(
  <HashRouter>
    <React.StrictMode>
      <App />
    </React.StrictMode>
  </HashRouter>,
);

//
// import { StrictMode } from 'react';
// import { createRoot } from 'react-dom/client';
//
// import './index.css';
// import App from './App.tsx';
//
// createRoot(document.getElementById('root')!).render(
//   <StrictMode>
//     <App />
//   </StrictMode>,
// );

// function Renderer() {
//   return (
//     <div>
//       <h1>렌더러</h1>
//     </div>
//   );
// }
//
// export default Renderer;
