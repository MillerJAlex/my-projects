package com.techelevator.project.model;

import java.util.List;

public interface CampgroundDAO {
	
	public List<Campground> getAllCampgrounds(Park chosenPark);
	
}
