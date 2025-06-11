package com.example.calendar.service;

import com.example.calendar.models.HolidaysDto;
import com.example.calendar.models.UiContractHolidayDto;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeParseException;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalendarColorService {

    public List<UiContractHolidayDto> markHolidaysByWeek(List<UiContractHolidayDto> monthDays, List<HolidaysDto> holidays) {

        Map<LocalDate, List<String>> holidayMap = holidays.stream()
                .collect(Collectors.groupingBy(
                        h -> parseToLocalDate(h.getDate().getIso()),
                        Collectors.mapping(HolidaysDto::getName, Collectors.toList())
                ));

        Map<Integer, List<UiContractHolidayDto>> weeksMap = new HashMap<>();

        for (UiContractHolidayDto dto : monthDays) {
            LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDay());
            int week = date.get(WeekFields.ISO.weekOfMonth());
            weeksMap.computeIfAbsent(week, k -> new ArrayList<>()).add(dto);
        }

        for (Map.Entry<Integer, List<UiContractHolidayDto>> entry : weeksMap.entrySet()) {
            List<UiContractHolidayDto> weekDays = entry.getValue();

            long holidayCount = weekDays.stream()
                    .filter(d -> holidayMap.containsKey(LocalDate.of(d.getYear(), d.getMonth(), d.getDay())))
                    .count();

            String color = "";
            if (holidayCount >= 2) {
                color = "#BBFFDD";
            } else if (holidayCount == 1) {
                color = "#0CA458";
            }

            for (UiContractHolidayDto dto : weekDays) {
                LocalDate date = LocalDate.of(dto.getYear(), dto.getMonth(), dto.getDay());
                dto.setColour(color);
                dto.setFestivals(holidayMap.getOrDefault(date, Collections.emptyList()));
            }
        }

        return monthDays;
    }

    private LocalDate parseToLocalDate(String iso) {
        try {
            return LocalDate.parse(iso);
        } catch (DateTimeParseException e) {
            return OffsetDateTime.parse(iso).toLocalDate();
        }
    }
}
