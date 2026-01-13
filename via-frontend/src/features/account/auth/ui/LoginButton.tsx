'use client';

import { useRouter } from 'next/navigation';

export function LoginButton() {
  const router = useRouter();

  const handleClick = () => {
    router.push('/login');
  };

  return (
    <button
      onClick={handleClick}
      className="bg-[#575bc7] text-white px-[14px] py-[8px] rounded-[4px] text-[15px] hover:bg-[#6b70d6] transition-colors w-[120px]"
    >
      로그인
    </button>
  );
}
