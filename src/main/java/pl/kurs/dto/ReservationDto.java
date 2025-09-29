package pl.kurs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.validation.Create;
import pl.kurs.validation.Delete;
import pl.kurs.validation.EndDateAfterStartDate;
import pl.kurs.validation.Update;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EndDateAfterStartDate
public class ReservationDto {
    @NotNull(message = "ID is required")
    @Min(value = 1, message = "ID must be at least 1")
    private Long id;

    @NotNull(message = "Bike ID is required", groups = Create.class)
    @Min(value = 1, message = "Bike ID must be at least 1", groups = Create.class)
    private Long bikeId;


    @NotNull(message = "Client ID is required", groups = Create.class)
    @Min(value = 1, message = "Client ID must be at least 1", groups = Create.class)
    private Long clientId;

    @NotNull(message = "Start date is required", groups = Create.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @NotNull(message = "End date is required", groups = Create.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    @NotBlank(message = "Status name is required")
    private String statusName;
}
