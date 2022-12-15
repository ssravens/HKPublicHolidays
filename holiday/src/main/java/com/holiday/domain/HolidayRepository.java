package com.holiday.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, Long> {

	@Query("SELECT h FROM Holiday h where h.dtStart >= ?1 AND h.dtEnd <= ?2")
	List<Holiday> getAllHolidaysByDateFilter(Date startDate, Date endDate);

}
