'use client';

import { Logo } from './Logo'
import { Navigation } from './Navigation'
import { MemberProfile } from './MemberProfile'
import { LoginButton } from '@/features/account/auth'
import { useMemberStore } from '@/entities/member'

export function GNB() {
  const { member, isLoading, isInitialized } = useMemberStore()

  return (
    <nav className="fixed top-0 left-0 right-0 bg-[#191a23] z-50 border-b border-[#393a4b]">
      <div className="flex items-center justify-between px-4 md:px-14 py-4">
        <div className="flex items-center gap-8 md:gap-12">
          <Logo />
          <Navigation />
        </div>
        <AuthSection member={member} isLoading={isLoading} isInitialized={isInitialized} />
      </div>
    </nav>
  )
}

function AuthSection({
  member,
  isLoading,
  isInitialized
}: {
  member: ReturnType<typeof useMemberStore>['member']
  isLoading: boolean
  isInitialized: boolean
}) {
  // Show skeleton while loading
  if (!isInitialized || isLoading) {
    return <div className="w-[120px] h-[36px] bg-[#252736] rounded-[4px] animate-pulse" />
  }

  return member ? <MemberProfile member={member} /> : <LoginButton />
}
