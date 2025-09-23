package pl.kurs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class BikeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String vinNumber;

    @OneToOne(mappedBy = "bikeDetails")
    private Bike bike;

    public BikeDetails(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    @Override
    public String toString() {
        return "BikeDetails{" +
               "id=" + id +
               ", vinNumber='" + vinNumber + '\'' +
               '}';
    }
}
