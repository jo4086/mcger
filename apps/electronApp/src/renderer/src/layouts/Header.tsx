import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <>
      <div className="flex justify-center items-center bg-brand-light py-2">
        <Link className="px-4 py-2 border border-white rounded-md min-w-30 text-center" to={{ pathname: '' }}>
          로고
        </Link>
      </div>
    </>
  );
};

export default Header;
