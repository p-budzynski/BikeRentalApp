package pl.kurs.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kurs.entity.Bike;
import pl.kurs.entity.BikeDetails;
import pl.kurs.exception.DataNotFoundException;
import pl.kurs.exception.EntityCannotBeDeleteException;
import pl.kurs.repository.BikeRepository;
import pl.kurs.repository.ReservationRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BikeServiceTest {

    @Mock
    private BikeRepository bikeRepositoryMock;

    @Mock
    private ReservationRepository reservationRepositoryMock;

    @InjectMocks
    private BikeService bikeService;

    @Test
    void shouldSaveBike() {
        //given
        Bike testBike = createTestBike();
        when(bikeRepositoryMock.save(testBike)).thenReturn(testBike);

        //when
        Bike savedBike = bikeService.save(testBike);

        //then
        assertThat(savedBike).isEqualTo(testBike);
    }

    @Test
    void shouldReturnBikeForGetBikeById() {
        //given
        Bike testBike = createTestBike();
        when(bikeRepositoryMock.findByIdAndDeletedFalse(1L)).thenReturn(Optional.of(testBike));

        //when
        Bike result = bikeService.getBikeById(1L);

        //then
        assertThat(result).isEqualTo(testBike);
    }

    @Test
    void shouldThrowWhenNotFoundBikeById() {
        //given
        when(bikeRepositoryMock.findByIdAndDeletedFalse(1L)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> bikeService.getBikeById(1L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Bike not found with id: 1");
    }

    @Test
    void shouldDoNothingForDeleteBikeById() {
        //given
        when(bikeRepositoryMock.existsById(1L)).thenReturn(false);

        //when
        bikeService.deleteById(1L);

        //then
        verify(bikeRepositoryMock, never()).deleteById(1L);
    }

    @Test
    void shouldDeleteBikeByIdWhenBikeIsExists() {
        //given
        when(bikeRepositoryMock.existsById(1L)).thenReturn(true);
        when(reservationRepositoryMock.existsByBikeId(1L)).thenReturn(false);

        //when
        bikeService.deleteById(1L);

        //then
        verify(bikeRepositoryMock).deleteById(1L);
    }

    @Test
    void shouldThrowWhenBikeHasActiveReservationForDeleteById() {
        //given
        when(bikeRepositoryMock.existsById(1L)).thenReturn(true);
        when(reservationRepositoryMock.existsByBikeId(1L)).thenReturn(true);

        //when then
        assertThatThrownBy(() -> bikeService.deleteById(1L))
                .isInstanceOf(EntityCannotBeDeleteException.class)
                .hasMessageContaining("Cannot delete bike with id: 1 - the bike has active reservations");
    }

    @Test
    void shouldUpdateBikeWhenExists() {
        //given
        Bike testBike = createTestBike();
        Bike incomingBike = new Bike("Kross", "Trial", new BikeDetails("XYZ4321"));
        incomingBike.setId(1L);
        when(bikeRepositoryMock.findByIdForUpdate(1L)).thenReturn(Optional.of(testBike));
        when(bikeRepositoryMock.save(any(Bike.class))).thenAnswer(inv -> inv.getArgument(0));

        //when
        Bike updated = bikeService.updateBike(incomingBike);

        //then
        assertThat(updated.getId()).isEqualTo(incomingBike.getId());
        assertThat(updated.getBrand()).isEqualTo(incomingBike.getBrand());
        assertThat(updated.getModel()).isEqualTo(incomingBike.getModel());
        assertThat(updated.getBikeDetails()).isEqualTo(incomingBike.getBikeDetails());
    }

    @Test
    void shouldThrowWhenUpdateBikeNotFound() {
        //given
        Bike testBike = createTestBike();
        when(bikeRepositoryMock.findByIdForUpdate(1L)).thenReturn(Optional.empty());

        //when then
        assertThatThrownBy(() -> bikeService.updateBike(testBike))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("Bike not found with id: 1");
    }

    @Test
    void shouldReturnAllBikesList() {
        //given
        Bike testBike = createTestBike();
        List<Bike> bikes = List.of(testBike);
        when(bikeRepositoryMock.findAllByDeletedFalse()).thenReturn(bikes);

        //when
        List<Bike> result = bikeService.getAll();

        //then
        assertThat(result).hasSize(1);
        assertThat(result.getFirst())
                    .usingRecursiveComparison()
                    .isEqualTo(testBike);
    }

    private Bike createTestBike() {
        Bike bike = new Bike("Giant", "Sport", new BikeDetails("ABC12345"));
        bike.setId(1L);
        return bike;
    }

}