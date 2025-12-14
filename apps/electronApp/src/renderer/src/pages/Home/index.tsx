import { useNavigate } from 'react-router-dom';

export function Home() {
  const navigate = useNavigate();

  return (
    <div className="flex flex-col items-center">
      <p>Home</p>
      <button className="bg-orange-400 flex w-fit px-2 py-0.5 cursor-pointer" onClick={() => navigate('/main')}>
        main 이동
      </button>
    </div>
  );
}
