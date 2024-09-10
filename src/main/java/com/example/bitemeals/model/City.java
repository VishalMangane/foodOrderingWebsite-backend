package com.example.bitemeals.model;

import com.example.bitemeals.dto.HotelDetail;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cities")
public class City {
    private String id;
    private String cityName;
    private List<Hotel> hotels;
    private List<HotelDetail> hotelDetails;

    public City (String id,String cityName,List<HotelDetail> hotelDetails){
        this.id = id;
        this.cityName= cityName;
        this.hotelDetails = hotelDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<Hotel> getHotels() {
        return hotels;
    }

    public void setHotels(List<Hotel> hotels) {
        this.hotels = hotels;
    }

    public List<HotelDetail> getHotelDetails() {
        return hotelDetails;
    }

    public void setHotelDetails(List<HotelDetail> hotelDetails) {
        this.hotelDetails = hotelDetails;
    }

}
