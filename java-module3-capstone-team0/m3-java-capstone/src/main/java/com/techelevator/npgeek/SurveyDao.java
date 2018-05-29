package com.techelevator.npgeek;

import java.util.List;
import java.util.Map;

public interface SurveyDao {

	public Object getAllSurveys();
	public void save(Survey post);
	public List<Park> getFavoriteSurvey();
}
