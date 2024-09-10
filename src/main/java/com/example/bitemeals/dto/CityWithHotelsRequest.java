package com.example.bitemeals.dto;

import com.example.bitemeals.model.Hotel;
import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CityWithHotelsRequest {
    private String cityName;
    private List<Hotel> hotels;

}

