package pl.kurs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDataDto {
    private Long id;
    private String street;
    private String city;
    private String zipCode;
    private String phoneNumber;
    private String pesel;
}
