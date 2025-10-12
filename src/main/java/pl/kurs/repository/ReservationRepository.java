package pl.kurs.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import pl.kurs.entity.Reservation;

import java.time.LocalDate;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("""
            select case when count(r) > 0 then true else false end
            from Reservation r
            where r.bike.id = :bikeId
            and r.startDate <= :endDate
            and r.endDate >= :startDate
            """)
    boolean existsOverlappingReservation(Long bikeId, LocalDate startDate, LocalDate endDate);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select r from Reservation r where r.id = :id and r.status <> 'CANCELED'")
    Optional<Reservation> findByIdForUpdate(Long id);

    boolean existsByBikeId(Long id);

    boolean existsByClientId(Long id);
}
