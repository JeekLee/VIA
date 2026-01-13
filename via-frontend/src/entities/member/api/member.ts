import { serverApiClient } from '@/shared/api/server';
import type { MemberInfo } from '../model';

/**
 * Get current member information (Server-side only)
 * Uses cookies to authenticate the request
 */
export async function getMyMemberInfo(): Promise<MemberInfo> {
  return serverApiClient.get<MemberInfo>('/member/me');
}
