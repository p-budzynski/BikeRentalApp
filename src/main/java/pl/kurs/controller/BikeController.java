package pl.kurs.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dto.BikeDto;
import pl.kurs.entity.Bike;
import pl.kurs.mapper.BikeMapper;
import pl.kurs.service.BikeService;
import pl.kurs.validation.Create;
import pl.kurs.validation.Update;

import java.util.List;

@RestController
@RequestMapping("/bikes")
@RequiredArgsConstructor
public class BikeController {
    private final BikeService bikeService;
    private final BikeMapper bikeMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<BikeDto> createBike(@RequestBody @Validated(Create.class) BikeDto bikeDto) {
        Bike bike = bikeMapper.dtoToEntity(bikeDto);
        Bike saved = bikeService.save(bike);
        return ResponseEntity.ok(bikeMapper.entityToDto(saved));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BikeDto> getById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than zero!") Long id) {
        Bike bike = bikeService.getBikeById(id);
        return ResponseEntity.ok(bikeMapper.entityToDto(bike));
    }

    @GetMapping
    public ResponseEntity<List<BikeDto>> getAll() {
        List<Bike> bikes = bikeService.getAll();
        return ResponseEntity.ok(bikeMapper.entitiesToDtos(bikes));
    }

    @PutMapping
    public ResponseEntity<BikeDto> updateBike(@RequestBody @Validated(Update.class) BikeDto bikeDto) {
        Bike bike = bikeMapper.dtoToEntity(bikeDto);
        Bike updatedBike = bikeService.updateBike(bike);
        return ResponseEntity.ok(bikeMapper.entityToDto(updatedBike));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than zero!") Long id) {
        bikeService.deleteById(id);
    }

}
