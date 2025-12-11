import type { RouteObject } from 'react-router-dom';

import { Main } from '../pages';

const appRoutes: RouteObject[] = [
  {
    path: '',
    element: <Main />,
  },
];

export default appRoutes;
