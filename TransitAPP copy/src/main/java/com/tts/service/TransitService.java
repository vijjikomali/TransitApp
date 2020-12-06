package com.tts.service;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.sun.jdi.Location;
import com.tts.model.BusComparator;
import com.tts.model.BusRequest;
import com.tts.model.GeocodingResponse;

@Service 
public class TransitService {
	@Value("${transit_url}") 
	public String transitUrl;
	
	@Value("${geocoding_url}")
	public String geocodingURL;
	
	@Value("${distance_url}")
	public String distanceUrl;
	
	@Value("${google_api_key}")
	public String googleAPiKey;
	
	public TransitService() {
		
	}
	
	private List<Bus> getBuses(){
	    RestTemplate restTemplate = new RestTemplate();
	    Bus[] buses = restTemplate.getForObject(transitUrl, Bus[].class);
	    return Arrays.asList(buses);
	}
	
	@SuppressWarnings("unused")
	private Location getCoordinates(String description) {
	    description = description.replace(" ", "+");
	    String url = geocodingURL + description + "+GA&key=" + googleAPiKey;
	    RestTemplate restTemplate = new RestTemplate();
	    GeocodingResponse response = restTemplate.getForObject(url, GeocodingResponse.class);
	    return (Location) response.results.get(0).geometry.location;
	}
	
	private double getDistance(Location origin, Location destination) {
	    String url = distanceUrl + "origins=" + origin.lat + "," + origin.lng 
	    + "&destinations=" + destination.lat + "," + destination.lng + "&key=" + googleAPiKey;
	    RestTemplate restTemplate = new RestTemplate();
	    DistanceResponse response = restTemplate.getForObject(url, DistanceResponse.class);
	    return ((List<Bus>) ((List<Bus>) response.rows).get(0).elements).get(0).distance.Value * 0.000621371;
	}
	public List<Bus> getNearbyBuses(BusRequest request){
		  List<Bus> allBuses = this.getBuses();
		  Location personLocation = this.getCoordinates(request.address + " " + request.city);
		  List<Bus> nearbyBuses = new ArrayList<>();
		  for(Bus bus : allBuses) {
		   Location busLocation = new Location();
		   busLocation.lat = bus.LATITUDE;
		   busLocation.lng = bus.LONGITUDE;
		   double latDistance = Double.parseDouble(busLocation.lat) - Double.parseDouble(personLocation.lat);
		   double lngDistance = Double.parseDouble(busLocation.lng) - Double.parseDouble(personLocation.lng);
		     if (Math.abs(latDistance) <= 0.02 && Math.abs(lngDistance) <= 0.02) {
		         double distance = getDistance(busLocation, personLocation);
		         if (distance <= 1) {
		             bus.distance = (double) Math.round(distance * 100) / 100;
		             nearbyBuses.add(bus);
		           }
		       }
		    }
		    Collections.sort(nearbyBuses, new BusComparator());
		    return nearbyBuses;
		}
}
