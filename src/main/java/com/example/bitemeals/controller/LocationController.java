package com.example.bitemeals.controller;

import com.example.bitemeals.dto.CityWithHotelsRequest;
import com.example.bitemeals.dto.HotelDetail;
import com.example.bitemeals.model.City;
import com.example.bitemeals.model.Hotel;
import com.example.bitemeals.service.LocationService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/cities")
    public ResponseEntity<List<City>> getAllCities() {
        List<City> cities = locationService.getAllCities();
        log.info("fetched all cities");
        return ResponseEntity.ok(cities);
    }

    @GetMapping("/cities-suggested")
    public ResponseEntity<List<String>> getCitySuggestions(@RequestParam String query) {
        List<String> cities = locationService.getCitySuggestions(query);
        return ResponseEntity.ok(cities);
    }


    @GetMapping("/hotels/{cityName}")
    public ResponseEntity<?> getHotelsWithMeals(@PathVariable String cityName) {
        return locationService.getHotelsWithMealsByCity(cityName);
    }

    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = locationService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }

    @PostMapping("/addCityWithHotels")
    public ResponseEntity<?> addCityWithHotels(@RequestBody CityWithHotelsRequest request) {
        log.info("Received request: {}", request);
        City cityDetails = locationService.addCityWithHotels(request.getCityName(), request.getHotels());

        // Handle potential null values to avoid NullPointerException
        String cityName = cityDetails.getCityName() != null ? cityDetails.getCityName() : "Unknown";

        List<HotelDetail> hotelDetails = request.getHotels().stream()
                .map(hotel -> new HotelDetail(
                        hotel.getName(),
                        hotel.getAddress(),
                        hotel.getId()
                ))
                .toList();

        return ResponseEntity.ok().body(Map.of(
                "message","City and hotels updated successfully",
                "cityName", cityName,
                "hotels", hotelDetails
        ));
    }

}
