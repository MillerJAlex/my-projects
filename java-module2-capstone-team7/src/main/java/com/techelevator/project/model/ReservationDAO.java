package com.techelevator.project.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {
	
	public List<Reservation> checkReservations(String userInput1, String userInput2, String campgrounName);
	
	public LocalDate parseDate(String userInput);
	
	public Reservation totalDays(String userInput, String userInput2);
	
	public long placeReservation(Campsite chosenSite, String userName, String userInput1, String userInput2); 
}
