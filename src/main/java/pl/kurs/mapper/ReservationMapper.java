package pl.kurs.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.kurs.dto.ReservationDto;
import pl.kurs.entity.Reservation;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    @Mapping(source = "bike.id", target = "bikeId")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "status.value", target = "statusName")
    ReservationDto entityToDto(Reservation entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bike", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "status", ignore = true)
    Reservation dtoToEntity(ReservationDto dto);
}
