package com.example.bitemeals.service;

import com.example.bitemeals.model.Meal;
import com.example.bitemeals.repository.MealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MealService {

    @Autowired
    private MealRepository mealRepository;


    public List<Meal> getMealsByHotel(String hotelName) {
        log.info("fetching meals by hotelName {}",hotelName);
        return mealRepository.findByHotelName(hotelName);
    }

    public List<Meal> getMealsByHotelNameAndCityName(String hotelName,String cityName){
        log.info("fetching meals by hotelName {} and cityName {} ",hotelName,cityName);
        return mealRepository.findByHotelNameAndCityName(hotelName,cityName);
    }

    public void addMealsToHotel(String hotelName, List<Meal> meals) {
        log.info("Adding meals to hotel: {}", hotelName);
        meals.forEach(meal -> meal.setHotelName(hotelName));
        mealRepository.saveAll(meals);
        log.info("Meals added to hotel '{}'", hotelName);
    }

    public void deleteMealById(String id) {
        log.info("Deleting meal with id: {}", id);
        mealRepository.deleteById(id);
    }

    public void deleteMealsByHotelName(String hotelName) {
        log.info("Deleting meals for hotelName: {}", hotelName);
        mealRepository.deleteByHotelName(hotelName);
    }
}
