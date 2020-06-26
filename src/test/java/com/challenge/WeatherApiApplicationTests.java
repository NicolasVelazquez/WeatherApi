package com.challenge;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.producer.Producer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.challenge.domain.Location;
import com.challenge.domain.LocationRepository;
import com.challenge.service.LocationService;
import com.challenge.service.LocationServiceImpl;
import com.challenge.service.PublishService;
import com.challenge.service.PublishServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherApiApplicationTests {

	@Mock
	private LocationRepository repository;
	
	private LocationService locationService;
	
	@Autowired
	private PublishService publishService;	
	
    @Before
    public void setUp() {
    	locationService = new LocationServiceImpl(repository);
    	
    	when(repository.findAll()).thenReturn(Arrays.asList(
				new Location(1L),
				new Location(2L),
				new Location(3L)));
    }

//	@Test
//	public void getLocations_shouldRetrieveLocations() {
//	
//		List<Location> locations = locationService.findAll();
//		assertEquals(false, locations.isEmpty());
//	}
	
	@Test
	public void getLocationsAndPublish() {
		//arrange
		
		//act
		List<Location> locations = locationService.findAll();
		boolean published = publishService.publishLocations(locations);
		
		//assert
		assertTrue(published);
	}
	
}
