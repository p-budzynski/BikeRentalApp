package pl.kurs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql = "UPDATE bike_details SET deleted = true WHERE id = ?")
@Table(name = "bike_details")
@NoArgsConstructor
@Getter
@Setter
public class BikeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vin_number", length = 8, nullable = false, unique = true)
    private String vinNumber;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToOne(mappedBy = "bikeDetails")
    private Bike bike;

    public BikeDetails(String vinNumber) {
        this.vinNumber = vinNumber;
    }

    @Override
    public String toString() {
        return STR."BikeDetails{id=\{id}, vinNumber='\{vinNumber}\{'\''}\{'}'}";
    }
}
