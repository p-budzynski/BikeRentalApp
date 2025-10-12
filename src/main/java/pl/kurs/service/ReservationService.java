package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.dto.ReservationDto;
import pl.kurs.dto.ReservationUpdateDatesDto;
import pl.kurs.entity.Bike;
import pl.kurs.entity.Client;
import pl.kurs.entity.Reservation;
import pl.kurs.entity.Status;
import pl.kurs.exception.DataNotFoundException;
import pl.kurs.exception.OverlappingReservationException;
import pl.kurs.mapper.ReservationMapper;
import pl.kurs.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final BikeService bikeService;
    private final ClientService clientService;
    private final ReservationMapper reservationMapper;
    private final BikeRentalService bikeRentalService;

    @Transactional
    public Reservation createReservation(ReservationDto reservationDto) {
        Bike bike = bikeService.getBikeByIdForUpdate(reservationDto.getBikeId());
        throwExceptionIfOverlappingReservation(reservationDto, bike);
        Client client = clientService.getClientById(reservationDto.getClientId());
        Reservation reservation = reservationMapper.dtoToEntity(reservationDto);
        reservation.setBike(bike);
        reservation.setClient(client);
        reservation.setStatus(Status.RESERVED);
        Reservation createdReservation = reservationRepository.save(reservation);
        bikeRentalService.sendReservationConfirmation(client, bike, createdReservation);
        return createdReservation;
    }

    public Reservation findReservationById(Long id) {
        return reservationRepository.findById(id).
                orElseThrow(() -> new DataNotFoundException(STR."Reservation not found with id: \{id}"));
    }

    @Transactional
    public Reservation cancelReservationById(Long id) {
        Reservation reservation = getReservationByIdForUpdate(id);
        reservation.setStatus(Status.CANCELED);
        Reservation canceledReservation = reservationRepository.save(reservation);
        bikeRentalService.sendReservationCancellation(canceledReservation.getClient(), canceledReservation.getBike(), canceledReservation);
        return canceledReservation;
    }

    public Reservation getReservationByIdForUpdate(Long id) {
        return reservationRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new DataNotFoundException(STR."Reservation not found with id: \{id}"));
    }

    @Transactional
    public Reservation updateReservationDatesById(Long id, ReservationUpdateDatesDto updateDto) {
        Reservation reservation = getReservationByIdForUpdate(id);
        reservation.setStartDate(updateDto.getStartDate());
        reservation.setEndDate(updateDto.getEndDate());
        return reservationRepository.save(reservation);
    }

    private void throwExceptionIfOverlappingReservation(ReservationDto reservationDto, Bike bike) {
        boolean existsOverlappingReservation = reservationRepository.existsOverlappingReservation(
                bike.getId(), reservationDto.getStartDate(), reservationDto.getEndDate());
        if (existsOverlappingReservation) {
            throw new OverlappingReservationException(STR."There is overlapping reservation for bike id: \{bike.getId()}");
        }
    }

}
