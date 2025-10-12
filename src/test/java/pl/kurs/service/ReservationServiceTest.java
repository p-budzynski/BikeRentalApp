package pl.kurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.dto.ReservationDto;
import pl.kurs.dto.ReservationUpdateDatesDto;
import pl.kurs.entity.*;
import pl.kurs.exception.DataNotFoundException;
import pl.kurs.exception.OverlappingReservationException;
import pl.kurs.mapper.ReservationMapper;
import pl.kurs.repository.ReservationRepository;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {
    private static final LocalDate FROM = LocalDate.of(2025, 7, 1);
    private static final LocalDate TO = LocalDate.of(2025, 7, 10);

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @Mock
    private BikeService bikeServiceMock;

    @Mock
    private ClientService clientServiceMock;

    @Mock
    private ReservationMapper reservationMapperMock;

    @Mock
    private BikeRentalService bikeRentalServiceMock;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    void shouldCreateReservation() {
        //given
        ReservationDto reservationDto = new ReservationDto(1L, 1L, FROM, TO, null);
        Bike testBike = createTestBike();
        testBike.setId(1L);
        Client testClient = createTestClient();
        testClient.setId(1L);
        Reservation testReservation = new Reservation(testBike, testClient, FROM, TO, Status.RESERVED);

        when(bikeServiceMock.getBikeByIdForUpdate(1L)).thenReturn(testBike);
        when(reservationRepositoryMock.existsOverlappingReservation(1L, FROM, TO)).thenReturn(false);
        when(clientServiceMock.getClientById(1L)).thenReturn(testClient);
        when(reservationMapperMock.dtoToEntity(reservationDto)).thenReturn(testReservation);
        when(reservationRepositoryMock.save(testReservation)).thenReturn(testReservation);
        doNothing().when(bikeRentalServiceMock).sendReservationConfirmation(testClient, testBike, testReservation);
        //when
        Reservation result = reservationService.createReservation(reservationDto);

        //then
        assertThat(result.getBike()).isEqualTo(testBike);
        assertThat(result.getClient()).isEqualTo(testClient);
        assertThat(result.getStatus()).isEqualTo(Status.RESERVED);
    }

    @Test
    void shouldThrowExceptionIfOverlappingReservation() {
        //given
        ReservationDto reservationDto = new ReservationDto(1L, 1L, FROM, TO, null);
        Bike testBike = createTestBike();
        testBike.setId(1L);

        when(bikeServiceMock.getBikeByIdForUpdate(1L)).thenReturn(testBike);
        when(reservationRepositoryMock.existsOverlappingReservation(anyLong(), any(), any())).thenReturn(true);

        //when then
        assertThatThrownBy(() -> reservationService.createReservation(reservationDto))
                .isInstanceOf(OverlappingReservationException.class)
                .hasMessageContaining("There is overlapping reservation for bike id: 1");
    }

    @Test
    void shouldFindReservationById() {
        //given
        Bike testBike = createTestBike();
        Client testClient = createTestClient();
        Reservation testReservation = new Reservation(testBike, testClient, FROM, TO, Status.RESERVED);
        when(reservationRepositoryMock.findById(1L)).thenReturn(Optional.of(testReservation));

        //when
        Reservation result = reservationService.findReservationById(1L);

        //then
        assertThat(result.getBike()).isEqualTo(testReservation.getBike());
        assertThat(result.getClient()).isEqualTo(testReservation.getClient());
        assertThat(result.getStartDate()).isEqualTo(testReservation.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(testReservation.getEndDate());
        assertThat(result.getStatus()).isEqualTo(testReservation.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenReservationByIdNotFound() {
        //given
        when(reservationRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> reservationService.findReservationById(1L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Reservation not found with id: 1");
    }

    @Test
    void shouldCancelReservationById() {
        //given
        Bike testBike = createTestBike();
        Client testClient = createTestClient();
        Reservation testReservation = new Reservation(testBike, testClient, FROM, TO, Status.RESERVED);
        when(reservationRepositoryMock.findByIdForUpdate(1L)).thenReturn(Optional.of(testReservation));
        when(reservationRepositoryMock.save(testReservation)).thenReturn(testReservation);
        doNothing().when(bikeRentalServiceMock).sendReservationCancellation(testClient, testBike, testReservation);

        //when
        Reservation result = reservationService.cancelReservationById(1L);

        //then
        assertThat(result.getStatus()).isEqualTo(Status.CANCELED);
    }

    @Test
    void shouldFindReservationByIdForUpdate() {
        //given
        Bike testBike = createTestBike();
        Client testClient = createTestClient();
        Reservation testReservation = new Reservation(testBike, testClient, FROM, TO, Status.RESERVED);
        when(reservationRepositoryMock.findByIdForUpdate(1L)).thenReturn(Optional.of(testReservation));

        //when
        Reservation result = reservationService.getReservationByIdForUpdate(1L);

        //then
        assertThat(result.getBike()).isEqualTo(testReservation.getBike());
        assertThat(result.getClient()).isEqualTo(testReservation.getClient());
        assertThat(result.getStartDate()).isEqualTo(testReservation.getStartDate());
        assertThat(result.getEndDate()).isEqualTo(testReservation.getEndDate());
        assertThat(result.getStatus()).isEqualTo(testReservation.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenReservationByIdForUpdateNotFound() {
        //given
        when(reservationRepositoryMock.findByIdForUpdate(1L)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> reservationService.getReservationByIdForUpdate(1L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Reservation not found with id: 1");
    }

    @Test
    void shouldUpdateReservationDatesById() {
        //given
        LocalDate newStart = LocalDate.of(2025, 7, 10);
        LocalDate newEnd = LocalDate.of(2025, 7, 15);
        Reservation testReservation = new Reservation(null, null, FROM, TO, Status.RESERVED);
        testReservation.setId(1L);
        ReservationUpdateDatesDto updateDatesDto = new ReservationUpdateDatesDto(newStart, newEnd);

        when(reservationRepositoryMock.findByIdForUpdate(1L)).thenReturn(Optional.of(testReservation));
        when(reservationRepositoryMock.save(testReservation)).thenReturn(testReservation);

        //when
        Reservation result = reservationService.updateReservationDatesById(1L, updateDatesDto);

        //then
        assertThat(result.getStartDate()).isEqualTo(newStart);
        assertThat(result.getEndDate()).isEqualTo(newEnd);
    }

    private Bike createTestBike() {
        return new Bike("Giant", "Sport", new BikeDetails("ABC12345"));
    }

    private Client createTestClient() {
        return new Client("Jan", "Nowak",
                new ClientData("Mokra", "Warszawa", "00-150", "600-700-800", "95051705741", "j.nowak@mail.com"));
    }

}