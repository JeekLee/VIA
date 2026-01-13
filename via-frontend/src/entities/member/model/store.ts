import { create } from 'zustand';
import type { MemberInfo } from './types';

interface MemberState {
  member: MemberInfo | null;
  isLoading: boolean;
  isInitialized: boolean;
  setMember: (member: MemberInfo | null) => void;
  setLoading: (isLoading: boolean) => void;
  setInitialized: (isInitialized: boolean) => void;
  reset: () => void;
}

export const useMemberStore = create<MemberState>((set) => ({
  member: null,
  isLoading: true,
  isInitialized: false,
  setMember: (member) => set({ member }),
  setLoading: (isLoading) => set({ isLoading }),
  setInitialized: (isInitialized) => set({ isInitialized }),
  reset: () => set({ member: null, isLoading: false, isInitialized: true }),
}));
