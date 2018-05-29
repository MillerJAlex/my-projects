package com.techelevator.project.model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class ReservationJDBCDAO implements ReservationDAO {

	private JdbcTemplate jdbcTemplate;
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	public ReservationJDBCDAO(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public List<Reservation> checkReservations(String userInput1, String userInput2, String campgroundName) {
		List<Reservation> reservationList = new ArrayList<>();

		String sqlGetReser = "SELECT * FROM reservation JOIN site ON reservation.site_id = site.site_id JOIN campground on site.campground_id = campground.campground_id "
				+ "WHERE (from_date >= ? AND from_date <= ?) OR (to_date >= ? AND to_date <= ?) AND campground.name = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetReser, parseDate(userInput1), parseDate(userInput2),
				parseDate(userInput1), parseDate(userInput2), campgroundName);

		while (results.next()) {
			Reservation theReservation = mapRowToReservation(results);
			reservationList.add(theReservation);

		}
		return reservationList;
	}
	
	public long placeReservation(Campsite chosenSite, String userName, String userInput1, String userInput2) {
		String placeReservation = 
				"INSERT INTO reservation (site_id, name, from_date, to_date) VALUES (?, ?, ?, ?) " +
				"RETURNING reservation_id ";
		long newReservationId = jdbcTemplate.queryForObject(placeReservation, Long.class, chosenSite.getSiteId(), userName, parseDate(userInput1), parseDate(userInput2));
		
		return newReservationId;
	}


	public Reservation totalDays(String userInput, String userInput2) {
		Reservation theReservation;
		theReservation = new Reservation();
		
		LocalDate arrivalDate = parseDate(userInput);
		LocalDate departureDate = parseDate(userInput2);

		long days = ChronoUnit.DAYS.between(arrivalDate, departureDate);

		theReservation.setTotalDays(days);
		return theReservation;
	}

	private Reservation mapRowToReservation(SqlRowSet results) {
		Reservation theReservation;
		theReservation = new Reservation();
		theReservation.setReservationId(results.getLong("reservation_id"));
		theReservation.setSiteId(results.getInt("site_id"));
		theReservation.setName(results.getString("name"));
		theReservation.setFromDate(results.getDate("from_date").toLocalDate());
		theReservation.setToDate(results.getDate("to_date").toLocalDate());

		return theReservation;

	}

	@Override
	public LocalDate parseDate(String userInput) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		LocalDate date = LocalDate.parse(userInput, formatter);
		return date;
	}

}
