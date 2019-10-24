package com.techelevator.campground.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Campground {
	private Long campGroundId;
	private int parkId;
	private String name;
	private String openFromMm;
	private String openToMm;
	private BigDecimal dailyFee;
	
	
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
	public String getOpenFromMm() {
		return openFromMm;
	}
	public void setOpenFromMm(String openFromMm) {
		this.openFromMm = openFromMm;
	}
	public String getOpenToMm() {
		return openToMm;
	}
	public void setOpenToMm(String openToMm) {
		this.openToMm = openToMm;
	}
	public BigDecimal getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(BigDecimal dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	
}
