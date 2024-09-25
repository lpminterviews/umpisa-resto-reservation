package co.umpisa.service;

import co.umpisa.dao.ReservationRepository;
import co.umpisa.exceptions.ReservationException;
import co.umpisa.model.NotificationChannel;
import co.umpisa.model.Reservation;
import co.umpisa.model.ReservationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class ReservationService {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SmsNotificationService smsNotificationService;
    @Autowired
    private EmailNotificationService emailNotificationService;

    public Reservation addReservation(ReservationRequest reservationRequest) {
        LOG.info("addReservation: {}", reservationRequest);
        Reservation reservation = Reservation.fromRequest(reservationRequest);

        reservationRepository.addReservation(reservation);
        notifySuccessfulReservation(reservation);

        return getReservation(reservation.getReservationId());
    }

    public Reservation getReservation(UUID reservationId) {
        LOG.info("getReservation: {}", reservationId);
        return reservationRepository.getReservation(reservationId);
    }

    public void deleteReservation(UUID reservationId) {
        LOG.info("deleteReservation: {}", reservationId);
        Reservation reservation = reservationRepository.getReservation(reservationId);

        reservationRepository.deleteReservation(reservationId);
        notifySuccessfulCancellation(reservation);
    }

    public Reservation updateReservation(UUID reservationId, ReservationRequest reservationRequest) {
        LOG.info("updateReservation: {}, {}", reservationId, reservationRequest);
        if (Objects.isNull(getReservation(reservationId))) {
            throw new ReservationException("Reservation ID not found");
        }
        Reservation reservation = Reservation.fromRequest(reservationId, reservationRequest);
        reservationRepository.updateReservation(reservation);
        notifySuccessfulUpdate(reservation);

        return getReservation(reservation.getReservationId());
    }

    private void notifySuccessfulReservation(Reservation reservation) {
        String notificationMessage = composeAddReservationSuccessfulMessage(reservation);
        notifySmsIfPreferred(reservation, notificationMessage);
        notifyEmailIfPreferred(reservation, "Reservation Successful - Umpisa Restaurant", notificationMessage);
    }

    private void notifySuccessfulUpdate(Reservation reservation) {
        String notificationMessage = composeUpdateReservationSuccessfulMessage(reservation);
        notifySmsIfPreferred(reservation, notificationMessage);
        notifyEmailIfPreferred(reservation, "Reservation Updated - Umpisa Restaurant", notificationMessage);
    }

    private void notifySuccessfulCancellation(Reservation reservation) {
        String notificationMessage = composeDeleteReservationSuccessfulMessage(reservation);
        notifySmsIfPreferred(reservation, notificationMessage);
        notifyEmailIfPreferred(reservation, "Reservation Cancelled - Umpisa Restaurant", notificationMessage);
    }

    private void notifySmsIfPreferred(Reservation reservation, String notificationMessage) {
        if (reservation.getCustomer().getPreferredChannels().contains(NotificationChannel.SMS)) {
            smsNotificationService.sendSms(reservation.getCustomer().getPhoneNumber(), notificationMessage);
        }
    }

    private void notifyEmailIfPreferred(Reservation reservation, String subject, String notificationMessage) {
        if (reservation.getCustomer().getPreferredChannels().contains(NotificationChannel.EMAIL)) {
            emailNotificationService.sendEmail(reservation.getCustomer().getEmail(),
                    subject,
                    notificationMessage);
        }
    }

    private String composeAddReservationSuccessfulMessage(Reservation reservation) {
        String readableDateTime = reservation.getReservationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String message = String.format("Hi %s, Thank you for making a reservation. Your reservation has been confirmed for %s guests on %s.",
                reservation.getCustomer().getCustomerName(), reservation.getNumberOfGuests(), readableDateTime);
        return message;
    }

    private String composeUpdateReservationSuccessfulMessage(Reservation reservation) {
        String readableDateTime = reservation.getReservationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String message = String.format("Hi %s, Your reservation has been updated for %s guests on %s.",
                reservation.getCustomer().getCustomerName(), reservation.getNumberOfGuests(), readableDateTime);
        return message;
    }

    private String composeDeleteReservationSuccessfulMessage(Reservation reservation) {
        String readableDateTime = reservation.getReservationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);
        String message = String.format("Hi %s, Your reservation for %s guests on %s has been cancelled .",
                reservation.getCustomer().getCustomerName(), reservation.getNumberOfGuests(), readableDateTime);
        return message;
    }

    public List<Reservation> getReservations(String customerName) {
        return reservationRepository.getReservations(customerName);
    }
}
