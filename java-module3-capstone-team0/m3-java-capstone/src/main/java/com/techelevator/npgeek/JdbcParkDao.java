package com.techelevator.npgeek;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
@Component
public class JdbcParkDao implements ParkDao{

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcParkDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public List<Park> getAllParks() {
		List<Park> allParks = new ArrayList<>();
		String sqlSelectAllParks = "SELECT * FROM park";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllParks);
		while (results.next()) {
			allParks.add(mapRowToProduct(results));
		}
		return allParks;

	}
	
	public Park getParkByParkCode(String parkCode) {
		for(Park park: getAllParks()) {
			if(park.getParkCode().equals(parkCode)) {
				return park;
			}
		}return null;
	}

	private Park mapRowToProduct(SqlRowSet row) {
		Park park = new Park();
		park.setParkCode(row.getString("parkcode"));
		park.setParkName(row.getString("parkname"));
		park.setState(row.getString("state"));
		park.setAcreage(row.getInt("acreage"));
		park.setElevationFt(row.getInt("elevationinfeet"));
		park.setMilesOfTrail(row.getFloat("milesoftrail"));
		park.setNumCampsites(row.getInt("numberofcampsites"));
		park.setClimate(row.getString("climate"));
		park.setYearFounded(row.getInt("yearfounded"));
		park.setAnnualVisCount(row.getInt("annualvisitorcount"));
		park.setInspirQuote(row.getString("inspirationalquote"));
		park.setInspirQuoteSource(row.getString("inspirationalquotesource"));
		park.setDescription(row.getString("parkdescription"));
		park.setEntryFee(row.getInt("entryfee"));
		park.setAnimalSpecies(row.getInt("numberofanimalspecies"));
		park.setParkImg("/img/parks/"+row.getString("parkcode").toLowerCase()+".jpg");
		return park;
	}
}
