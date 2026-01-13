'use client';

import { useState } from 'react';
import { initiateSocialLogin } from '../lib/socialLogin';

export function KakaoLoginButton() {
  const [isLoading, setIsLoading] = useState(false);

  const handleClick = async () => {
    try {
      setIsLoading(true);
      await initiateSocialLogin({
        provider: 'KAKAO',
        redirectPath: '/',
      });
    } catch (error) {
      console.error('Kakao login failed:', error);
      setIsLoading(false);
    }
  };

  return (
    <button
      type="button"
      onClick={handleClick}
      disabled={isLoading}
      className="w-full flex items-center justify-center gap-3 px-4 py-3 bg-[#FEE500] text-[#000000D9] font-medium rounded-lg hover:bg-[#FDD800] transition-colors disabled:opacity-50 disabled:cursor-not-allowed"
    >
      <svg className="w-5 h-5" viewBox="0 0 24 24">
        <path
          fill="#000000"
          d="M12 3C6.477 3 2 6.463 2 10.691c0 2.722 1.8 5.108 4.5 6.439-.2.724-.724 2.621-.83 3.031-.13.506.186.499.391.363.16-.107 2.558-1.737 3.595-2.442.778.112 1.581.17 2.344.17 5.523 0 10-3.463 10-7.691S17.523 3 12 3z"
        />
      </svg>
      {isLoading ? '로그인 중...' : '카카오로 계속하기'}
    </button>
  );
}
