export const generateMonthDays = (year: number, month: number) => {
    const firstDay = new Date(year, month, 1);
    const lastDay = new Date(year, month + 1, 0);
    const startingDayOfWeek = firstDay.getDay();
    const totalDays = lastDay.getDate();

    const days = [];

    // 이전 달의 날짜들
    for (let i = 0; i < startingDayOfWeek; i++) {
        days.push(null);
    }

    // 현재 달의 날짜들
    for (let i = 1; i <= totalDays; i++) {
        days.push(new Date(year, month, i));
    }

    return days;
};

export const generateWeekDays = (date: Date) => {
    const startOfWeek = new Date(currentDate);
    const day = startOfWeek.getDay();
    const diff = startOfWeek.getDate() - day;
    startOfWeek.setDate(diff);

    const days = [];
    for (let i = 0; i < 7; i++) {
        const date = new Date(startOfWeek);
        date.setDate(startOfWeek.getDate() + i);
        days.push(date);
    }
    return days;
};