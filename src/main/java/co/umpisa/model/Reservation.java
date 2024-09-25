package co.umpisa.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
public class Reservation implements Serializable {

    @Id
    private UUID reservationId;
    @ManyToOne(targetEntity = Customer.class, cascade = CascadeType.MERGE)
    private Customer customer;
    private int numberOfGuests;
    private ZonedDateTime reservationTime;

    public Reservation() {
        // empty
    }

    public Reservation(UUID reservationId, Customer customer, int numberOfGuests, ZonedDateTime reservationTime) {
        this.reservationId = reservationId;
        this.customer = customer;
        this.numberOfGuests = numberOfGuests;
        this.reservationTime = reservationTime;
    }

    public static Reservation fromRequest(UUID reservationId, ReservationRequest reservationRequest) {
        return new Reservation(reservationId,
                new Customer(reservationRequest.getCustomerName(), reservationRequest.getPhoneNumber(), reservationRequest.getEmail(), reservationRequest.getPreferredChannels()),
                reservationRequest.getNumberOfGuests(),
                reservationRequest.getReservationTime());
    }

    public static Reservation fromRequest(ReservationRequest reservationRequest) {
        return fromRequest(UUID.randomUUID(), reservationRequest);
    }

    public UUID getReservationId() {
        return reservationId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public ZonedDateTime getReservationTime() {
        return reservationTime;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationId=" + reservationId +
                ", customer=" + customer +
                ", numberOfGuests=" + numberOfGuests +
                ", reservationTime=" + reservationTime +
                '}';
    }

}
