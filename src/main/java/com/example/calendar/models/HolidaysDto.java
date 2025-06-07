package com.example.calendar.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HolidaysDto {
    private String name;
    private String description;
    CustomDate date;
}
