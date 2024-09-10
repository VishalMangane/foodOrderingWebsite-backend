package com.example.bitemeals.controller;

import com.example.bitemeals.model.Meal;
import com.example.bitemeals.service.MealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/meals")
public class MealController {

    @Autowired
    private MealService mealService;

    @GetMapping("/hotel/{hotelName}")
    public List<Meal> getHotelMeals(@PathVariable String hotelName) {
        return mealService.getMealsByHotel(hotelName);
    }

    @GetMapping("/hotel")
    public List<Meal> getMealsByHotelNameAndCityName(@RequestParam String hotelName, @RequestParam String cityName) {
        return mealService.getMealsByHotelNameAndCityName(hotelName, cityName);
    }

    @PostMapping("/hotel/{hotelName}")
    public ResponseEntity<String> addMealsToHotel(@PathVariable String hotelName, @RequestBody List<Meal> meals) {
        mealService.addMealsToHotel(hotelName, meals);
        return ResponseEntity.ok("Meals added successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMealById(@PathVariable String id) {
        mealService.deleteMealById(id);
        return ResponseEntity.ok("Meal deleted successfully");
    }

    @DeleteMapping("/hotel/{hotelName}")
    public ResponseEntity<String> deleteMealsByHotelName(@PathVariable String hotelName) {
        mealService.deleteMealsByHotelName(hotelName);
        return ResponseEntity.ok("Meals deleted successfully for hotelName: " + hotelName);
    }
}
