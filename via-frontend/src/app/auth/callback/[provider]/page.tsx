'use client';

import { useEffect, useState } from 'react';
import { useRouter, useSearchParams } from 'next/navigation';
import { handleOAuthCallback, LOGIN_STATUS_REDIRECT } from '@/features/account/auth';

export default function OAuthCallbackPage() {
  const router = useRouter();
  const searchParams = useSearchParams();
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const processCallback = async () => {
      const code = searchParams.get('code');
      const state = searchParams.get('state');
      const errorParam = searchParams.get('error');

      if (errorParam) {
        setError(`OAuth provider error: ${errorParam}`);
        return;
      }

      if (!code || !state) {
        setError('Missing required OAuth parameters');
        return;
      }

      try {
        const response = await handleOAuthCallback({ code, state });

        // Redirect based on login status
        const redirectPath =
          response.redirectPath || LOGIN_STATUS_REDIRECT[response.status] || '/';
        router.replace(redirectPath);
      } catch (err) {
        console.error('OAuth callback error:', err);
        setError(err instanceof Error ? err.message : 'Login failed. Please try again.');
      }
    };

    processCallback();
  }, [searchParams, router]);

  if (error) {
    return (
      <div className="min-h-[calc(100vh-56px)] bg-[#191a23] flex items-center justify-center">
        <div className="w-full max-w-md p-8 text-center">
          <div className="text-red-500 mb-4">
            <svg
              className="w-16 h-16 mx-auto"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth={2}
                d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
              />
            </svg>
          </div>
          <h1 className="text-white text-xl font-semibold mb-2">로그인 실패</h1>
          <p className="text-[#858699] mb-6">{error}</p>
          <button
            onClick={() => router.push('/login')}
            className="px-6 py-2 bg-[#575bc7] text-white rounded-lg hover:bg-[#4a4eb3] transition-colors"
          >
            다시 시도
          </button>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-[calc(100vh-56px)] bg-[#191a23] flex items-center justify-center">
      <div className="w-full max-w-md p-8 text-center">
        <div className="animate-spin rounded-full h-12 w-12 border-b-2 border-[#575bc7] mx-auto mb-4" />
        <p className="text-[#858699]">로그인 처리 중...</p>
      </div>
    </div>
  );
}
