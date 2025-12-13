import { TabItem } from '../components/TabItem';
import { Tabs } from '../components/Tabs';

export const Main = () => {
  const tabItems = [{ tabId: 1 }, { tabId: 2 }, { tabId: 3 }, { tabId: 4 }];

  return (
    <>
      <div className="bg_second m-[0 auto] flex flex-col w-full justify-center">
        Main Page
        <Tabs>
          {tabItems.map(({ tabId }) => {
            return (
              <TabItem key={tabId} tabId={tabId} className="w-30">
                탭{tabId}
              </TabItem>
            );
          })}
        </Tabs>
      </div>
    </>
  );
};

export default Main;
