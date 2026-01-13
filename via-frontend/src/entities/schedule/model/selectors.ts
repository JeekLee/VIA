import {ScheduleEvent} from "@/entities/schedule/model/types";

export const getEventsForDate = (
    events: ScheduleEvent[],
    date: Date
) =>
    events.filter(
        e => e.date.toDateString() === date.toDateString()
    );
