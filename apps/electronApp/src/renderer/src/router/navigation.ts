import type { NavigateFunction } from 'react-router-dom';

let navigateResolver!: (_navigate: NavigateFunction) => void;

export const navigatePromise = new Promise<NavigateFunction>((resolve) => {
  navigateResolver = resolve;
});

export function setNavigate(navigate: NavigateFunction) {
  navigateResolver(navigate);
}
