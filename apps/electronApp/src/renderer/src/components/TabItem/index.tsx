import type React from 'react';

import { cn } from '../../utils';

export interface TabItemProps {
  children?: React.ReactNode;
  className?: string;
  selected?: boolean;

  // 내부 전용 타입
  onSelect?: () => void;
  tabId?: string | number;
}

export function TabItem({ children, className, selected = false, onSelect }: TabItemProps) {
  // const bgClass = selected ? 'bg-gray-400' : 'bg-gray-600';
  // const baseClass = 'rounded-t-[8px] py-1.5 px-3 border-none min-w-24 cursor-pointer flex';
  // const hoverClass = selected ? '' : 'hover:bg-gray-500';

  return (
    <div
      onClick={onSelect}
      className={cn(
        'rounded-t-[8px] py-1.5 px-3 border-none min-w-24 cursor-pointer flex justify-center items-center',
        selected ? 'bg-gray-400' : 'bg-gray-600',
        !selected && 'hover:bg-gray-500',
        className, // 👈 외부가 항상 최종 승자
      )}
    >
      {children}
    </div>
  );
}
