package com.techelevator.campground.model;

import java.util.List;

public interface ParkDAO {

	public List<Park> getAllParks(); 
	
	public Park getSelectedParkInfo(String choice);
	
}
