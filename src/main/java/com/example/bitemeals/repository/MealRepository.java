package com.example.bitemeals.repository;

import com.example.bitemeals.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends MongoRepository<Meal, String> {
    List<Meal> findByHotelName(String hotelName);

    List<Meal> findByHotelNameAndCityName(String hotelId, String cityName);

    void deleteByHotelName(String hotelName);

    @Override
    void delete(Meal entity);

    @Override
    void deleteAll(Iterable<? extends Meal> entities);

    @Override
    void deleteAllById(Iterable<? extends String> strings);
}
