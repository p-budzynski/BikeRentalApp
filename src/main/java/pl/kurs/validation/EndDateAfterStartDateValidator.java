package pl.kurs.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class EndDateAfterStartDateValidator implements ConstraintValidator<EndDateAfterStartDate, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;

        try {
            LocalDate startDate = (LocalDate) value.getClass().getMethod("getStartDate").invoke(value);
            LocalDate endDate = (LocalDate) value.getClass().getMethod("getEndDate").invoke(value);

            if (startDate == null || endDate == null) return true;

            return endDate.isAfter(startDate);
        } catch (Exception e) {

            return false;
        }
    }
}