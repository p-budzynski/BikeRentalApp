package pl.kurs.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import pl.kurs.dto.ReservationDto;
import pl.kurs.entity.Bike;
import pl.kurs.entity.Client;
import pl.kurs.entity.Reservation;
import pl.kurs.entity.Status;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class ReservationMapperTest {
    private static final LocalDate FROM = LocalDate.of(2025, 7, 1);
    private static final LocalDate TO = LocalDate.of(2025, 7, 10);
    private final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);

    @Test
    void shouldMapEntityToDto() {
        //given
        Reservation testReservation = createTestReservation();
        ReservationDto testReservationDto = createTestReservationDto();

        //when
        ReservationDto dto = reservationMapper.entityToDto(testReservation);

        //then
        assertThat(dto)
                .usingRecursiveComparison()
                .isEqualTo(testReservationDto);
    }

    @Test
    void shouldMapDtoToEntity() {
        //given
        Reservation testReservation = createTestReservation();
        ReservationDto testReservationDto = createTestReservationDto();

        //when
        Reservation entity = reservationMapper.dtoToEntity(testReservationDto);

        //then
        assertThat(entity)
                .usingRecursiveComparison()
                .ignoringFields("bike")
                .ignoringFields("client")
                .ignoringFields("status")
                .isEqualTo(testReservation);
    }

    @Test
    void shouldReturnNullBikeClientStatusWhenFieldsAreNull() {
        //given
        Reservation testReservation = new Reservation(null, null, FROM, TO,null);
        testReservation.setId(1L);

        //when
        ReservationDto dto = reservationMapper.entityToDto(testReservation);

        //then
        assertThat(dto.getId()).isEqualTo(1L);
        assertThat(dto.getBikeId()).isNull();
        assertThat(dto.getClientId()).isNull();
        assertThat(dto.getStatusName()).isNull();
    }

    @Test
    void shouldReturnNullWhenEntityToDtoGivenNull() {
        //when then
        assertThat(reservationMapper.entityToDto(null)).isNull();
    }

    @Test
    void shouldReturnNullWhenDtoToEntityGivenNull() {
        //when then
        assertThat(reservationMapper.dtoToEntity(null)).isNull();
    }

    private Reservation createTestReservation() {
        Bike bike = new Bike();
        bike.setId(1L);
        Client client = new Client();
        client.setId(1L);
        return new Reservation(bike, client, FROM, TO, Status.RESERVED);
    }

    private ReservationDto createTestReservationDto() {
        return new ReservationDto(1L, 1L, FROM, TO, "RESERVED");
    }

}