import { cookies } from 'next/headers';
import type { MemberInfo } from '../model';
import type { LoginResponse } from '@/features/account/auth';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080';

interface FetchResult<T> {
  data?: T;
  status: number;
  setCookies?: string[];
}

/**
 * Make a server-side API request and capture Set-Cookie headers
 */
async function fetchWithCookies<T>(
  endpoint: string,
  cookieHeader: string
): Promise<FetchResult<T>> {
  const response = await fetch(`${API_BASE_URL}${endpoint}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
      ...(cookieHeader && { Cookie: cookieHeader }),
    },
    cache: 'no-store',
  });

  const setCookies = response.headers.getSetCookie();

  if (!response.ok) {
    return { status: response.status, setCookies };
  }

  const contentType = response.headers.get('content-type');
  if (contentType?.includes('application/json')) {
    const data = await response.json();
    return { data, status: response.status, setCookies };
  }

  return { status: response.status, setCookies };
}

/**
 * Call refresh token API and return new cookies if successful
 */
async function refreshTokenRequest(
  cookieHeader: string
): Promise<{ success: boolean; newCookieHeader?: string; status?: string }> {
  const response = await fetch(`${API_BASE_URL}/auth/token/refresh`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      ...(cookieHeader && { Cookie: cookieHeader }),
    },
    cache: 'no-store',
  });

  if (!response.ok) {
    return { success: false };
  }

  // Extract Set-Cookie headers from response
  const setCookies = response.headers.getSetCookie();

  // Parse the new cookies into a cookie header string for subsequent requests
  const newCookies: string[] = [];
  for (const cookie of setCookies) {
    // Extract cookie name and value (before the first semicolon)
    const cookiePart = cookie.split(';')[0];
    if (cookiePart) {
      newCookies.push(cookiePart);
    }
  }

  const contentType = response.headers.get('content-type');
  let loginResponse: LoginResponse | undefined;
  if (contentType?.includes('application/json')) {
    loginResponse = await response.json();
  }

  return {
    success: true,
    newCookieHeader: newCookies.join('; '),
    status: loginResponse?.status,
  };
}

/**
 * Build cookie header from cookie store
 */
function buildCookieHeader(accessToken?: string, refreshToken?: string): string {
  return [
    accessToken ? `access-token=${accessToken}` : '',
    refreshToken ? `refresh-token=${refreshToken}` : '',
  ]
    .filter(Boolean)
    .join('; ');
}

/**
 * Get current member information with automatic token refresh
 *
 * Flow:
 * 1. Try to fetch member info with current cookies
 * 2. If 401 (unauthorized), attempt token refresh
 * 3. If refresh succeeds with ALLOWED status, retry member fetch with new cookies
 * 4. Return member info or null
 *
 * @returns MemberInfo if authenticated, null if not authenticated or error
 */
export async function getCurrentMember(): Promise<MemberInfo | null> {
  const cookieStore = await cookies();
  const accessToken = cookieStore.get('access-token')?.value;
  const refreshToken = cookieStore.get('refresh-token')?.value;

  // No tokens at all - user is not logged in
  if (!accessToken && !refreshToken) {
    return null;
  }

  const cookieHeader = buildCookieHeader(accessToken, refreshToken);

  // First attempt: try to get member info
  const firstAttempt = await fetchWithCookies<MemberInfo>('/member/me', cookieHeader);

  if (firstAttempt.status === 200 && firstAttempt.data) {
    return firstAttempt.data;
  }

  // If unauthorized (401), try to refresh the token
  if (firstAttempt.status === 401 && refreshToken) {
    const refreshResult = await refreshTokenRequest(cookieHeader);

    // Only proceed if refresh was successful and user is ALLOWED
    if (refreshResult.success && refreshResult.status === 'ALLOWED' && refreshResult.newCookieHeader) {
      // Retry with new cookies
      const secondAttempt = await fetchWithCookies<MemberInfo>(
        '/member/me',
        refreshResult.newCookieHeader
      );

      if (secondAttempt.status === 200 && secondAttempt.data) {
        return secondAttempt.data;
      }
    }
  }

  // All attempts failed - user is not authenticated
  return null;
}
