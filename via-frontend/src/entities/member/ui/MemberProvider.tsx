'use client';

import { useEffect } from 'react';
import { useMemberStore } from '../model/store';
import { fetchCurrentMember } from '../api/memberClient';

interface MemberProviderProps {
  children: React.ReactNode;
}

/**
 * Provider component that fetches and manages member state
 * Should be placed near the root of the app (e.g., in layout.tsx)
 */
export function MemberProvider({ children }: MemberProviderProps) {
  const { isInitialized, setMember, setLoading, setInitialized } = useMemberStore();

  useEffect(() => {
    // Only fetch if not already initialized
    if (isInitialized) return;

    async function initializeMember() {
      setLoading(true);
      try {
        const member = await fetchCurrentMember();
        setMember(member);
      } catch (error) {
        console.error('Failed to initialize member:', error);
        setMember(null);
      } finally {
        setLoading(false);
        setInitialized(true);
      }
    }

    initializeMember();
  }, [isInitialized, setMember, setLoading, setInitialized]);

  return <>{children}</>;
}
