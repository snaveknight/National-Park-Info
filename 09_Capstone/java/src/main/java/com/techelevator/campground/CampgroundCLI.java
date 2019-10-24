package com.techelevator.campground;

import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.CampgroundDAO;
import com.techelevator.campground.model.CustomerDAO;
import com.techelevator.campground.model.Park;
import com.techelevator.campground.model.ParkDAO;
import com.techelevator.campground.model.ReservatioDAO;
import com.techelevator.campground.model.SiteDAO;
import com.techelevator.campground.model.jdbc.JDBCCampgroundDAO;
import com.techelevator.campground.model.jdbc.JDBCCustomer;
import com.techelevator.campground.model.jdbc.JDBCParkDAO;
import com.techelevator.campground.model.jdbc.JDBCReservation;
import com.techelevator.campground.model.jdbc.JDBCSite;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {
	
	
	
	private Menu menu;
	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private SiteDAO siteDAO;
	private ReservatioDAO reservationDAO;
	private CustomerDAO customerDAO;
	
	public static void main(String[] args) {
		CampgroundCLI application = new CampgroundCLI();
		application.run();
		
	}

	public CampgroundCLI() {
		this.menu = new Menu(System.in, System.out);
		// create your DAOs here
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		
		parkDAO = new JDBCParkDAO(dataSource);
		campgroundDAO = new JDBCCampgroundDAO(dataSource);
		siteDAO = new JDBCSite(dataSource);
		reservationDAO = new JDBCReservation(dataSource);
		customerDAO = new JDBCCustomer(dataSource);
		
	}

	public void run() {	
		System.out.println("Welcome to our parks Menu!\r\n"
				+ "Here is a list of our parks.\r\n"
				+ "Type in the number of the park you would like more information "
				+ "on or to check for reservation availability");
		
		List<Park> parkList = parkDAO.getAllParks();
		
		while(true) {
			int i=0;
			String[] parkNames = new String[parkList.size() + 1];
			for (; i< parkList.size(); i++) {
				parkNames[i] = parkList.get(i).getName();
			}
			parkNames[i] = "quit";
			menu.getChoiceFromOptions(parkNames);
		}
	}
}
