package com.techelevator.npgeek;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class SurveyController {

	
	@Autowired
	SurveyDao surveyDao;
	
	@RequestMapping(path="/survey", method=RequestMethod.GET)
	public ModelAndView displayForum() {
		return new ModelAndView("survey").addObject("survey", surveyDao.getAllSurveys());
	}	
	
	@RequestMapping(path="/survey", method=RequestMethod.POST)
	public String outputPost(@RequestParam String parkCode, @RequestParam String email, @RequestParam String state
			, @RequestParam String activityLvl, RedirectAttributes flashScope) {
		Survey post = new Survey(); 
		post.setParkCode(parkCode);
		post.setEmailAddress(email);
		post.setState(state);
		post.setActivityLvl(activityLvl);
		surveyDao.save(post);
		
		flashScope.addFlashAttribute("message", "Park Ranking's According to Community");
		return "redirect:/surveyResults";
	}
	@RequestMapping(path="/surveyResults", method=RequestMethod.GET)
	public ModelAndView surveyResults() {
		return new ModelAndView("surveyResults").addObject("survey", surveyDao.getFavoriteSurvey());
	}
}
