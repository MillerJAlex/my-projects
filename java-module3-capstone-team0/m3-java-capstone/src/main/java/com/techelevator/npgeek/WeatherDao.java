package com.techelevator.npgeek;

import java.util.List;

public interface WeatherDao {
	
	List<Weather> getForeCast(String parkcode);

}
