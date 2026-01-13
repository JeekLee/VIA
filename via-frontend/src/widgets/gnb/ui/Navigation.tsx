import Link from 'next/link'
import { NAV_ITEMS } from '../config/navItems'

export const Navigation = () => {
  return (
    <div className="hidden md:flex items-center gap-6 text-white text-[15px] font-medium">
      {NAV_ITEMS.map((item) => {
        return (
          <Link 
            key={item.id} 
            href={item.href} 
            className="hover:opacity-80 transition-opacity"
          >
            {item.label}
          </Link>
        )
      })}
    </div>
  )
}
