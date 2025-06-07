package com.example.calendar.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UiContractHolidayDto {
    private int year;
    private int month;
    private int day;
    private String weekDay;
    private String colour;
    private List<String> festivals;
}
