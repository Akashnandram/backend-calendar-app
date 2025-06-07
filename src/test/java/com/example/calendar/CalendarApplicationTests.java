package com.example.calendar;

import com.example.calendar.helper.HolidaysCalendarHelper;
import com.example.calendar.models.ApiResponseDto;
import com.example.calendar.models.HolidaysDto;
import com.example.calendar.models.UiContractHolidayDto;
import com.example.calendar.service.CalendarColorService;
import com.example.calendar.service.HolidayCalendarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CalendarApplicationTests {
    @Autowired
    HolidaysCalendarHelper holidaysCalendarHelper;
    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    HolidayCalendarService holidayCalendarService;

    @Autowired
    CalendarColorService calendarColorService;

    @Test
    void generateMonthDaysTest() throws JsonProcessingException {
        List<UiContractHolidayDto> uiContractHolidays = holidaysCalendarHelper.generateMonthDays(2025, 5);
        System.out.println(objectMapper.writeValueAsString(uiContractHolidays));
    }

    @Test
    void getCalendarHolidaysDetailsTest() throws Exception {
        ApiResponseDto apiResponseDto = holidaysCalendarHelper.getCalendarHolidaysDetails("IN", "2025");
        System.out.println(objectMapper.writeValueAsString(apiResponseDto.getResponse().getHolidays()));
    }

    @Test
    public void getCalendarHolidaysWeeks() throws Exception {
        List<UiContractHolidayDto> uiContractHolidays = holidaysCalendarHelper.generateMonthDays(2025, 5);
        ApiResponseDto apiResponseDto = holidaysCalendarHelper.getCalendarHolidaysDetails("IN", "2025");
        List<HolidaysDto> holidays = apiResponseDto.getResponse().getHolidays();
        List<UiContractHolidayDto> uiContractHolidayDtos = calendarColorService.markHolidaysByWeek(uiContractHolidays, holidays);
        System.out.println(objectMapper.writeValueAsString(uiContractHolidayDtos));
    }
}
