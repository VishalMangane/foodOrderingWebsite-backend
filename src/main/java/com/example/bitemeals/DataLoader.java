package com.example.bitemeals;

import com.example.bitemeals.dto.HotelDetail;
import com.example.bitemeals.model.City;
import com.example.bitemeals.model.Hotel;
import com.example.bitemeals.repository.CityRepository;
import com.example.bitemeals.repository.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
@Profile("data")
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public void run(String... args) throws Exception {
        String newCityName = "Bengaluru";
        String newCityId = generateCityId(newCityName); // Generate ID based on city name

        List<Hotel> hotelsToAdd = List.of(
                new Hotel(null, "Food point", "mysore road", newCityName, newCityId,null),
                new Hotel(null, "Aadishakti restaurant", "5 th cross,cottenpete", newCityName, newCityId,null),
                new Hotel(null, "Satyanarayana hotel", "3rd cross , cottonpete", newCityName, newCityId,null),
                new Hotel(null, "Veerabhadra hotel", "circle majestic, near MRP bar", newCityName, newCityId,null)
        );

        // Save or update hotels
        List<Hotel> savedHotels = new ArrayList<>();
        for (Hotel hotel : hotelsToAdd) {
            Hotel existingHotel = hotelRepository.findByNameAndCityId(hotel.getName(), hotel.getCityId()).orElse(null);

            if (existingHotel != null) {
                // Update existing hotel
                existingHotel.setName(hotel.getName());
                existingHotel.setAddress(hotel.getAddress());
                existingHotel.setCityName(hotel.getCityName());
                existingHotel.setCityId(hotel.getCityId());
                hotelRepository.save(existingHotel);
                log.info("Hotel '{}' updated.", existingHotel.getName());
                savedHotels.add(existingHotel);
            } else {
                // Save new hotel
                Hotel savedHotel = hotelRepository.save(hotel);
                log.info("Hotel '{}' added.", savedHotel.getName());
                savedHotels.add(savedHotel);
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

        if (existingCityOpt.isPresent()) {
            City existingCity = existingCityOpt.get();

            // Update city with the list of hotels
            existingCity.setHotelDetails(hotelDetails);
            cityRepository.save(existingCity);
            log.info("Existing city updated with new hotels.");
        } else {
            // Create a new city
            City city = new City(newCityId, newCityName, null, hotelDetails);
            cityRepository.save(city);
            log.info("New city '{}' and hotels added.",newCityName);
        }
    }

    private String generateCityId(String cityName) {
        return cityName.substring(0, Math.min(cityName.length(), 3)).toUpperCase();
    }
}
