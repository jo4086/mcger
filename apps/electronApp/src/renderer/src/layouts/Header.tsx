import { Link } from 'react-router-dom';

const Header = () => {
  return (
    <>
      <div className="bg-brand-light">
        <Link className="" to={{ pathname: '' }}>
          로고에요
        </Link>
      </div>
    </>
  );
};

export default Header;
