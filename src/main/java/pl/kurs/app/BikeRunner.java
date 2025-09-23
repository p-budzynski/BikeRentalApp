package pl.kurs.app;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.entity.Bike;
import pl.kurs.entity.BikeDetails;
import pl.kurs.repository.BikeRepository;

@Component
@RequiredArgsConstructor
public class BikeRunner {
    private final BikeRepository bikeRepository;

    @Transactional
    public void run() {
        BikeDetails bikeDetails1 = new BikeDetails("123456");
        Bike bike1 = new Bike("Trek", "Sport", bikeDetails1);
        bikeRepository.save(bike1);

        BikeDetails bikeDetails2 = new BikeDetails("654321");
        Bike bike2 = new Bike("Specialized", "Mountain", bikeDetails2);
        bikeRepository.save(bike2);

        BikeDetails bikeDetails3 = new BikeDetails("112233");
        Bike bike3 = new Bike("Giant", "Road", bikeDetails3);
        bikeRepository.save(bike3);

        BikeDetails bikeDetails4 = new BikeDetails("998877");
        Bike bike4 = new Bike("Cannondale", "Hybrid", bikeDetails4);
        bikeRepository.save(bike4);

        BikeDetails bikeDetails5 = new BikeDetails("445566");
        Bike bike5 = new Bike("Cube", "Electric", bikeDetails5);
        bikeRepository.save(bike5);
    }

    @Transactional
    public void check() {
        bikeRepository.findById(1L).ifPresent(System.out::println);
    }
}
