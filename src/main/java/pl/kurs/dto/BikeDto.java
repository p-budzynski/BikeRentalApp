package pl.kurs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BikeDto {
    private Long id;
    private String brand;
    private String model;
    private BikeDetailsDto bikeDetails;

}
