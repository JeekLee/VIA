export const NAV_ITEMS = [
    { id: 'my-schedule', href: '/my-schedule', label: '내 일정' },
    { id: 'roadmap', href: '/roadmap', label: '로드맵' },
    { id: 'self-diagnosis', href: '/self-diagnosis', label: '자가 진단' },
    { id: 'courses', href: '/courses', label: '강의' },
    { id: 'recruitment', href: '/recruitment', label: '채용 공고' }
] as const;

export type NavItem = typeof NAV_ITEMS[number];