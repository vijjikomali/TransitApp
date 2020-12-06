package com.tts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;

import com.tts.model.BusRequest;
import com.tts.service.TransitService;
import com.tts.service.Bus;


@Controller
public class TransitController {
    @Autowired
    private TransitService apiService;
	
    @GetMapping("/buses")
    public String getBusesPage(Model model){
        model.addAttribute("request", new BusRequest());
        return "index";
    }
	
    @SuppressWarnings("unchecked")
	@PostMapping("/buses")
    
    public String getNearbyBuses(BusRequest request, Model model) {
        List<Bus> buses = apiService.getNearbyBuses(request);
        model.addAttribute("buses", buses);
        model.addAttribute("request", request);    
        return "index";
    }
}