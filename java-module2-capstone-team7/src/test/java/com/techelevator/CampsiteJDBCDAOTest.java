package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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

import com.techelevator.project.model.Campground;
import com.techelevator.project.model.CampgroundJDBCDAO;
import com.techelevator.project.model.Campsite;
import com.techelevator.project.model.CampsiteJDBCDAO;
import com.techelevator.project.model.Park;
import com.techelevator.project.model.ParkJDBCDAO;
import com.techelevator.project.model.ReservationJDBCDAO;

public class CampsiteJDBCDAOTest {
	private static SingleConnectionDataSource dataSource;
	private CampsiteJDBCDAO testSite;
	private ParkJDBCDAO testPark;
	private CampgroundJDBCDAO testGround;
	private ReservationJDBCDAO testReservation;
	private Long newCampsiteId;
	JdbcTemplate jdbcTemplate;
	Campsite newCampsite;
	private Long existingReservationId;
	private Campground camp;
	private Long newParkId;
	private Long newCampId;

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
		testSite = new CampsiteJDBCDAO(dataSource);
		testGround = new CampgroundJDBCDAO(dataSource);
		testReservation = new ReservationJDBCDAO(dataSource);

		jdbcTemplate = new JdbcTemplate(dataSource);

		String sqlParkIns = ("INSERT INTO park (name, location, establish_date, area, visitors, description) VALUES (?, ?, ?, ?, ?, ?) RETURNING park_id");
		newParkId = jdbcTemplate.queryForObject(sqlParkIns, Long.class, "Tester Park", "Location",
				LocalDate.parse("2018-01-01"), 100, 200, "Description");

		String sqlCampIns = ("INSERT INTO campground (park_id, name, open_from_mm, open_to_mm, daily_fee) VALUES (?, ?, ?, ?, ?) RETURNING campground_id");
		newCampId = jdbcTemplate.queryForObject(sqlCampIns, Long.class, newParkId, "Test Camp", 1, 1, 20);

		String sqlnewSite = "INSERT INTO site(campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities) "
				+ "VALUES(?, ?, ?, ?, ?, ?) RETURNING site_id";
		newCampsiteId = jdbcTemplate.queryForObject(sqlnewSite, Long.class, newCampId, 1, 0, false, 0, false);

		String sqlNewReservation = "INSERT INTO reservation(site_id, name, from_date, to_date, create_date) "
				+ "VALUES(?, ?, ?, ?, ?) RETURNING reservation_id";
		existingReservationId = jdbcTemplate.queryForObject(sqlNewReservation, Long.class, newCampsiteId, "Mr. Death",
				LocalDate.parse("2018-02-01"), LocalDate.parse("2018-03-10"), LocalDate.parse("2018-01-01"));

	}

	@After
	public void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	}

	@Test
	public void testGetSelectedCampsitesWithValidDate() {

		List<Park> parkInfo = testPark.getAllParks();
		for (Park p : parkInfo) {
			if (p.getId().equals(newParkId)) {
				assertEquals(newParkId, p.getId());
				assertEquals("Tester Park", p.getName());

				List<Campground> campInfo = testGround.getAllCampgrounds(p);

				for (Campground c : campInfo) {
					if (c.getCampgroundId().equals(newCampId)) {
						assertEquals(newCampId, c.getCampgroundId());
						assertEquals("Test Camp", c.getName());

						List<Campsite> newCampsites = testSite.getOpenReservations("2018-01-01", "2018-01-10", c);
						for (Campsite camp : newCampsites) {
							if (camp.getSiteId().equals(newCampsiteId)) {
								assertEquals(newCampsiteId, camp.getSiteId());
								return;
							}
						}

						fail("Site not returned");
					}
				}
			}
		}
	}
}
