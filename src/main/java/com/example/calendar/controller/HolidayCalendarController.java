package com.example.calendar.controller;


import com.example.calendar.models.ResquestParamsDto;
import com.example.calendar.models.UiContractHolidayDto;
import com.example.calendar.service.HolidayCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/calendar")
public class HolidayCalendarController {
    @Autowired
    HolidayCalendarService holidayCalendarService;

    @PostMapping("/holidays")
    public ResponseEntity<List<UiContractHolidayDto>> getHoliday(@RequestBody ResquestParamsDto param) {
        try {
            return ResponseEntity.ok(holidayCalendarService.getHolidays(param.getCounty(), param.getYear(), param.getMonth()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unable to retrieve My Loan details.");
        }
    }
}
