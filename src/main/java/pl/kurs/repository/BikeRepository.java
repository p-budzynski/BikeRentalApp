package pl.kurs.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.entity.Bike;

import java.util.List;
import java.util.Optional;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select b from Bike b where b.id = :id and b.deleted = false")
    Optional<Bike> findByIdForUpdate(Long id);

    Optional<Bike> findByIdAndDeletedFalse(Long id);

    List<Bike> findAllByDeletedFalse();
}
