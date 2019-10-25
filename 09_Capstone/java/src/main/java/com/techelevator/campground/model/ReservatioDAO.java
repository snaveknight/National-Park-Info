package com.techelevator.campground.model;

import java.util.List;

public interface ReservatioDAO {

	public void searchForCampgroundReservationFromACampground ();
	
	public List<Customer> gatherUsersDataForSiteReservation();
	
}
