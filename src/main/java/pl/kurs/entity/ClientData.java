package pl.kurs.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql = "UPDATE client_data SET deleted = true WHERE id = ?")
@Table(name = "client_data")
@NoArgsConstructor
@Getter
@Setter
public class ClientData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String city;

    @Column(name = "zip_code", nullable = false)
    private String zipCode;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false, unique = true)
    private String pesel;

    @Column(nullable = false)
    private Boolean deleted = false;

    @OneToOne(mappedBy = "clientData")
    private Client client;

    public ClientData(String street, String city, String zipCode, String phoneNumber, String pesel) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
    }

    @Override
    public String toString() {
        return STR."ClientData{id=\{id}, street='\{street}\{'\''}, city='\{city}\{'\''}, zipCode='\{zipCode}\{'\''}, phoneNumber='\{phoneNumber}\{'\''}, pesel='\{pesel}\{'\''}\{'}'}";
    }
}
