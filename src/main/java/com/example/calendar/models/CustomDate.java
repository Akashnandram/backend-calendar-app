package com.example.calendar.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDate {
    private String iso;
    private DateTimeDto datetime;
}
