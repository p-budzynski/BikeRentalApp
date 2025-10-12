package pl.kurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.entity.Client;
import pl.kurs.entity.ClientData;
import pl.kurs.exception.DataNotFoundException;
import pl.kurs.exception.EntityCannotBeDeleteException;
import pl.kurs.repository.ClientRepository;
import pl.kurs.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepositoryMock;

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @InjectMocks
    private ClientService clientService;

    @Test
    void shouldSaveClient() {
        //given
        Client testClient = createTestClient();
        when(clientRepositoryMock.save(testClient)).thenReturn(testClient);

        //when
        Client savedClient = clientService.save(testClient);

        //then
        assertThat(savedClient).isEqualTo(testClient);
    }

    @Test
    void shouldReturnClientForGetBikeById() {
        //given
        Client testClient = createTestClient();
        when(clientRepositoryMock.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(testClient));

        //when
        Client result = clientService.getClientById(1L);

        //then
        assertThat(result).isEqualTo(testClient);
    }

    @Test
    void shouldThrowWhenNotFoundClientById() {
        //given
        when(clientRepositoryMock.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> clientService.getClientById(1L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Client not found with id: 1");
    }

    @Test
    void shouldDoNothingForDeleteClientById() {
        //given
        when(clientRepositoryMock.existsById(1L)).thenReturn(false);

        //when
        clientService.deleteById(1L);

        //then
        verify(clientRepositoryMock, never()).deleteById(1L);
    }

    @Test
    void shouldDeleteClientByIdWhenClientIsExists() {
        //given
        when(clientRepositoryMock.existsById(1L)).thenReturn(true);
        when(reservationRepositoryMock.existsByClientId(1L)).thenReturn(false);

        //when
        clientService.deleteById(1L);

        //then
        verify(clientRepositoryMock).deleteById(1L);
    }

    @Test
    void shouldThrowWhenClientHasActiveReservationForDeleteById() {
        //given
        when(clientRepositoryMock.existsById(1L)).thenReturn(true);
        when(reservationRepositoryMock.existsByClientId(1L)).thenReturn(true);

        //when then
        assertThatThrownBy(() -> clientService.deleteById(1L))
                .isInstanceOf(EntityCannotBeDeleteException.class)
                .hasMessageContaining("Cannot delete client with id: 1 - the client has active reservations");
    }

    @Test
    void shouldUpdateClientWhenExists() {
        //given
        Client testClient = createTestClient();
        Client incomingClient = new Client("Jan", "Kowalski",
                new ClientData("Mokra", "Warszawa", "00-150", "600-999-800", "88051705741", "j.kowal@mail.com"));
        incomingClient.setId(1L);
        when(clientRepositoryMock.findClientByIdForUpdate(1L)).thenReturn(Optional.of(testClient));
        when(clientRepositoryMock.save(any(Client.class))).thenAnswer(inv -> inv.getArgument(0));

        //when
        Client updated = clientService.updateClient(incomingClient);

        //then
        assertThat(updated.getId()).isEqualTo(incomingClient.getId());
        assertThat(updated.getFirstName()).isEqualTo(incomingClient.getFirstName());
        assertThat(updated.getLastName()).isEqualTo(incomingClient.getLastName());
        assertThat(updated.getClientData()).isEqualTo(incomingClient.getClientData());
    }

    @Test
    void shouldThrowWhenUpdateClientNotFound() {
        //given
        Client testClient = createTestClient();
        when(clientRepositoryMock.findClientByIdForUpdate(1L)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> clientService.updateClient(testClient))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Client not found with id: 1");
    }

    @Test
    void shouldReturnAllClientsList() {
        //given
        Client testClient = createTestClient();
        List<Client> clients = List.of(testClient);
        when(clientRepositoryMock.findAllByDeletedFalse()).thenReturn(clients);

        //when
        List<Client> result = clientService.getAll();

        //then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(testClient);
    }

    private Client createTestClient() {
        Client client = new Client("Jan", "Nowak",
                new ClientData("Mokra", "Warszawa", "00-150", "600-700-800", "95051705741", "j.nowak@mail.com"));
        client.setId(1L);
        return client;
    }


}