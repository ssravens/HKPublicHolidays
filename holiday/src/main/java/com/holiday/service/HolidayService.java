package com.holiday.service;

import com.holiday.domain.Holiday;
import com.holiday.domain.HolidayRepository;
import com.holiday.request.HolidayRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HolidayService {
	private static SimpleDateFormat DATE_FORMAT_01 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat DATE_FORMAT_02 = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private HolidayRepository holidayRepository;

	public List<Holiday> getAllHolidays() {
		return holidayRepository.findAll();
	}

	public List<Holiday> getAllHolidaysByDateFilter(String startDate, String endDate) throws Exception {
		Date sDate = DATE_FORMAT_01.parse(startDate);
		Date eDate = DATE_FORMAT_01.parse(endDate);
		return holidayRepository.getAllHolidaysByDateFilter(sDate, eDate);
	}

	public List<Holiday> saveAllHolidays(List<HolidayRequest> holidays) throws Exception {
		List<Holiday> findAll = holidayRepository.findAll();
		if (findAll.isEmpty()) {
			List<Holiday> covertHolidayRequestToDomainObject = convertHolidayRequestToDomainObject(holidays);
			return holidayRepository.saveAll(covertHolidayRequestToDomainObject);
		}
		return findAll;
	}

	public List<Holiday> convertHolidayRequestToDomainObject(List<HolidayRequest> holidays) throws Exception {
		List<Holiday> list = new ArrayList<>(holidays.size());
		for (HolidayRequest h : holidays) {
			Holiday nHoliday = new Holiday();
			nHoliday.setUid(h.getUid());
			nHoliday.setSummary(h.getSummary());
			if (h.getDtstart() != null) {
				Date sDate = DATE_FORMAT_02.parse(String.valueOf(h.getDtstart().get(0)));
				nHoliday.setDtStart(sDate);
			}
			if (h.getDtend() != null) {
				Date eDate = DATE_FORMAT_02.parse(String.valueOf(h.getDtend().get(0)));
				nHoliday.setDtEnd(eDate);
			}
			list.add(nHoliday);
		}
		return list;
	}

}
