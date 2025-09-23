package pl.kurs.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dto.ClientDto;
import pl.kurs.entity.Client;
import pl.kurs.mapper.ClientMapper;
import pl.kurs.service.ClientService;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto createClient(@RequestBody ClientDto clientDto) {
        Client client = clientMapper.dtoToEntity(clientDto);
        Client saved = clientService.save(client);
        return clientMapper.entityToDto(saved);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable("id") Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(clientMapper.entityToDto(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        List<Client> clients = clientService.getAll();
        return ResponseEntity.ok(clientMapper.entitiesToDtos(clients));
    }

    @PutMapping
    public ClientDto updateClient(@RequestBody ClientDto clientDto) {
        Client client = clientMapper.dtoToEntity(clientDto);
        Client updatedClient = clientService.updateClient(client);
        return clientMapper.entityToDto(updatedClient);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        clientService.deleteById(id);
    }

}
