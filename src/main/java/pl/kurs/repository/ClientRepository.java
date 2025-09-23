package pl.kurs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kurs.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
