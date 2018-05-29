package com.techelevator.project.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Reservation {

	private Long reservationId;
	private int siteId;
	private String name;
	private LocalDate fromDate;
	private LocalDate toDate;
	private LocalDate createDate;
	private long totalDays;
	
	
	public Long getReservationId() {
		return reservationId;
	}
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	public int getSiteId() {
		return siteId;
	}
	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public LocalDate getCreateDate() {
		return createDate;
	}
	public void setCreateDate(LocalDate createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "Reservation [reservationId=" + reservationId + ", siteId=" + siteId + ", name=" + name + ", fromDate="
				+ fromDate + ", toDate=" + toDate + ", createDate=" + createDate + "]";
	}
	public long getTotalDays() {
		return totalDays;
	}
	public void setTotalDays(long days) {
		this.totalDays = days;
	}
	
	
}
