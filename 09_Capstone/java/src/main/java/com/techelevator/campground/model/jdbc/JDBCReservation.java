package com.techelevator.campground.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Customer;
import com.techelevator.campground.model.ReservatioDAO;
import com.techelevator.campground.model.Reservation;

public class JDBCReservation implements ReservatioDAO {
	private JdbcTemplate jdbcTemplate;
	public JDBCReservation(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Customer> gatherUsersDataForSiteReservation() {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Which campground (enter 0 to cancel)?__ ");
		String userSiteChoice = userInput.nextLine();
		int userCampgroundSite = Integer.parseInt(userSiteChoice);
		System.out.println("What is the arrival date? yyyy-mm-dd");
		String userFromDateString = userInput.nextLine();
		LocalDate userFromDate = LocalDate.parse(userFromDateString);
		System.out.println("What is the departure date? yyyy-mm-dd");
		String userToDateString = userInput.nextLine();
		LocalDate userToDate = LocalDate.parse(userToDateString);
		List<Customer> reservationList = new ArrayList<>();
		String sql = "select r.site_id,s.max_occupancy, s.accessible, \r\n" + 
				"s.max_rv_length, s.utilities, ((r.to_date - r.from_date) * c.daily_fee) AS cost\r\n" + 
				"FROM site s\r\n" + 
				"INNER JOIN campground c\r\n" + 
				"ON c.campground_id = s.campground_id\r\n" + 
				"INNER JOIN reservation r\r\n" + 
				"ON r.reservation_id = s.site_id\r\n" + 
				"INNER JOIN park p\r\n" + 
				"ON c.park_id = p.park_id\r\n" + 
				"WHERE  NOT r.from_date BETWEEN ? AND ? \r\n" + 
				"AND s.site_number = ?\r\n" + 
				"ORDER BY p.visitors DESC\r\n" + 
				"LIMIT 5";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userFromDate, userToDate
				,userCampgroundSite);
		System.out.println("Results Matching Your Search Criteria\r\n"
				+ "Site No.     Max Occup.  Accessible?    RV Len   Utility    Cost");
		
		while (results.next()) {
			Customer reservationsAvailable = new Customer();
			reservationsAvailable.setSiteId(results.getLong("site_id"));
			reservationsAvailable.setMaxOccupancy(results.getInt("max_occupancy"));
			reservationsAvailable.setAccessible(results.getBoolean("accessible"));
			reservationsAvailable.setMaxRvLength(results.getInt("max_rv_length"));
			reservationsAvailable.setUtilities(results.getBoolean("utilities"));
			reservationsAvailable.setCost(results.getString("cost"));
			reservationList.add(reservationsAvailable);
			for (Customer custReservList: reservationList) {
				System.out.print(custReservList.getSiteId() + "     ");  
				System.out.print(custReservList.getMaxOccupancy() +"     "); 
				System.out.print(custReservList.isAccessible() +"    ");
				System.out.print(custReservList.getMaxRvLength() +"    ");
				System.out.print(custReservList.isUtilities() +"     "); 
				System.out.print("$" + custReservList.getCost() + "    ");
			}	
		}
		while(true) {
		System.out.println("\r\nWhich site should be reserved (enter 0 to cancel)?__");
		String userChoiceString = userInput.nextLine();
		int userChoiceInt = Integer.parseInt(userChoiceString);
		if (userChoiceInt == 0) {
			break;
		}else {
		System.out.println("What name should the reservation be made under?__");
		String userName = userInput.nextLine();
		takeInUsersChoiceForSiteAndReturnReservationId(userChoiceInt, userName, userFromDate, userToDate);
		}
		break;
		}
		
		return reservationList;
	}

	@Override
	public void takeInUsersChoiceForSiteAndReturnReservationId(int userChoiceInt, String userName,
			LocalDate userFromDate, LocalDate userToDate) {
		String sqlGetReservId = "INSERT INTO reservation (site_id, name, from_date, to_date, create_date)\r\n" + 
				"VALUES (?, ?, ?, ?, CURRENT_DATE);";
		jdbcTemplate.update(sqlGetReservId, userChoiceInt, userName, userFromDate,
				userToDate);
		
		String sqlShowReservId = "SELECT reservation_id\r\n" + 
				"FROM reservation \r\n" + 
				"WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlShowReservId, userName);
		Long reservationId = 0L;
		while (results.next()) {
			Reservation reserId = new Reservation();
			reserId.setReservationId(results.getLong("reservation_id"));
			reservationId = reserId.getReservationId();
		}
		System.out.println("The reservation has been made and the confirmation id is " + reservationId);
		
	}

	@Override
	public void optionTwoFromMainMenuShowsAllReservationsForThatPark(String parkChoice) {
		String sqlGetAllReservations = "SELECT r.reservation_id, r.site_id, r.name, r.from_date, r.to_date, r.create_date \r\n" + 
				"FROM reservation r\r\n" + 
				"INNER JOIN site s\r\n" + 
				"ON r.site_id = s.site_id\r\n" + 
				"INNER JOIN campground c\r\n" + 
				"ON s.campground_id = c.campground_id\r\n" + 
				"INNER JOIN park p\r\n" + 
				"ON p.park_id = c.park_id\r\n" + 
				"WHERE p.name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllReservations, parkChoice);
		List<Reservation> parkReservations = new ArrayList<>();
		while (results.next()) {
			Reservation reserves = new Reservation();
			reserves.setReservationId(results.getLong("reservation_id"));
			reserves.setSiteId(results.getInt("site_id"));
			reserves.setName(results.getString("name"));
			reserves.setFromDate(results.getDate("from_date").toLocalDate());
			reserves.setToDate(results.getDate("to_date").toLocalDate());
			reserves.setCreateDate(results.getDate("create_date").toLocalDate());
			parkReservations.add(reserves);
		}

		
		System.out.println();
		for (Reservation parkReservList: parkReservations) {
			String [] test = new String[] {parkReservList.getReservationId().toString(), parkReservList.getSiteId().toString(), };
			
			
			
			
			
			System.out.print(parkReservList.getReservationId() + "     ");  
			System.out.print(parkReservList.getSiteId() + "     ");
			System.out.print(parkReservList.getName() + "     ");
			System.out.print(parkReservList.getFromDate() + "     ");
			System.out.print(parkReservList.getToDate() + "     "); 
			System.out.println(parkReservList.getCreateDate() + "     ");
		}	
		
	}
	
	

}
