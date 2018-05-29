package com.techelevator.npgeek;

public class Survey {
		
		private Long surveyId;
		private String parkCode;
		private String emailAddress;
		private String state;
		private String activityLvl;
		
		
		public Long getSurveyId() {
			return surveyId;
		}
		public void setSurveyId(Long surveyId) {
			this.surveyId = surveyId;
		}
		public String getParkCode() {
			return parkCode;
		}
		public void setParkCode(String parkCode) {
			this.parkCode = parkCode;
		} 
		public String getEmailAddress() {
			return emailAddress;
		}
		public void setEmailAddress(String emailAddress) {
			this.emailAddress = emailAddress;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getActivityLvl() {
			return activityLvl;
		}
		public void setActivityLvl(String activityLvl) {
			this.activityLvl = activityLvl;
		}
}
