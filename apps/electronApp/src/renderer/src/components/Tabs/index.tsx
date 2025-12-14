import type React from 'react';
import { useState, Children, type ReactElement, cloneElement } from 'react';

import { type TabItemProps } from '../TabItem';

interface TabsProps {
  children: React.ReactNode;
  className?: string;
}

export function Tabs({ children, className }: TabsProps) {
  const [selectedId, setSelectedId] = useState<number | null>(1);

  return (
    <div className={`bg-gray-600 flex gap-0 rounded-tl-[8px] rounded-tr-[8px] ${className}`}>
      {Children.map(children, (child, index) => {
        if (!child) return null;

        const element = child as ReactElement<TabItemProps & { tabId?: number }>;

        return cloneElement(element, {
          selected: element.props.tabId === selectedId,
          onSelect: () => setSelectedId(element.props.tabId ?? index),
        });
      })}
    </div>
  );
}
