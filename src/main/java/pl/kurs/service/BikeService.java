package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.entity.Bike;
import pl.kurs.exception.DataNotFoundException;
import pl.kurs.exception.EntityCannotBeDeleteException;
import pl.kurs.repository.BikeRepository;
import pl.kurs.repository.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {
    private final BikeRepository bikeRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Bike save(Bike bike) {
        return bikeRepository.save(bike);
    }

    public Bike getBikeById(Long id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(STR."Bike not found with id: \{id}"));
    }

    @Transactional
    public void deleteById(Long id) {
        if (bikeRepository.existsById(id)) {
            if (reservationRepository.existsByBikeId(id)) {
             throw new EntityCannotBeDeleteException(STR."Cannot delete bike with id: \{id} - the bike has active reservations");
            }
            bikeRepository.deleteById(id);
        }
    }

    @Transactional
    public Bike updateBike(Bike bike) {
        Bike bikeToUpdate = getBikeByIdForUpdate(bike.getId());
        BeanUtils.copyProperties(bike, bikeToUpdate);
        return bikeRepository.save(bikeToUpdate);
    }

    public List<Bike> getAll() {
        List<Bike> bikes = bikeRepository.findAll();
        if (bikes.isEmpty()) {
            throw new DataNotFoundException("No bikes in the database");
        }
        return bikes;
    }

    public Bike getBikeByIdForUpdate(Long id) {
        return bikeRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new DataNotFoundException(STR."Bike not found with id: \{id}"));
    }


}
