package com.techelevator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.project.model.Campground;
import com.techelevator.project.model.CampgroundDAO;
import com.techelevator.project.model.CampgroundJDBCDAO;
import com.techelevator.project.model.Campsite;
import com.techelevator.project.model.CampsiteDAO;
import com.techelevator.project.model.CampsiteJDBCDAO;
import com.techelevator.project.model.Park;
import com.techelevator.project.model.ParkDAO;
import com.techelevator.project.model.ParkJDBCDAO;
import com.techelevator.project.model.Reservation;
import com.techelevator.project.model.ReservationDAO;
import com.techelevator.project.model.ReservationJDBCDAO;

public class CampgroundCLI {

	private ParkDAO parkDAO;
	private CampgroundDAO campgroundDAO;
	private CampsiteDAO campsiteDAO;
	private ReservationDAO reservationDAO;
	private Menu menu;

	public static void main(String[] args) {
		CampgroundCLI application = new CampgroundCLI();
		application.run();
	}

	public CampgroundCLI() {
		// Initiate menu object
		this.menu = new Menu(System.in, System.out);
		// Setup for database connection
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		// Link databases to Java objects
		parkDAO = new ParkJDBCDAO(dataSource);
		campgroundDAO = new CampgroundJDBCDAO(dataSource);
		campsiteDAO = new CampsiteJDBCDAO(dataSource);
		reservationDAO = new ReservationJDBCDAO(dataSource);
	}

	public void run() {
		displayApplicationBanner();
		System.out.println("\nSelect a Park for Further Details");
		
		// Create a List of Parks
		List<Park> parkList = parkDAO.getAllParks();
		// Create Park[] to be passed into the Menu based on List size
		Park[] parkArray = new Park[parkList.size()];
		// Fill Park[] with the List data
		parkArray = parkList.toArray(parkArray);
		// Creates a new Park object based on the chosen park through the Menu
		Park chosenPark = (Park)menu.getChoiceFromOptions(parkArray);
		
		showParkInfo(chosenPark);
		
		// The following code is similar to handling park information
		List<Campground> campgroundList = campgroundDAO.getAllCampgrounds(chosenPark);
		Campground[] campgroundArray = new Campground[campgroundList.size()];
		campgroundArray = campgroundList.toArray(campgroundArray);
		System.out.println("\nSelect a Campground");
		System.out.printf("%8s %16s %11s %14s", "-Name-", "-Open-", "-Close-", "-Daily Fee-");
		Campground chosenCampground = (Campground)menu.getChoiceFromOptions(campgroundArray);
		
		// --
		Scanner input = new Scanner(System.in);
		System.out.println("\nWhat is your arrival date (yyyy/MM/dd)?");
		String arrivalString = input.nextLine();
		System.out.println("What is your departure date (yyyy/MM/dd)?");
		String departureString = input.nextLine();
		
		
		List<Reservation> reservationList = reservationDAO.checkReservations(arrivalString, departureString, chosenCampground.getName());
		List<Campsite> campsiteList = campsiteDAO.getOpenReservations(arrivalString, departureString, chosenCampground);
		
		Campsite[] campsiteArray = new Campsite[campsiteList.size()];
		campsiteArray = campsiteList.toArray(campsiteArray);
		
		
		if (reservationList.size() > 0) {
			System.out.println("Sorry there are no available sites, please choose another date range.");
		}
		
		System.out.println("\nSelect a Campsite");
		System.out.printf("%12s %12s %10s %13s", "Name", "Open", "Close", "Daily Fee");
		//TODO: Check Ryver for code Nick sent to adjust this code...
		Campsite availableCampsites = (Campsite)menu.getChoiceSiteOptions(campsiteArray);

		//long chosenSite = availableCampsites.getSiteId();
		System.out.println("What's your name?");
		String userName = input.nextLine();
		
		System.out.println("The reservation has been made and the confirmation id is " + reservationDAO.placeReservation(availableCampsites, userName, arrivalString, departureString));
		
		
	}

	public void showParkInfo(Park chosenPark) {
		String strDate = String.format("Established: %1$td %1$tB %1$tY", chosenPark.getEstablishDate());
		System.out.printf("%n%s National Park%nLocation: %s %n%s %nArea: %d sq km%nAnnual Visitors: %d %n%n %s%n",
				chosenPark.getName(), chosenPark.getLocation(), strDate, chosenPark.getArea(), chosenPark.getVisitors(), chosenPark.getDescription());
	}
	
	public BigDecimal handleTotalFee(String start, String end, BigDecimal dailyFee) {
		LocalDate arrivalDate = parseDate(start);
		LocalDate departureDate = parseDate(end);

		long days = ChronoUnit.DAYS.between(arrivalDate, departureDate);
		
		BigDecimal totalDays = new BigDecimal(days);
		BigDecimal totalFee = totalDays.multiply(dailyFee);
		return totalFee;
	}
	
	public LocalDate parseDate(String userInput) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

		LocalDate date = LocalDate.parse(userInput, formatter);
		return date;
	}
	public LocalDate parseLocalDate(String input) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		LocalDate date = null;
		date = (LocalDate) formatter.parse(input);
		return date;
	}
	
	public void displayApplicationBanner() {
		System.out.println(" __    __              __      __                                __        _______                      __  " );              
		System.out.println("/  \\  /  |            /  |    /  |                              /  |      /       \\                    /  |    ");            
		System.out.println("$$  \\ $$ |  ______   _$$ |_   $$/   ______   _______    ______  $$ |      $$$$$$$  | ______    ______  $$ |   __   _______ ");
		System.out.println("$$$  \\$$ | /      \\ / $$   |  /  | /      \\ /       \\  /      \\ $$ |      $$ |__$$ |/      \\  /      \\ $$ |  /  | /       |");
		System.out.println("$$$$  $$ | $$$$$$  |$$$$$$/   $$ |/$$$$$$  |$$$$$$$  | $$$$$$  |$$ |      $$    $$/ $$$$$$  |/$$$$$$  |$$ |_/$$/ /$$$$$$$/ ");
		System.out.println("$$ $$ $$ | /    $$ |  $$ | __ $$ |$$ |  $$ |$$ |  $$ | /    $$ |$$ |      $$$$$$$/  /    $$ |$$ |  $$/ $$   $$<  $$      \\ ");
		System.out.println("$$ |$$$$ |/$$$$$$$ |  $$ |/  |$$ |$$ \\__$$ |$$ |  $$ |/$$$$$$$ |$$ |      $$ |     /$$$$$$$ |$$ |      $$$$$$  \\  $$$$$$  |");
		System.out.println("$$ | $$$ |$$    $$ |  $$  $$/ $$ |$$    $$/ $$ |  $$ |$$    $$ |$$ |      $$ |     $$    $$ |$$ |      $$ | $$  |/     $$/ ");
		System.out.println("$$/   $$/  $$$$$$$/    $$$$/  $$/  $$$$$$/  $$/   $$/  $$$$$$$/ $$/       $$/       $$$$$$$/ $$/       $$/   $$/ $$$$$$$/ " );
		System.out.println();
	}
}
	

