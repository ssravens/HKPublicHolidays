package com.holiday.controller;

import com.holiday.domain.Holiday;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class HolidayControllerTest {

	@Test
	@DisplayName("Test01: Check Load All Holidays")
	public void testGetHolidays() {
		String uri = "http://localhost:8080/holiday/";
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Holiday>> exchange = restTemplate.exchange(uri, HttpMethod.GET,
				new HttpEntity<Object>(headers), new ParameterizedTypeReference<List<Holiday>>() {
				});

		List<Holiday> holidays = exchange.getBody();

		Assertions.assertNotNull(holidays);
		Holiday holiday = holidays.get(0);
		Assertions.assertNotNull(holiday.getUid());
		Assertions.assertNotNull(holiday.getSummary());
		Assertions.assertNotNull(holiday.getDtEnd());
		Assertions.assertNotNull(holiday.getDtStart());
	}
	
	@Test
	@DisplayName("Test02: Check Load All Holidays with Date Filter")
	public void testGetHolidaysWithDateFilter() {
		String uri = "http://localhost:8080/holiday/filterByDate?startDate=2021-01-01&endDate=2021-12-31";
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Content-Type", "application/json");
		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<List<Holiday>> exchange = restTemplate.exchange(uri, HttpMethod.GET,
				new HttpEntity<Object>(headers), new ParameterizedTypeReference<List<Holiday>>() {
				});

		List<Holiday> holidays = exchange.getBody();

		Assertions.assertNotNull(holidays);
		Holiday holiday = holidays.get(0);
		Assertions.assertNotNull(holiday.getUid());
		Assertions.assertNotNull(holiday.getSummary());
		Assertions.assertNotNull(holiday.getDtEnd());
		Assertions.assertNotNull(holiday.getDtStart());
	}

}
