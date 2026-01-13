'use client';

import Image from 'next/image';
import { useState, useRef, useEffect } from 'react';
import { useMemberStore, type MemberInfo } from '@/entities/member';

interface MemberProfileProps {
  member: MemberInfo;
}

export function MemberProfile({ member }: MemberProfileProps) {
  const [isOpen, setIsOpen] = useState(false);
  const dropdownRef = useRef<HTMLDivElement>(null);
  const reset = useMemberStore((state) => state.reset);

  // Close dropdown when clicking outside
  useEffect(() => {
    function handleClickOutside(event: MouseEvent) {
      if (dropdownRef.current && !dropdownRef.current.contains(event.target as Node)) {
        setIsOpen(false);
      }
    }

    document.addEventListener('mousedown', handleClickOutside);
    return () => document.removeEventListener('mousedown', handleClickOutside);
  }, []);

  const handleLogout = async () => {
    try {
      await fetch('/api/auth/logout', { method: 'POST' });
      reset(); // Reset member state
    } catch (error) {
      console.error('Logout failed:', error);
    }
  };

  return (
    <div className="relative" ref={dropdownRef}>
      <button
        onClick={() => setIsOpen(!isOpen)}
        className="flex items-center gap-2 hover:opacity-80 transition-opacity"
      >
        {member.imagePath ? (
          <Image
            src={`${process.env.NEXT_PUBLIC_S3_BASE_URL}/${member.imagePath}`}
            alt={member.nickname}
            width={32}
            height={32}
            unoptimized // minio, next optimizer 충돌로 인해 개발 환경에만 사용.
            className="rounded-full object-cover"
          />
        ) : (
          <div className="w-8 h-8 rounded-full bg-[#575bc7] flex items-center justify-center text-white text-sm font-medium">
            {member.nickname.charAt(0).toUpperCase()}
          </div>
        )}
        <span className="text-white text-[15px] font-medium hidden md:block">
          {member.nickname}
        </span>
      </button>

      {isOpen && (
        <div className="absolute right-0 top-full mt-2 w-48 bg-[#252736] border border-[#393a4b] rounded-lg shadow-lg py-1 z-50">
          <div className="px-4 py-2 border-b border-[#393a4b]">
            <p className="text-white text-sm font-medium">{member.nickname}</p>
          </div>
          <button
            onClick={handleLogout}
            className="w-full text-left px-4 py-2 text-[#9d9eb5] hover:text-white hover:bg-[#393a4b] text-sm transition-colors"
          >
            로그아웃
          </button>
        </div>
      )}
    </div>
  );
}
