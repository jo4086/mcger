type Props = {
  serverId: string; // 고정값: 반드시 전달
};

export const Status = ({ serverId }: Props) => {
  return <div>서버 {serverId} 상태</div>;
};
