package co.umpisa.dao;

import co.umpisa.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class ReservationH2Repository implements ReservationRepository {

    @Autowired
    private ReservationJPARepository jpaRepository;

    @Override
    public void addReservation(Reservation reservation) {
        jpaRepository.save(reservation);
    }

    @Override
    public Reservation getReservation(UUID reservationId) {
        return jpaRepository.findById(reservationId).orElseThrow();
    }

    @Override
    public void deleteReservation(UUID reservationId) {
        jpaRepository.deleteById(reservationId);
    }

    @Override
    public void updateReservation(Reservation reservation) {
        jpaRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservations(ZonedDateTime start, ZonedDateTime end) {
        return jpaRepository.findByTimestamp(start, end);
    }

    @Override
    public List<Reservation> getReservations(String customerName) {
        return jpaRepository.findReservationsByCustomerName(customerName);
    }
}
