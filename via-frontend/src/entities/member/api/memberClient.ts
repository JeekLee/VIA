import { apiClient, ApiClientError } from '@/shared/api';
import { refreshToken } from '@/features/account/auth';
import type { MemberInfo } from '../model';

/**
 * Fetch current member info from API
 */
async function fetchMemberInfo(): Promise<MemberInfo> {
  return apiClient.get<MemberInfo>('/member/me');
}

/**
 * Get current member information with automatic token refresh (Client-side)
 *
 * Flow:
 * 1. Try to fetch member info
 * 2. If 401 (unauthorized), attempt token refresh
 * 3. If refresh succeeds with ALLOWED status, retry member fetch
 * 4. Return member info or null
 */
export async function fetchCurrentMember(): Promise<MemberInfo | null> {
  try {
    return await fetchMemberInfo();
  } catch (error) {
    // If unauthorized, try to refresh token
    if (error instanceof ApiClientError && error.status === 401) {
      try {
        const response = await refreshToken();

        // Only retry if user status is ALLOWED
        if (response.status === 'ALLOWED') {
          return await fetchMemberInfo();
        }

        // User has pending requirements (terms, profile, etc.)
        return null;
      } catch {
        // Refresh failed - user needs to re-login
        return null;
      }
    }

    // Other errors
    console.error('Failed to fetch member info:', error);
    return null;
  }
}
