package com.techelevator.npgeek;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcSurveyDao implements SurveyDao{
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public JdbcSurveyDao(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	@Override
	public List<Survey> getAllSurveys() {
		List<Survey> allSurveys = new ArrayList<>();
		String sqlSelectAllSurveys = "SELECT * FROM survey_result";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSelectAllSurveys);
		while (results.next()) {
			allSurveys.add(mapRowToProduct(results));
		}
		return allSurveys;

	}
	private Survey mapRowToProduct(SqlRowSet results) {
		Survey survey = new Survey();
		survey.setSurveyId(results.getLong("surveyid"));
		survey.setParkCode(results.getString("parkcode"));
		survey.setEmailAddress(results.getString("emailaddress"));
		survey.setState(results.getString("state"));
		survey.setActivityLvl(results.getString("activitylevel"));
		return survey;
	}
	
	@Override
	public void save(Survey post) {
		String sqlInsertSurvey= "INSERT INTO survey_result( parkcode, emailaddress, state, activitylevel) VALUES (?,?,?,?)";
		jdbcTemplate.update(sqlInsertSurvey, post.getParkCode(), post.getEmailAddress(), post.getState(), post.getActivityLvl());
	}
	
	public List <Park> getFavoriteSurvey() {
		List<Park> favSurveys = new ArrayList<>();
		String sqlFavoriteSurvey="SELECT COUNT(*) votecount, parkname, park.parkcode, park.state, park.annualvisitorcount, park.milesoftrail "
				+ "FROM survey_result "
				+ "JOIN park ON park.parkcode = survey_result.parkcode "
				+ "GROUP BY parkname, park.parkcode ORDER BY votecount DESC, parkname ASC";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlFavoriteSurvey);
		
		while (results.next()) {
			Park newPark = new Park();
			newPark.setVoteCount(results.getInt("votecount"));
			newPark.setParkCode(results.getString("parkcode"));
			newPark.setParkName(results.getString("parkname"));
			newPark.setState(results.getString("state"));
			newPark.setAnnualVisCount(results.getInt("annualvisitorcount"));
			newPark.setMilesOfTrail(results.getDouble("milesoftrail"));
			newPark.setParkImg("/img/parks/"+ results.getString("parkcode").toLowerCase()+".jpg");
			favSurveys.add(newPark);
		}
		return favSurveys;
	}

}
