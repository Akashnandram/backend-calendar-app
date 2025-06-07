package com.example.calendar.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResquestParamsDto {
    String county;
    String year;
    String month;
}
