package com.techelevator.project.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class CampgroundJDBCDAO implements CampgroundDAO{
	
	private JdbcTemplate jdbcTemplate;
	
	// Constructor
	public CampgroundJDBCDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getAllCampgrounds(Park chosenPark) {
		List<Campground> campgroundList = new ArrayList<>();
		String sqlGetCamp = "Select * FROM campground WHERE park_id = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetCamp, chosenPark.getId());

		while (results.next()) {
			Campground theCampground = mapRowToParks(results);
			campgroundList.add(theCampground);
			
		}
		return campgroundList;
	}
	
	private Campground mapRowToParks(SqlRowSet results) {
		Campground theCampground;
		theCampground = new Campground();
		theCampground.setCampgroundId(results.getLong("campground_id"));
		theCampground.setParkId(results.getInt("park_id"));
		theCampground.setName(results.getString("name"));
		theCampground.setOpenFrom(results.getInt("open_from_mm"));
		theCampground.setOpenTo(results.getInt("open_to_mm"));
		theCampground.setDailyFee(results.getBigDecimal("daily_fee"));

		return theCampground;
	}


}
