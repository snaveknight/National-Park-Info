package com.techelevator.campground.model;

import java.time.LocalDate;
import java.util.List;

public interface ReservatioDAO {
	
	public List<Customer> gatherUsersDataForSiteReservation();
	

	public void takeInUsersChoiceForSiteAndReturnReservationId(int userChoiceInt, String userName, LocalDate userFromDate, LocalDate userToDate);
	
	public void optionTwoFromMainMenuShowsAllReservationsForThatPark(String parkChoice);
	
}
