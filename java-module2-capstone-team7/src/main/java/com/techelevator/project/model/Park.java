package com.techelevator.project.model;

import java.time.LocalDate;
//import java.util.Date;

public class Park {
	
	private Long id;
	private String name;
	private String location;
	private LocalDate establishDate;
	private Integer area;
	private Integer visitors;
	private String description;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public LocalDate getEstablishDate() {
		return establishDate;
	}

	public void setEstablishDate(LocalDate establishDate) {
		this.establishDate = establishDate;
	}

	public Integer getArea() {
		return area;
	}

	public void setArea(Integer area) {
		this.area = area;
	}

	public Integer getVisitors() {
		return visitors;
	}

	public void setVisitors(Integer visitors) {
		this.visitors = visitors;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		StringBuilder sb = new StringBuilder(description);

	    int i = 0;
	    while ((i = sb.indexOf(" ", i + 100)) != -1) {
	        sb.replace(i, i + 1, "\n");
	    }
		this.description = sb.toString();
	}
	
	public String toString() {
		return name;
	}
}
