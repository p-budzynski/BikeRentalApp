package pl.kurs.validation;

import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.dto.ReservationDto;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class EndDateAfterStartDateValidatorTest {
    private final EndDateAfterStartDateValidator validator = new EndDateAfterStartDateValidator();

    @Mock
    private ConstraintValidatorContext context;

    @Test
    void shouldReturnTrueWhenReservationDtoIsNull() {
        //when
        boolean result = validator.isValid(null, context);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenStartDateIsNull() {
        //given
        ReservationDto reservationDto = new ReservationDto(1L, 1L, 1L,
                null, LocalDate.of(2024, 1, 15), "RESERVED");

        //when
        boolean result = validator.isValid(reservationDto, context);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenEndDateIsNull() {
        //given
        ReservationDto reservationDto = new ReservationDto(1L, 1L, 1L,
                LocalDate.of(2024, 1, 15), null, "RESERVED");

        //when
        boolean result = validator.isValid(reservationDto, context);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnTrueWhenEndDateIsAfterStartDate() {
        //given
        ReservationDto reservationDto = new ReservationDto(1L, 1L, 1L, LocalDate.of(2024, 1, 10),
                LocalDate.of(2024, 1, 15), "RESERVED");

        //when
        boolean result = validator.isValid(reservationDto, context);

        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenEndDateIsBeforeStartDate() {
        //given
        ReservationDto reservationDto = new ReservationDto(1L, 1L, 1L, LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 1, 10), "RESERVED");

        //when
        boolean result = validator.isValid(reservationDto, context);

        //then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenReflectionFails() {
        //given
        class InvalidObject {

        }

        Object value = new InvalidObject();

        //when
        boolean result = validator.isValid(value, null);

        //then
        assertFalse(result);
    }

}