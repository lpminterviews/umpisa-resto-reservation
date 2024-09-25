package co.umpisa.model;

import java.time.ZonedDateTime;
import java.util.List;

public class ReservationRequest {

    private String customerName;
    private String phoneNumber;
    private String email;
    private List<NotificationChannel> preferredChannels;
    private int numberOfGuests;
    private ZonedDateTime reservationTime;

    public ReservationRequest(String customerName, String phoneNumber, String email, List<NotificationChannel> preferredChannels, int numberOfGuests, ZonedDateTime reservationTime) {
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.preferredChannels = preferredChannels;
        this.numberOfGuests = numberOfGuests;
        this.reservationTime = reservationTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public String getEmail() {
        return email;
    }

    public List<NotificationChannel> getPreferredChannels() {
        return preferredChannels;
    }

    public ZonedDateTime getReservationTime() {
        return reservationTime;
    }

    @Override
    public String toString() {
        return "ReservationRequest{" +
                "customerName='" + customerName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", preferredChannels=" + preferredChannels +
                ", numberOfGuests=" + numberOfGuests +
                ", reservationTime=" + reservationTime +
                '}';
    }

}
