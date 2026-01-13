'use client'

import { Logo } from './Logo'
import { Navigation } from './Navigation'
import { LoginButton } from '@/features/account/auth'

export const GNB = () => {
  return (
    <nav className="fixed top-0 left-0 right-0 bg-[#191a23] z-50 border-b border-[#393a4b]">
      <div className="flex items-center justify-between px-4 md:px-14 py-4">
        <div className="flex items-center gap-8 md:gap-12">
          <Logo />
          <Navigation />
        </div>
        <LoginButton />
      </div>
    </nav>
  )
}
