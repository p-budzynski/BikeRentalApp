package pl.kurs.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class ClientData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;

    private String city;

    private String zipCode;

    private String phoneNumber;

    private String pesel;

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
        return "ClientData{" +
               "id=" + id +
               ", street='" + street + '\'' +
               ", city='" + city + '\'' +
               ", zipCode='" + zipCode + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", pesel='" + pesel + '\'' +
               '}';
    }
}
