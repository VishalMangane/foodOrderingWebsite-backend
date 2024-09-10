    package com.example.bitemeals.service;

    import com.example.bitemeals.dto.HotelDetail;
    import com.example.bitemeals.exception.CityNotFoundException;
    import com.example.bitemeals.model.City;
    import com.example.bitemeals.model.Hotel;
    import com.example.bitemeals.model.Meal;
    import com.example.bitemeals.repository.CityRepository;
    import com.example.bitemeals.repository.HotelRepository;
    import com.example.bitemeals.repository.MealRepository;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Service;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.Optional;
    import java.util.stream.Collectors;


    @Slf4j
    @Service
    public class LocationService {

        @Autowired
        private CityRepository cityRepository;

        @Autowired
        private HotelRepository hotelRepository;

        @Autowired
        private MealRepository mealRepository;

        public List<City> getAllCities() {
            return cityRepository.findAll();
        }

        public List<String> getCitySuggestions(String query) {
            List<City> cities = cityRepository.findAll();
            return cities.stream()
                    .map(City::getCityName)
                    .filter(cityName -> cityName.toLowerCase().startsWith(query.toLowerCase()))
                    .collect(Collectors.toList());
        }

        public List<Hotel> getAllHotels() {
            log.info("Fetching hotels and restaurants for cityName");
            return hotelRepository.findAll();
        }
        public ResponseEntity<?> getHotelsWithMealsByCity(String cityName) {
            log.info("Fetching hotels and restaurants for cityName: {}", cityName);
            String cityId = generateCityId(cityName);
            Optional<City> cityOptional = cityRepository.findById(cityId);
            try{
                if (cityOptional.isPresent()) {
                    List<Hotel> hotels = hotelRepository.findByCityId(cityId);
                    if (hotels != null && !hotels.isEmpty()) {
                        log.info("For each hotel, fetching the associated meals and setting them");
                        hotels.forEach(hotel -> {
                            List<Meal> meals = mealRepository.findByHotelName(hotel.getName());
                            hotel.setMeals(meals);
                        });
                        log.info("hotels for city {} -> {}",cityName,hotels);
                        return ResponseEntity.ok(hotels);
                    } else {
                        log.info("No hotels found in city {}", cityName);
                        return ResponseEntity.ok(new ArrayList<>());
                    }
                }
                else{
                    log.info("City {} not found", cityName);
                    return ResponseEntity.ok("city not found");
                }
            }catch(CityNotFoundException exception) {
                    log.info("City {} not found", cityName);
                    return ResponseEntity.ok(exception);
                }
        }

        public City addCityWithHotels(String newCityName, List<Hotel> hotelsToAdd) {
            String newCityId = generateCityId(newCityName);

            List<Hotel> savedHotels = new ArrayList<>();
            for (Hotel hotel : hotelsToAdd) {
                Hotel existingHotel = hotelRepository.findByNameAndCityId(hotel.getName(), newCityId).orElse(null);
                if (existingHotel != null) {
                    // Update existing hotel
                    existingHotel.setName(hotel.getName());
                    existingHotel.setAddress(hotel.getAddress());
                    existingHotel.setCityId(newCityId);
                    existingHotel.setCityName(newCityName);
                    existingHotel.setMeals(null);
                    hotelRepository.save(existingHotel);
                    log.info("Hotel '{}' updated.", existingHotel.getName());
                    savedHotels.add(existingHotel);
                } else {
                    // Save new hotel
                    Hotel newHotel = new Hotel(hotel);
                    newHotel.setCityName(newCityName);
                    newHotel.setCityId(newCityId);
                    newHotel.setMeals(null);
                    hotelRepository.save(newHotel);
                    log.info("Hotel '{}' added.", newHotel.getName());
                    savedHotels.add(newHotel);
                }
            }

            List<HotelDetail> hotelDetails = savedHotels.stream()
                    .map(hotel -> new HotelDetail(
                            hotel.getName(),
                            hotel.getAddress(),
                            hotel.getId()
                    ))
                    .collect(Collectors.toList());

            Optional<City> existingCityOpt = cityRepository.findByCityName(newCityName);
            City city;
            if (existingCityOpt.isPresent()) {
                city = existingCityOpt.get();

                // Update city with the list of hotels
                city.setHotelDetails(hotelDetails);
                cityRepository.save(city);
                log.info("city updated with new hotels.");
            } else {
                // Create a new city
                city = new City(newCityId, newCityName, null, hotelDetails);
                cityRepository.save(city);
                log.info("New city '{}' and hotels added.",newCityName);
            }

            log.info("City and hotels added or updated successfully");
            return city;
        }

        private String generateCityId(String cityName) {
            return cityName.substring(0, Math.min(cityName.length(), 3)).toUpperCase();
        }
    }
