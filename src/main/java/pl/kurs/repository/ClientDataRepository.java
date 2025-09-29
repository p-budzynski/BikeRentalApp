package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.entity.ClientData;

public interface ClientDataRepository extends JpaRepository<ClientData, Long> {
    boolean existsByPhoneNumber(String phoneNumber);

    boolean existsByPesel(String pesel);
}
