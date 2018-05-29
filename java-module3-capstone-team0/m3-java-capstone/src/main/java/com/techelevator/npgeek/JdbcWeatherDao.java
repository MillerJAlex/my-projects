package com.techelevator.npgeek;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcWeatherDao implements WeatherDao {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcWeatherDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Weather> getForeCast(String parkcode) {
		List<Weather> fiveDayForecast = new ArrayList<>();
		String sqlFavoriteSurvey="SELECT * FROM weather WHERE parkcode = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFavoriteSurvey, parkcode);
		
		while (results.next()) {
			Weather newDay = new Weather();
			newDay.setFivedayforecastvalue(results.getInt("fivedayforecastvalue"));
			newDay.setForecast(results.getString("forecast"));
			newDay.setLow(results.getInt("low"));
			newDay.setHigh(results.getInt("high"));
			newDay.setParkcode(results.getString("parkcode").toLowerCase());
			fiveDayForecast.add(newDay);
		}
	return fiveDayForecast;
	}
	
	
	
	
}
