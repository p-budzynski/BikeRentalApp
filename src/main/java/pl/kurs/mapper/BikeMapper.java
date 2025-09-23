package pl.kurs.mapper;

import org.mapstruct.Mapper;
import pl.kurs.dto.BikeDto;
import pl.kurs.entity.Bike;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BikeMapper {
    Bike dtoToEntity(BikeDto dto);

    BikeDto entityToDto(Bike entity);

    List<BikeDto> entitiesToDtos(List<Bike> bikes);
}
