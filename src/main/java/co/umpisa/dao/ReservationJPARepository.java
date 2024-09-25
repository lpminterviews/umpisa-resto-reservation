package co.umpisa.dao;

import co.umpisa.model.Reservation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationJPARepository extends ListCrudRepository<Reservation, UUID> {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(UUID id);

    void deleteById(UUID id);

    @Query(value = "SELECT r FROM Reservation r WHERE r.reservationTime > :start AND r.reservationTime < :end")
    List<Reservation> findByTimestamp(@Param("start") ZonedDateTime start, @Param("end") ZonedDateTime end);

    @Query(value = "SELECT r FROM Reservation r, Customer c WHERE c.customerName = :customerName")
    List<Reservation> findReservationsByCustomerName(@Param("customerName") String customerName);

}
