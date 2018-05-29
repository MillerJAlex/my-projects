package com.techelevator;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.project.model.Campground;
import com.techelevator.project.model.CampgroundJDBCDAO;
import com.techelevator.project.model.Park;
import com.techelevator.project.model.ParkJDBCDAO;

public class CampgroundJDBCDAOTest {

	private static SingleConnectionDataSource dataSource;
	private CampgroundJDBCDAO testCamp;
	private ParkJDBCDAO testPark;
	private Long newCampId;
	private Long newParkId;
	private Park thePark;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setAutoCommit(false);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		dataSource.destroy();
	}

	@Before
	public void setUp() throws Exception {

		testPark = new ParkJDBCDAO(dataSource);

		testCamp = new CampgroundJDBCDAO(dataSource);
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		String sqlParkIns = ("INSERT INTO park (name, location, establish_date, area, visitors, description) VALUES (?, ?, ?, ?, ?, ?) RETURNING park_id");
		newParkId = jdbcTemplate.queryForObject(sqlParkIns, Long.class, "Tester Park", "Location",
				LocalDate.parse("2018-01-01"), 100, 200, "Description");

		String sqlCampIns = ("INSERT INTO campground (park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (?, ?, ?, ?, ?) RETURNING campground_id");
		newCampId = jdbcTemplate.queryForObject(sqlCampIns, Long.class, newParkId, "Test Camp", 1, 1, 20);

	}

	@After
	public void tearDown() throws Exception {

		dataSource.getConnection().rollback();

	}

	@Test
	public void testGetAllCampgrounds() {

		List<Park> parkInfo = testPark.getAllParks();
		for(Park p : parkInfo) 
			if(p.getId().equals(newParkId)) {
				assertEquals(newParkId, p.getId());
				assertEquals("Tester Park", p.getName());

		List<Campground> campInfo = testCamp.getAllCampgrounds(p);
		
		for(Campground c : campInfo) {
			if(c.getCampgroundId().equals(newCampId)) {
				assertEquals(newCampId, c.getCampgroundId());
				assertEquals("Test Camp", c.getName());
				return;
			}
		}
		fail("Campground was not found");
	}
	
}

	private Park mapRowToParks(SqlRowSet results) {
		Park thePark;
		thePark = new Park();
		thePark.setId(results.getLong("park_id"));
		thePark.setName(results.getString("name"));
		thePark.setLocation(results.getString("location"));
		thePark.setEstablishDate(results.getDate("establish_date").toLocalDate()); // Remember this method!
		thePark.setArea(results.getInt("area"));
		thePark.setVisitors(results.getInt("visitors"));
		thePark.setDescription(results.getString("description"));

		return thePark;
	}

}
