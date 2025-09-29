package pl.kurs.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.entity.Bike;
import pl.kurs.entity.Client;
import pl.kurs.entity.Reservation;
import pl.kurs.entity.Status;
import pl.kurs.repository.BikeRepository;
import pl.kurs.repository.ClientRepository;
import pl.kurs.repository.ReservationRepository;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ReservationRunner {
    private final ReservationRepository reservationRepository;
    private final BikeRepository bikeRepository;
    private final ClientRepository clientRepository;

    @Transactional
    public void run() {
        Bike bike = bikeRepository.findById(1L).orElseThrow();
        Client client = clientRepository.findById(1L).orElseThrow();
        Reservation reservation = new Reservation(bike, client, LocalDate.now(), LocalDate.now().plusDays(5), Status.RESERVED);
        reservationRepository.save(reservation);
    }
}
