package com.example.calendar.service;

import com.example.calendar.helper.HolidaysCalendarHelper;
import com.example.calendar.models.ApiResponseDto;
import com.example.calendar.models.UiContractHolidayDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HolidayCalendarService {

    private final HolidaysCalendarHelper holidaysCalendarHelper;
    private final CalendarColorService calendarColorService;

    public HolidayCalendarService(HolidaysCalendarHelper holidaysCalendarHelper, CalendarColorService calendarColorService) {
        this.holidaysCalendarHelper = holidaysCalendarHelper;
        this.calendarColorService = calendarColorService;
    }

    public List<UiContractHolidayDto> getHolidays(String county, String year, String month) throws Exception {
        List<UiContractHolidayDto> monthDays = holidaysCalendarHelper.generateMonthDays(Integer.parseInt(year), Integer.parseInt(month));
        ApiResponseDto apiResponseDto = holidaysCalendarHelper.getCalendarHolidaysDetails(county, year);
        List<UiContractHolidayDto> monthWithColoredWeeks = calendarColorService.markHolidaysByWeek(monthDays, apiResponseDto.getResponse().getHolidays());
        return monthWithColoredWeeks;
    }

}
