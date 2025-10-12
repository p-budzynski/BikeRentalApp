package pl.kurs.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.kurs.dto.ClientDataDto;
import pl.kurs.dto.ClientDto;
import pl.kurs.entity.Client;
import pl.kurs.entity.ClientData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClientMapperTest {
    private final ClientMapper clientMapper = Mappers.getMapper(ClientMapper.class);

    @Test
    void shouldMapEntityToDto() {
        //given
        Client testClient = createTestClient();
        ClientDto testClientDto = createTestClientDto();

        //when
        ClientDto dto = clientMapper.entityToDto(testClient);

        //then
        assertThat(dto)
                .usingRecursiveComparison()
                .isEqualTo(testClientDto);
    }

    @Test
    void shouldMapEntityListToDtoList() {
        //given
        Client testClient = createTestClient();
        ClientDto testClientDto = createTestClientDto();
        List<Client> clients = List.of(testClient);

        //when
        List<ClientDto> dtos = clientMapper.entitiesToDtos(clients);

        //then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(testClientDto);
    }

    @Test
    void shouldMapDtoToEntity() {
        //given
        Client testClient = createTestClient();
        ClientDto testClientDto = createTestClientDto();

        //when
        Client entity = clientMapper.dtoToEntity(testClientDto);

        //then
        assertThat(entity)
                .usingRecursiveComparison()
                .isEqualTo(testClient);
    }

    @Test
    void shouldReturnNullWhenEntityToDtoGivenNull() {
        //when then
        assertThat(clientMapper.entityToDto(null)).isNull();
    }

    @Test
    void shouldReturnNullWhenDtoToEntityGivenNull() {
        //when then
        assertThat(clientMapper.dtoToEntity(null)).isNull();
    }

    @Test
    void shouldReturnEmptyListWhenEntitiesToDtosGivenNull() {
        //when then
        assertThat(clientMapper.entitiesToDtos(null)).isNull();
    }

    @Test
    void shouldReturnDtoWithBikeDetailsNullWhenEntityToDtoGivenBikeDetailsNull() {
        //given
        Client entity = new Client("Jan", "Nowak", null);

        //when
        ClientDto dto = clientMapper.entityToDto(entity);

        //then
        assertThat(dto.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(dto.getLastName()).isEqualTo(entity.getLastName());
        assertThat(dto.getClientData()).isNull();
    }

    @Test
    void shouldReturnEntityWithBikeDetailsNullWhenDtoToEntityGivenBikeDetailsDtoNull() {
        //given
        ClientDto dto = new ClientDto("Jan", "Nowak", null);

        //when
        Client entity = clientMapper.dtoToEntity(dto);

        //then
        assertThat(entity.getFirstName()).isEqualTo(dto.getFirstName());
        assertThat(entity.getLastName()).isEqualTo(dto.getLastName());
        assertThat(entity.getClientData()).isNull();
    }

    private Client createTestClient() {
        return new Client("Jan", "Nowak",
                new ClientData("Mokra", "Warszawa", "00-150", "600-700-800", "95051705741", "j.nowak@mail.com"));
    }

    private ClientDto createTestClientDto() {
        return new ClientDto("Jan", "Nowak",
                new ClientDataDto("Mokra", "Warszawa", "00-150", "600-700-800", "95051705741", "j.nowak@mail.com"));
    }

}