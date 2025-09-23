package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dto.BikeDto;
import pl.kurs.entity.Bike;
import pl.kurs.mapper.BikeMapper;
import pl.kurs.service.BikeService;

import java.util.List;

@RestController
@RequestMapping("/bikes")
@RequiredArgsConstructor
public class BikeController {
    private final BikeService bikeService;
    private final BikeMapper bikeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BikeDto createBike(@RequestBody BikeDto bikeDto) {
        Bike bike = bikeMapper.dtoToEntity(bikeDto);
        Bike saved = bikeService.save(bike);
        return bikeMapper.entityToDto(saved);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BikeDto> getById(@PathVariable("id") Long id) {
        Bike bike = bikeService.getBikeById(id);
        return ResponseEntity.ok(bikeMapper.entityToDto(bike));
    }

    @GetMapping
    public ResponseEntity<List<BikeDto>> getAll() {
        List<Bike> bikes = bikeService.getAll();
        return ResponseEntity.ok(bikeMapper.entitiesToDtos(bikes));
    }

    @PutMapping
    public BikeDto updateBike(@RequestBody BikeDto bikeDto) {
        Bike bike = bikeMapper.dtoToEntity(bikeDto);
        Bike updatedBike = bikeService.updateBike(bike);
        return bikeMapper.entityToDto(updatedBike);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        bikeService.deleteById(id);
    }

}
