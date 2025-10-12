package pl.kurs.controller;

import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pl.kurs.dto.ClientDto;
import pl.kurs.entity.Client;
import pl.kurs.mapper.ClientMapper;
import pl.kurs.service.ClientService;
import pl.kurs.validation.Create;
import pl.kurs.validation.Update;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClientDto> createClient(@RequestBody @Validated ClientDto clientDto) {
        Client client = clientMapper.dtoToEntity(clientDto);
        Client saved = clientService.save(client);
        return ResponseEntity.ok(clientMapper.entityToDto(saved));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDto> getById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than zero!") Long id) {
        Client client = clientService.getClientById(id);
        return ResponseEntity.ok(clientMapper.entityToDto(client));
    }

    @GetMapping
    public ResponseEntity<List<ClientDto>> getAll() {
        List<Client> clients = clientService.getAll();
        return ResponseEntity.ok(clientMapper.entitiesToDtos(clients));
    }

    @PutMapping
    public ResponseEntity<ClientDto> updateClient(@RequestBody @Validated(Update.class) ClientDto clientDto) {
        Client client = clientMapper.dtoToEntity(clientDto);
        Client updatedClient = clientService.updateClient(client);
        return ResponseEntity.ok(clientMapper.entityToDto(updatedClient));
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") @Min(value = 1, message = "ID must be greater than zero!") Long id) {
        clientService.deleteById(id);
    }

}
