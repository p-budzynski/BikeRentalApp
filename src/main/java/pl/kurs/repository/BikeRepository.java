package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.entity.Bike;

public interface BikeRepository extends JpaRepository<Bike, Long> {
}
