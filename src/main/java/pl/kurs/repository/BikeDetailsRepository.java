package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.entity.BikeDetails;

public interface BikeDetailsRepository extends JpaRepository<BikeDetails, Long> {

    boolean existsByVinNumber(String vinNumber);
}
