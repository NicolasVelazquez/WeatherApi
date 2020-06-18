package com.challenge.service;

import java.util.List;

import com.challenge.domain.Location;

public interface PublishService {
	
	public boolean publishLocations(List<Location> locations);

}
