import type React from 'react';

import { cn } from '@/renderer/src/utils';

interface ButtonProps {
  children: React.ReactNode;
  onClick?: () => void;
  className?: string;
  disabled?: boolean;
  toggle?: boolean;
}

export function Button({ children, className, disabled = false, onClick }: ButtonProps) {
  return (
    <button className={cn(disabled ? 'cursor-default bg-green-900' : 'cursor-pointer bg-green-600', `${className}`)} disabled={disabled} onClick={onClick}>
      {children}
    </button>
  );
}
