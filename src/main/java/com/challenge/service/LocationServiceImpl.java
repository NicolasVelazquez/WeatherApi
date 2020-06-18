package com.challenge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.challenge.domain.Location;
import com.challenge.domain.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService {

	private LocationRepository repository;
	
	public LocationServiceImpl(LocationRepository repository) {
		this.repository = repository;
	}

	public List<Location> findAll() {
		return repository.findAll();
	}

}
