package com.example.calendar.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DateTimeDto {
    private int year;
    private int month;
    private int day;
}
