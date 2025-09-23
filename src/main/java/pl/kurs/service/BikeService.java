package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.entity.Bike;
import pl.kurs.exception.DataNotFoundException;
import pl.kurs.repository.BikeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BikeService {
    private final BikeRepository bikeRepository;

    @Transactional
    public Bike save(Bike bike) {
        return bikeRepository.save(bike);
    }

    public Bike getBikeById(Long id) {
        return bikeRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Bike not found with id: " + id));
    }

    public void deleteById(Long id) {
        bikeRepository.deleteById(id);
    }

    @Transactional
    public Bike updateBike(Bike bike) {
        Bike bikeToUpdate = bikeRepository.findById(bike.getId())
                .orElseThrow(() -> new DataNotFoundException("Bike with id: " + bike.getId() + " not found"));
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
}
