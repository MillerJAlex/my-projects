package com.techelevator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.project.model.Park;
import com.techelevator.project.model.ParkJDBCDAO;

public class ParkJDBCDAOTest {
	
	private static SingleConnectionDataSource dataSource;
	private ParkJDBCDAO testPark;
	private Long newParkId;

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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		String sqlParkIns = ("INSERT INTO park (name, location, establish_date, area, visitors, description) VALUES (?, ?, ?, ?, ?, ?) RETURNING park_id");
		newParkId = jdbcTemplate.queryForObject(sqlParkIns, Long.class, "Tester Park", "Location", LocalDate.parse("2018-01-01"), 100, 200, "Description");
		
	}

	@After
	public void tearDown() throws Exception {
		dataSource.getConnection().rollback();
	}

	@Test
	public void testAllParks() {
		List<Park> parkInfo = testPark.getAllParks();
		for(Park p : parkInfo) {
			if(p.getId().equals(newParkId)) {
				assertEquals(newParkId, p.getId());
				assertEquals("Tester Park", p.getName());
				return;
			}
		}
		fail("Test Park was not found");
	}

}
