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
	public void searchForCampgroundReservationFromACampground() {
		
		
	}

	@Override
	public List<Customer> gatherUsersDataForSiteReservation() {
		Scanner userInput = new Scanner(System.in);
		System.out.println("Which campground (enter 0 to cancel)?__ ");
		String userSiteChoice = userInput.nextLine();
		int userCampgroundSite = Integer.parseInt(userSiteChoice);
		System.out.println("What is the arrival date? yyyy-mm-dd");
		String userFromDateString = userInput.nextLine();
		//LocalDate userDate = LocalDate.parse(userDateString);
		System.out.println("What is the departure date? yyyy-mm-dd");
		String userToDateString = userInput.nextLine();
		List<Customer> reservationList = new ArrayList<>();
		String sql = "select r.site_id,s.max_occupancy, s.accessible, \r\n" + 
				"s.max_rv_length, s.utilities, ((r.to_date - r.from_date) * c.daily_fee)AS cost\r\n" + 
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
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userFromDateString, userToDateString
				,userSiteChoice);
		while (results.next()) {
			Customer reservationsAvailable = new Customer();
			reservationsAvailable.setSiteId(results.getLong("site_id"));
			reservationsAvailable.setMaxOccupancy(results.getInt("max_occupancy"));
			reservationsAvailable.setAccessible(results.getBoolean("accessible"));
			reservationsAvailable.setMaxRvLength(results.getInt("max_rv_length"));
			reservationsAvailable.setUtilities(results.getBoolean("utilities"));
			reservationsAvailable.setCost(results.getString("cost"));
			reservationList.add(reservationsAvailable);
		}
		return reservationList;
	}

}
