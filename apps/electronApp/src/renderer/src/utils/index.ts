import { twMerge } from 'tailwind-merge';

function toTwSize(prefix: 'w' | 'h', value?: number | string | null) {
  if (value == null) return '';

  // 1️⃣ number → w-6
  if (typeof value === 'number') {
    return `${prefix}-${value}`;
  }

  // 2️⃣ '8px', '12rem', '50%' → w-[8px]
  if (value.endsWith('px') || value.endsWith('rem') || value.endsWith('%') || value.endsWith('vh') || value.endsWith('vw')) {
    return `${prefix}-[${value}]`;
  }

  // 3️⃣ 'auto', 'fit-content' 등 → w-auto
  return `${prefix}-${value}`;
}

export function sizeToTw(size?: { w?: number | string | null; h?: number | string | null }) {
  if (!size) return '';

  return [toTwSize('w', size.w), toTwSize('h', size.h)].filter(Boolean).join(' ');
}

export function cn(...classes: (string | undefined | null | false)[]) {
  return twMerge(classes.filter(Boolean).join(' '));
}
