package com.neusoft.tijian.service;

import com.neusoft.tijian.dto.CalendarResponseDto;

import java.util.List;

public interface CalendarService {
    // 生成预约日历
    public List<CalendarResponseDto> listAppointmentCalendar(CalendarResponseDto calendarResponseDto);
}
