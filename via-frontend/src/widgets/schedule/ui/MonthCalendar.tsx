'use client';

import { ScheduleEvent } from "@/entities/schedule/model/types";
import { getEventsForDate } from '@/entities/schedule/model/selectors';
import { generateMonthDays } from '@/shared/lib/date/calendar';

interface MonthCalendarProps {
    currentDate: Date;
    events: ScheduleEvent[];
    onSelectEvent: (event: ScheduleEvent) => void;
}

export function MonthCalendar(
    {
        currentDate,
        events,
        onSelectEvent,
    }: MonthCalendarProps
) {
    const year = currentDate.getFullYear();
    const month = currentDate.getMonth();
    const days = generateMonthDays(year, month);

    return (
        <div>
            {/* 요일 헤더 */}
            <div className="grid grid-cols-7 mb-2">
                {['일', '월', '화', '수', '목', '금', '토'].map((day) => (
                    <div
                        key={day}
                        className="text-center text-sm text-gray-400 font-medium py-2"
                    >
                        {day}
                    </div>
                ))}
            </div>

            {/* 날짜 그리드 */}
            <div className="grid grid-cols-7 gap-2">
                {days.map((date, index) => {
                    const dayEvents = date
                        ? getEventsForDate(events, date)
                        : [];

                    return (
                        <div
                            key={index}
                            className={`min-h-[120px] p-2 rounded border
                ${
                                date
                                    ? 'bg-[#2a2b34] border-[#393a4b]'
                                    : 'bg-[#1a1b24] border-[#2a2b34]'
                            }`}
                        >
                            {date && (
                                <>
                                    <div className="text-right text-sm text-gray-300 mb-1">
                                        {date.getDate()}
                                    </div>

                                    <div className="space-y-1">
                                        {dayEvents.map((event) => (
                                            <button
                                                key={event.id}
                                                onClick={() => onSelectEvent(event)}
                                                className="w-full text-left text-xs px-2 py-1 rounded
                                   bg-[#575bc7]/20 text-[#a8abff]
                                   hover:opacity-80 transition truncate"
                                            >
                                                {event.title}
                                            </button>
                                        ))}
                                    </div>
                                </>
                            )}
                        </div>
                    );
                })}
            </div>
        </div>
    );
}