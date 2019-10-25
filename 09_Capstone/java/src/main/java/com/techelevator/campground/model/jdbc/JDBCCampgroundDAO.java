package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Campground;
import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.Park;

public class JDBCCampgroundDAO implements CampgroundDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCCampgroundDAO(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Campground> displayCampgroundFromParkSelected(String choice) {
		List<Campground> campgroundList = new ArrayList<>();
		System.out.println("\nPARK CAMPGROUNDS\r\n");
		System.out.println(choice + " National Park Campgrounds");
		String sqlGetAllcampgrounds = "SELECT c.campground_id, c.name, c.open_from_mm, c.open_to_mm, c.daily_fee\r\n" + 
				"FROM campground c\r\n" + 
				"INNER JOIN park p\r\n" + 
				"ON c.park_id = p.park_id\r\n" + 
				"WHERE p.name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllcampgrounds, choice);
		while (results.next()) {
			Campground campground = new Campground();
			campground.setCampGroundId(results.getLong("campground_id"));
			campground.setName(results.getString("name"));
			campground.setOpenFromMm(results.getString("open_from_mm"));
			campground.setOpenToMm(results.getString("open_to_mm"));
			campground.setDailyFee(results.getBigDecimal("daily_fee"));
			campgroundList.add(campground);
		}
		return campgroundList;
	}

}
