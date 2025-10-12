package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.kurs.entity.Bike;
import pl.kurs.entity.Client;
import pl.kurs.entity.Reservation;
import pl.kurs.model.EmailNotification;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeRentalService {
    private final KafkaSender kafkaSender;

    public void sendReservationConfirmation(Client client, Bike bike, Reservation reservation) {
        EmailNotification emailNotification = EmailNotification.builder()
                .to(List.of(client.getClientData().getMail()))
                .subject("Confirmation of reservation creation")
                .body(String.format(
                        "Hello %s, \n\n" +
                        "We confirm the creation of the bike reservation.\n\n" +
                        "Reservation details:\n" +
                        "- Bike: %s %s\n" +
                        "- Start date: %s\n" +
                        "- End date: %s\n\n" +
                        "Thank you for using our services!\n" +
                        "Best regards,\n" +
                        "The BikeRental Team\n",
                        client.getFirstName(), bike.getModel(), bike.getBrand(), reservation.getStartDate(), reservation.getEndDate()
                ))
                .build();

        kafkaSender.sendEmailNotification(emailNotification);
    }

    public void sendReservationCancellation(Client client, Bike bike, Reservation reservation) {
        EmailNotification emailNotification = EmailNotification.builder()
                .to(List.of(client.getClientData().getMail()))
                .subject("Confirmation of cancellation of reservation")
                .body(String.format(
                        "Hello %s, \n\n" +
                        "We confirm the cancellation of your bike reservation.\n\n" +
                        "Details of the cancelled reservation:\n" +
                        "- Bike: %s %s\n" +
                        "- Start date: %s\n" +
                        "- End date: %s\n\n" +
                        "Best regards,\n" +
                        "The BikeRental Team\n",
                        client.getFirstName(), bike.getModel(), bike.getBrand(), reservation.getStartDate(), reservation.getEndDate()
                ))
                .build();

        kafkaSender.sendEmailNotification(emailNotification);
    }

}
