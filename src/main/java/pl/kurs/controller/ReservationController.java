package pl.kurs.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dto.ReservationDto;
import pl.kurs.dto.ReservationUpdateDatesDto;
import pl.kurs.entity.Reservation;
import pl.kurs.mapper.ReservationMapper;
import pl.kurs.service.ReservationService;
import pl.kurs.validation.Create;
import pl.kurs.validation.Update;

@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ReservationDto createReservation(@RequestBody @Validated ReservationDto reservationDto) {
        Reservation reservation = reservationService.createReservation(reservationDto);
        return reservationMapper.entityToDto(reservation);
    }

    @GetMapping("/{id}")
    public ReservationDto getReservationById(@PathVariable @Min(value = 1, message = "ID must be greater than zero!") Long id) {
        Reservation reservation = reservationService.findReservationById(id);
        return reservationMapper.entityToDto(reservation);
    }

    @PatchMapping("/{id}/dates")
    public ResponseEntity<ReservationDto> updateReservationDatesById(
            @PathVariable @Min(value = 1, message = "ID must be greater than zero!") Long id,
            @RequestBody @Validated ReservationUpdateDatesDto updateDto) {
        Reservation reservation = reservationService.updateReservationDatesById(id, updateDto);
        return ResponseEntity.ok(reservationMapper.entityToDto(reservation));
    }

    @PutMapping("/cancellations/{id}")
    public ResponseEntity<ReservationDto> cancelReservationById(@PathVariable @Min(value = 1, message = "ID must be greater than zero!") Long id) {
        Reservation reservation = reservationService.cancelReservationById(id);
        return ResponseEntity.ok(reservationMapper.entityToDto(reservation));
    }

}
