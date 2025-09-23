package pl.kurs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String model;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bike_details_id")
    private BikeDetails bikeDetails;

    public Bike(String brand, String model, BikeDetails bikeDetails) {
        this.brand = brand;
        this.model = model;
        this.bikeDetails = bikeDetails;
    }
}
