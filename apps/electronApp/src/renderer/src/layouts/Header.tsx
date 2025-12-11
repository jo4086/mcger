import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <>
      <header className="bg-[oklch(0.2_0.05_250/0.9)] text-black text-2xl flex">
        <Link to={{ pathname: '' }}>로고</Link>
        <input className="rounded-sm m-4 " />
      </header>
    </>
  );
};

export default Header;
