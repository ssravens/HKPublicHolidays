package com.holiday.request;

import java.util.List;

public class HolidayRequest {
	private String uid;
	private String summary;
	private List<Object> dtstart;
	private List<Object> dtend;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public List<Object> getDtstart() {
		return dtstart;
	}

	public void setDtstart(List<Object> dtstart) {
		this.dtstart = dtstart;
	}

	public List<Object> getDtend() {
		return dtend;
	}

	public void setDtend(List<Object> dtend) {
		this.dtend = dtend;
	}

}
