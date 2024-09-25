package co.umpisa.dao;

import co.umpisa.model.Reservation;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public interface ReservationRepository {

    public void addReservation(Reservation reservation);

    public Reservation getReservation(UUID reservationId);

    public void deleteReservation(UUID reservationId);

    public void updateReservation(Reservation reservation);

    public List<Reservation> getReservations(ZonedDateTime start, ZonedDateTime end);

    List<Reservation> getReservations(String customerName);
}
