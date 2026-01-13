import React from 'react';

import Footer from './Footer';
import Header from './Header';

const Layout = (props: { children: React.ReactNode }) => {
  return (
    <div className='flex flex-col py-1 gap-1'>
      <Header />

      <main> {props.children} </main>

      <Footer />
    </div>
  );
};

export default Layout;
