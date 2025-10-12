package pl.kurs.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.validation.Create;
import pl.kurs.validation.Update;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BikeDetailsDto {
    @NotNull(message = "ID is required", groups = Update.class)
    @Min(value = 1, message = "ID must be at least 1", groups = Update.class)
    private Long id;

    @NotBlank(message = "Vin number must not be blank", groups = {Create.class, Update.class})
    @Pattern(regexp = "^[A-Z0-9]{8}$", message = "The VIN number must consist of 8 characters: letters and numbers", groups = {Create.class, Update.class})
    private String vinNumber;

    public BikeDetailsDto(String vinNumber) {
        this.vinNumber = vinNumber;
    }
}
