package pl.kurs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.kurs.validation.Create;
import pl.kurs.validation.EndDateAfterStartDate;
import pl.kurs.validation.Update;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EndDateAfterStartDate
public class ReservationUpdateDatesDto {

    @NotNull(message = "Start date is required", groups = Update.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate startDate;

    @NotNull(message = "End date is required", groups = Update.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate endDate;
}
