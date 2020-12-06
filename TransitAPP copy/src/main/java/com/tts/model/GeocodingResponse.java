package com.tts.model;

import java.util.List;

public class GeocodingResponse {
	public static List<Geocoding> results11 = null;
	public List<Geocoding> results1;
	public Object results;

	public List<Geocoding> getResults() {
		return results11;
	}

	public void setResults(List<Geocoding> results) {
		GeocodingResponse.results11 = results;
	}

	
}
