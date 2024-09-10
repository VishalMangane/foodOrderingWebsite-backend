package com.example.bitemeals.repository;

import com.example.bitemeals.model.Hotel;
import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HotelRepository extends MongoRepository<Hotel, String> {

    @NonNull List<Hotel> findAll();

    List<Hotel> findByCityName(String cityName);

    Optional<Hotel> findByNameAndCityId(@NonNull String name, @NonNull String cityId);

    List<Hotel> findByCityId(String cityId);
}
