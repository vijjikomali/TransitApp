package com.tts.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.tts.model.BusRequest;
import com.tts.service.Bus;

@Controller
public class TransitService<TransitServices> {
	
	@SuppressWarnings("unused")
	@Autowired
	private TransitServices apiService;

	public List<Bus> getNearbyBuses(BusRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
