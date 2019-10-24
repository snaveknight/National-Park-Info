package com.techelevator.campground.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;

public class JDBCParkDAO implements ParkDAO{
	
	private JdbcTemplate jdbcTemplate;
	public JDBCParkDAO(BasicDataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Park> getAllParks() {
		List<Park> parkList = new ArrayList<>();
		String sqlGetAllParks = "SELECT name FROM park ORDER BY name ASC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParks);
		while (results.next()) {
			Park park = new Park();
			park.setName(results.getString("name"));
			parkList.add(park);
		}
		return parkList;
	}

	@Override
	public Park getSelectedParkInfo(String choice){
		Park parkListDescription = new Park();
		Park park = new Park();
		String sqlGetAllParkInfo = "SELECT * FROM park WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllParkInfo, choice);
		while (results.next()) {
			park.setParkId(results.getLong("park_id"));
			park.setName(results.getString("name"));
			park.setLocation(results.getString("location"));
			park.setEstablishDate(results.getDate("establish_date").toLocalDate());
			park.setArea(results.getInt("area"));
			park.setVisitors(results.getInt("visitors"));
			park.setDescription(results.getString("description"));
		}
		return park;
	}

}
