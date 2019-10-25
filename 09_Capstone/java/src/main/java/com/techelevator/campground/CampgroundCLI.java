package com.techelevator.campground;

import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.campground.model.Campground;
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
	private static final String SELECTED_PARK_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String SELECTED_PARK_SEARCH_RESERVATION = "Search for Reservation";
	private static final String SELECTED_PARK_RETURN_PREV_SCREEN = "Return to Previous Screen";
	private static final String[] SELECTED_PARK_COMMAND_MENU = new String[] {SELECTED_PARK_VIEW_CAMPGROUNDS, SELECTED_PARK_SEARCH_RESERVATION, SELECTED_PARK_RETURN_PREV_SCREEN};
	
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
		greetingMessageForMenu();
		while(true) {
		handleListOfParks();
		String parkChoice = (String)menu.getChoiceFromOptions(SELECTED_PARK_COMMAND_MENU);
		}
	}
	
	public void handleListOfParks() {
		List<Park> parkList = parkDAO.getAllParks();
		
		int i=0;
		String[] parkNames = new String[parkList.size() + 1];
		for (; i< parkList.size(); i++) {
			parkNames[i] = parkList.get(i).getName();
		}
		parkNames[i] = "quit";
		String parkChoice = (String)menu.getChoiceFromOptions(parkNames);
		if(parkChoice.equals("quit")) {
			System.exit(0);
		}
		handleSelectedPark(parkChoice);
	}
	
	public void handleSelectedPark(String parkChoice) {
		Park parkListDescription = parkDAO.getSelectedParkInfo(parkChoice);
		System.out.println("\r\nPARK INFORMATION SCREEN \r\n");
		System.out.println(parkListDescription.getName() + " National Park");
		System.out.println("Location: " + parkListDescription.getLocation());
		System.out.println("Established: " + parkListDescription.getEstablishDate());
		System.out.println("Area: " + parkListDescription.getArea());
		System.out.println("Annual Visitors: " + parkListDescription.getVisitors());
		System.out.println("\r\n" +  parkListDescription.getDescription());
		
		// Andy debug
		String commandChoice = (String)menu.getChoiceFromOptions(SELECTED_PARK_COMMAND_MENU);
		
		if(commandChoice.equals(SELECTED_PARK_VIEW_CAMPGROUNDS)) {
			displayCampgroundFromParkSelected(parkChoice);
		}  else if(commandChoice.equals(SELECTED_PARK_SEARCH_RESERVATION)) {
			System.out.println("first time thru display 3");
		} else if(commandChoice.equals(SELECTED_PARK_RETURN_PREV_SCREEN)) {
			System.out.println("first time thru display 3");
			run();
		}
			
		
	}


	
	
	public void displayCampgroundFromParkSelected(String parkChoice) {
		List <Campground> campgroundList = campgroundDAO.displayCampgroundFromParkSelected(parkChoice);
		
		System.out.println("\n	Name	Open	Close	Daily Fee");
		for(Campground cgl : campgroundList) {
			//cgl.getName();
			System.out.println("#" + cgl.getCampGroundId() + "	" + cgl.getName() + "		" + cgl.getOpenFromMm() + "	" + cgl.getOpenToMm() + "	$" + cgl.getDailyFee());
		}
	}
		
		
		
		
	
	
	public void greetingMessageForMenu() {
		System.out.println("Welcome to our parks Menu!\r\n"
				+ "Here is a list of our parks.\r\n"
				+ "Type in the number of the park you would like more information "
				+ "on or to check for reservation availability");
	}
}
