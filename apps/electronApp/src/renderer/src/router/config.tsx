import type { RouteObject } from 'react-router-dom';

import { Main, Home } from '../pages';

const appRoutes: RouteObject[] = [
  {
    path: 'main',
    element: <Main />,
  },
  {
    path: '',
    element: <Home />,
  },
];

export default appRoutes;
