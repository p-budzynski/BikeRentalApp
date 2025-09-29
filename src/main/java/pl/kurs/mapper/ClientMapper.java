package pl.kurs.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kurs.dto.ClientDto;
import pl.kurs.entity.Client;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    Client dtoToEntity(ClientDto dto);

    @Mapping(target = "client.deleted", ignore = true)
    ClientDto entityToDto(Client entity);

    List<ClientDto> entitiesToDtos(List<Client> clients);
}
