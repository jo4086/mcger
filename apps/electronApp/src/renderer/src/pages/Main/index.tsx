import type React from 'react';

import { Button } from '../../components/shared';
import { TabItem } from '../../components/TabItem';
import { Tabs } from '../../components/Tabs';

export const Main = () => {
  const tabItems = [
    { tabId: 1, name: '상태' },
    { tabId: 2, name: '유저' },
    { tabId: 3, name: '로그' },
    { tabId: 4, name: '설정' },
  ];

  return (
    <div className="bg-sky-600 m-[0 auto] flex flex-col w-full justify-center">
      <Container className="flex px-3 py-1.5 justify-between">
        <Title className="bg-fuchsia-300 flex-1 flex justify-center items-center">서버명</Title>
        <div className="flex-5 flex gap-5 justify-end">
          <Button className="px-4 py-2 min-w-24 rounded-[8px]" disabled>
            시작
          </Button>
          <Button className="bg-red-600 px-4 py-2 min-w-24 rounded-[8px]">중지</Button>
        </div>
      </Container>

      <Tabs>
        {tabItems.map(({ tabId: id, name }) => {
          return (
            <TabItem key={id} tabId={id} className="w-30">
              {name}
            </TabItem>
          );
        })}
      </Tabs>
      <Content />
    </div>
  );
};

export default Main;

const Container = ({ children, className }: { children: React.ReactNode; className?: string }) => {
  return <div className={className}>{children}</div>;
};

const Title = ({ children, className }: { children?: React.ReactNode; className?: string }) => {
  return <div className={className}>{children}</div>;
};

const Content = () => {
  return (
    <>
      <span>콘텐츠부분</span>
    </>
  );
};
