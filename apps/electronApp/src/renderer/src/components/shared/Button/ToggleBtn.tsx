import { cn } from '@/renderer/src/utils';

interface Props {
  onToggle?: () => void;
  className?: string;
  isRunning: boolean;
  disabled?: boolean;
}

export function ToggleBtn({ isRunning = false, className, disabled = false, onToggle }: Props) {
  const runningClass = isRunning ? 'bg-red-700' : 'bg-green-700';
  const disableClass = disabled ? 'cursor-wait' : 'cursor-pointer';
  const value = isRunning ? 'stop' : 'start';
  console.log(disabled);

  return (
    <button onClick={onToggle} className={cn(runningClass, disableClass, `${disabled ? 'brightness-90' : ''}`, `${className}`)} disabled={disabled}>
      {value}
    </button>
  );
}
