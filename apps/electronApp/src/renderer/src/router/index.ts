import { useEffect } from 'react';
import { useNavigate, useRoutes } from 'react-router-dom';

import appRoutes from './config';
import { setNavigate } from './navigation';

export function AppRoutes() {
  const element = useRoutes(appRoutes);
  const navigate = useNavigate();

  useEffect(() => {
    setNavigate(navigate);
  }, [navigate]);

  return element;
}
