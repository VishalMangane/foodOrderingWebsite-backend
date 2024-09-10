package com.example.bitemeals.repository;

import com.example.bitemeals.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends MongoRepository<City, String> {

    List<City> findAll();

    List<String> findByCityName();

    Optional<City> findByCityName(String cityName);

    Optional<City> findById(String id);

}

