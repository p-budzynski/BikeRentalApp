package pl.kurs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @NotNull(message = "ID is required", groups = Delete.class)
    @Min(value = 1, message = "ID must be at least 1")
    private Long id;

    @NotNull(message = "Bike ID is required")
    @Min(value = 1, message = "Bike ID must be at least 1")
    private Long bikeId;


    @NotNull(message = "Client ID is required")
    @Min(value = 1, message = "Client ID must be at least 1")
    private Long clientId;

    @NotNull(message = "Start date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;

    private String statusName;

    public ReservationDto(Long bikeId, Long clientId, LocalDate startDate, LocalDate endDate, String statusName) {
        this.bikeId = bikeId;
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.statusName = statusName;
    }
}
