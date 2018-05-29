package com.techelevator.project.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface CampsiteDAO {
	
	public List<Campsite> getOpenReservations(String userInput1, String userInput2, Campground campground);

	public LocalDate parseDate(String userInput);


}
