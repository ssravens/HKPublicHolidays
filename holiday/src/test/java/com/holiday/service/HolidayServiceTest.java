package com.holiday.service;

import com.holiday.domain.Holiday;
import com.holiday.request.HolidayRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class HolidayServiceTest {

	private HolidayService holidayService;

	@BeforeEach
	public void setUp() {
		holidayService = new HolidayService();
	}

	@Test
	@DisplayName("Test01: Convert Request Object to domain object")
	public void testConvertRequestToDomain() throws Exception {
		HolidayRequest req01 = new HolidayRequest();
		req01.setUid("abc@hk");
		req01.setSummary("test summary");
		List<Holiday> convertHolidayRequestToDomainObject = holidayService
				.convertHolidayRequestToDomainObject(Arrays.asList(req01));
		Assertions.assertEquals(1, convertHolidayRequestToDomainObject.size());
		Holiday holiday = convertHolidayRequestToDomainObject.get(0);
		Assertions.assertEquals("abc@hk", holiday.getUid());
		Assertions.assertEquals("test summary", holiday.getSummary());
	}

}
