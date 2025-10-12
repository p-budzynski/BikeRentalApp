package pl.kurs.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.kurs.dto.BikeDetailsDto;
import pl.kurs.dto.BikeDto;
import pl.kurs.entity.Bike;
import pl.kurs.entity.BikeDetails;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BikeMapperTest {
    private final BikeMapper bikeMapper = Mappers.getMapper(BikeMapper.class);

    @Test
    void shouldMapEntityToDto() {
        //given
        Bike testBike = createTestBike();
        BikeDto testBikeDto = createTestBikeDto();

        //when
        BikeDto dto = bikeMapper.entityToDto(testBike);

        //then
        assertThat(dto)
                .usingRecursiveComparison()
                .isEqualTo(testBikeDto);
    }

    @Test
    void shouldMapEntityListToDtoList() {
        //given
        Bike testBike = createTestBike();
        BikeDto testBikeDto = createTestBikeDto();
        List<Bike> bikes = List.of(testBike);

        //when
        List<BikeDto> dtos = bikeMapper.entitiesToDtos(bikes);

        //then
        assertThat(dtos).hasSize(1);
        assertThat(dtos.getFirst())
                .usingRecursiveComparison()
                .isEqualTo(testBikeDto);
    }

    @Test
    void shouldMapDtoToEntity() {
        //given
        Bike testBike = createTestBike();
        BikeDto testBikeDto = createTestBikeDto();

        //when
        Bike entity = bikeMapper.dtoToEntity(testBikeDto);

        //then
        assertThat(entity)
                .usingRecursiveComparison()
                .isEqualTo(testBike);
    }

    @Test
    void shouldReturnNullWhenEntityToDtoGivenNull() {
        //when then
        assertThat(bikeMapper.entityToDto(null)).isNull();
    }

    @Test
    void shouldReturnNullWhenDtoToEntityGivenNull() {
        //when then
        assertThat(bikeMapper.dtoToEntity(null)).isNull();
    }

    @Test
    void shouldReturnEmptyListWhenEntitiesToDtosGivenNull() {
        //when then
        assertThat(bikeMapper.entitiesToDtos(null)).isNull();
    }

    @Test
    void shouldReturnDtoWithBikeDetailsNullWhenEntityToDtoGivenBikeDetailsNull() {
        //given
        Bike entity = new Bike("Giant", "Sport", null);

        //when
        BikeDto dto = bikeMapper.entityToDto(entity);

        //then
        assertThat(dto.getBrand()).isEqualTo(entity.getBrand());
        assertThat(dto.getModel()).isEqualTo(entity.getModel());
        assertThat(dto.getBikeDetails()).isNull();
    }

    @Test
    void shouldReturnEntityWithBikeDetailsNullWhenDtoToEntityGivenBikeDetailsDtoNull() {
        //given
        BikeDto dto = new BikeDto("Giant", "Sport", null);

        //when
        Bike entity = bikeMapper.dtoToEntity(dto);

        //then
        assertThat(entity.getBrand()).isEqualTo(dto.getBrand());
        assertThat(entity.getModel()).isEqualTo(dto.getModel());
        assertThat(entity.getBikeDetails()).isNull();
    }

    private Bike createTestBike() {
        return new Bike("Giant", "Sport", new BikeDetails("ABC12345"));
    }

    private BikeDto createTestBikeDto() {
        return new BikeDto("Giant", "Sport", new BikeDetailsDto("ABC12345"));
    }

}