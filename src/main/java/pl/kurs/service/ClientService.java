package pl.kurs.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kurs.entity.Client;
import pl.kurs.exception.DataNotFoundException;
import pl.kurs.repository.ClientRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    @Transactional
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Client not found with id: " + id));
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    @Transactional
    public Client updateClient(Client client) {
        Client clientToUpdate = clientRepository.findById(client.getId())
                .orElseThrow(() -> new DataNotFoundException("Client with id: " + client.getId() + " not found"));
        BeanUtils.copyProperties(client, clientToUpdate);
        return clientRepository.save(clientToUpdate);
    }

    public List<Client> getAll() {
        List<Client> clients = clientRepository.findAll();
        if (clients.isEmpty()) {
            throw new DataNotFoundException("No clients in the database");
        }
        return clients;
    }
}
