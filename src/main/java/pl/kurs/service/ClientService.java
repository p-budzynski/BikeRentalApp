package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.entity.Client;
import pl.kurs.exception.DataNotFoundException;
import pl.kurs.exception.EntityCannotBeDeleteException;
import pl.kurs.repository.ClientRepository;
import pl.kurs.repository.ReservationRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException(STR."Client not found with id: \{id}"));
    }

    @Transactional
    public void deleteById(Long id) {
        if (clientRepository.existsById(id)) {
            if (reservationRepository.existsByClientId(id)) {
                throw new EntityCannotBeDeleteException(STR."Cannot delete client with id: \{id} - the client has active reservations");
            }
            clientRepository.deleteById(id);
        }
    }

    @Transactional
    public Client updateClient(Client client) {
        Client clientToUpdate = getClientByIdForUpdate(client.getId());
        BeanUtils.copyProperties(client, clientToUpdate);
        return clientRepository.save(clientToUpdate);
    }

    private Client getClientByIdForUpdate(Long id) {
        return clientRepository.findClientByIdForUpdate(id)
                .orElseThrow(() -> new DataNotFoundException(STR."Client not found with id: \{id}"));
    }

    public List<Client> getAll() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new DataNotFoundException("No clients in the database");
        }
        return clients;
    }
}
