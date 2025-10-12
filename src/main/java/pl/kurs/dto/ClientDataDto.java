package pl.kurs.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.validation.Update;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClientDataDto {

    @NotNull(message = "ID is required", groups = Update.class)
    @Min(value = 1, message = "ID must be at least 1", groups = Update.class)
    private Long id;

    @NotBlank(message = "Street must not be blank")
    private String street;

    @NotBlank(message = "City must not be blank")
    private String city;

    @NotBlank(message = "Zip code must not be blank")
    @Pattern(regexp = "^\\d{2}-\\d{3}$", message = "The postal code must be in the format XX-XXX")
    private String zipCode;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^\\d{9}$", message = "The phone number must have 9 digits")
    private String phoneNumber;

    @NotBlank(message = "PESEL number must not be blank")
    @Pattern(regexp = "\\d{11}", message = "PESEL must have 11 digits")
    private String pesel;

    @NotBlank(message = "E-mail must not be blank")
    @Email
    private String mail;

    public ClientDataDto(String street, String city, String zipCode, String phoneNumber, String pesel, String mail) {
        this.street = street;
        this.city = city;
        this.zipCode = zipCode;
        this.phoneNumber = phoneNumber;
        this.pesel = pesel;
        this.mail = mail;
    }
}
