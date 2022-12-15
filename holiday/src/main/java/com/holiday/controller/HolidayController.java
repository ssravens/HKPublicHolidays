package com.holiday.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.holiday.domain.Holiday;
import com.holiday.request.HolidayRequest;
import com.holiday.service.HolidayService;

@RestController
@RequestMapping(value = "/holiday")
public class HolidayController {

	@Autowired
	private HolidayService holidayService;

	@CrossOrigin
	@GetMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Holiday>> getAllHolidays() {
		return ResponseEntity.ok(holidayService.getAllHolidays());
	}

	@CrossOrigin
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Holiday>> saveAllHolidays(@RequestBody List<HolidayRequest> holidays) throws Exception {
		return ResponseEntity.ok(holidayService.saveAllHolidays(holidays));
	}

	@CrossOrigin
	@GetMapping(value = "/filterByDate", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Holiday>> getAllHolidaysByDateFilter(@RequestParam(name = "startDate") String startDate,
			@RequestParam(name = "endDate") String endDate) throws Exception {
		if (startDate == null || endDate == null) {
			return ResponseEntity.ok(new ArrayList<>());
		}
		return ResponseEntity.ok(holidayService.getAllHolidaysByDateFilter(startDate, endDate));
	}

}
