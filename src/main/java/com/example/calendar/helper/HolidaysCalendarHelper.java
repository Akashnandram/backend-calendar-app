package com.example.calendar.helper;

import com.example.calendar.models.ApiResponseDto;
import com.example.calendar.models.UiContractHolidayDto;
import com.example.calendar.service.HttpClientService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Component
public class HolidaysCalendarHelper {
    private static final String API_KEY = "d8gfbzKZp6OWlMnZAb4Z2Vd7PPVAhzBQ";
    private static final String BASE_URL = "https://calendarific.com/api/v2/holidays";

    private final HttpClientService httpClientService;
    private final ObjectMapper objectMapper;


    public HolidaysCalendarHelper(HttpClientService httpClientService, ObjectMapper objectMapper) {
        this.httpClientService = httpClientService;
        this.objectMapper = objectMapper;
    }

    public ApiResponseDto getCalendarHolidaysDetails(String country, String year) throws Exception {
        String url = String.format("%s?api_key=%s&country=%s&year=%s", BASE_URL, API_KEY, country, year);
        Response response = httpClientService.getCall(url, new HashMap<>());
        String resAsString = "";
        if (response != null && response.code() == 200 && response.body() != null) {
            resAsString = response.body().string();
        }
        return objectMapper.readValue(resAsString, ApiResponseDto.class);
    }

    public List<UiContractHolidayDto> generateMonthDays(int year, int month) {
        List<UiContractHolidayDto> calendar = new ArrayList<>();

        LocalDate firstDay = LocalDate.of(year, month, 1);
        int daysInMonth = firstDay.lengthOfMonth();

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(year, month, day);

            UiContractHolidayDto dto = new UiContractHolidayDto();
            dto.setYear(year);
            dto.setMonth(month);
            dto.setDay(day);
            dto.setWeekDay(date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            dto.setColour("default");

            calendar.add(dto);
        }

        return calendar;
    }

}
