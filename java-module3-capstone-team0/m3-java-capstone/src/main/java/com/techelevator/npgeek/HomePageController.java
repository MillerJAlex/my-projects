package com.techelevator.npgeek;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomePageController {

	@Autowired
	ParkDao parkDao;

	@Autowired
	WeatherDao weatherDao;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public ModelAndView displayPage() {
		return new ModelAndView("homePage").addObject("parks", parkDao.getAllParks());
	}

	@RequestMapping(path = "/parkDetail", method = RequestMethod.GET)
	public String displayParkDetails(ModelMap modelHolder, @RequestParam String parkCode, HttpServletResponse response)
			throws IOException {
		try {
			modelHolder.put("park", parkDao.getParkByParkCode(parkCode));
			modelHolder.put("weather", weatherDao.getForeCast(parkCode));
			return "parkDetail";
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}
	}

	@RequestMapping(path = "/parkDetail", method = RequestMethod.POST)
	public String updateTempChoice(@RequestParam String parkCode, @RequestParam boolean tempChoice,
			ModelMap modelHolder, HttpSession session) {
		Boolean fahrenheit = true;

		if (tempChoice) {
			fahrenheit = false;
		} else {
			fahrenheit = true;
		}

		modelHolder.put("parkCode", parkCode);
		session.setAttribute("isFahrenheit", fahrenheit);

		return "redirect:/parkDetail#jump";
	}
}
