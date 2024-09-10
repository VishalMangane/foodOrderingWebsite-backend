package com.example.bitemeals;

import com.example.bitemeals.model.City;
import com.example.bitemeals.model.Hotel;
import com.example.bitemeals.model.Meal;
import com.example.bitemeals.repository.CityRepository;
import com.example.bitemeals.repository.HotelRepository;
import com.example.bitemeals.repository.MealRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Profile("meals")
public class MealLoader implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private MealRepository mealRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Meal> mealsToAdd = List.of(
                new Meal(null, "Butter Chicken", "Spicy chicken in buttery gravy", 1.99),
                new Meal(null, "Paneer Tikka", "Grilled paneer cubes marinated in spices", 1.59),
                new Meal(null, "Dal Makhani", "Lentils cooked in a creamy sauce", 2.19),
                new Meal(null, "Aloo parotaa", "stuffed with potato and perfectly cooked", 0.49),
                new Meal(null, "Tandoori Roti", "crispy baked bread", 0.49),
                new Meal(null, "Butter Naan", "perfectly baked bread with butter on it", 0.49)
        );

        // Retrieve all cities
        List<City> cities = cityRepository.findAll();

        for (City city : cities) {
            List<Hotel> hotels = hotelRepository.findByCityName(city.getCityName());

            for (Hotel hotel : hotels) {
                // Add meals to the hotel
                List<Meal> hotelMeals = mealsToAdd.stream()
                        .map(meal -> new Meal(null, meal.getName(), meal.getDescription(), meal.getPrice(), hotel.getName(), city.getCityName()))
                        .toList();

                // Save meals to the repository
                mealRepository.saveAll(hotelMeals);
                log.info("Meals added to hotel '{}'", hotel.getName());
            }
        }
    }
}
