package com.challenge.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Location {
	
	@Id
	private Long locationId;
	
	public Location(Long locationId) {
		this.locationId = locationId;
	}

}
