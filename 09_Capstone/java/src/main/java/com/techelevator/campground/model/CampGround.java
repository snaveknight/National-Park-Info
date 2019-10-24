package com.techelevator.campground.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CampGround {
	private Long campGroundId;
	private int parkId;
	private String name;
	private LocalDate openFromMm;
	private LocalDate openToMm;
	private BigDecimal dailyFee;
	
	public CampGround(Long campGroundId, int parkId, String name, LocalDate openFromMm, LocalDate openToMm,
			BigDecimal dailyFee) {
		this.campGroundId = campGroundId;
		this.parkId = parkId;
		this.name = name;
		this.openFromMm = openFromMm;
		this.openToMm = openToMm;
		this.dailyFee = dailyFee;
	}
	
	public Long getCampGroundId() {
		return campGroundId;
	}
	public void setCampGroundId(Long campGroundId) {
		this.campGroundId = campGroundId;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getOpenFromMm() {
		return openFromMm;
	}
	public void setOpenFromMm(LocalDate openFromMm) {
		this.openFromMm = openFromMm;
	}
	public LocalDate getOpenToMm() {
		return openToMm;
	}
	public void setOpenToMm(LocalDate openToMm) {
		this.openToMm = openToMm;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	
}
