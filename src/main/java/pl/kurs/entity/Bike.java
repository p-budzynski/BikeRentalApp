package pl.kurs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql = "UPDATE bikes SET deleted = true WHERE id = ?")
@Table(name = "bikes")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Bike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bike_details_id")
    private BikeDetails bikeDetails;

    public Bike(String brand, String model, BikeDetails bikeDetails) {
        this.brand = brand;
        this.model = model;
        this.bikeDetails = bikeDetails;
    }
}
