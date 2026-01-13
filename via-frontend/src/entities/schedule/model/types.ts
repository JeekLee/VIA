export interface ScheduleEvent {
    id: number;

    roadmapId?: number;
    roadmap?: string;

    title: string;
    content: string;

    startAt: Date;
    endAt: Date;

    assignment?: string;
    aiFeedback?: string;
}