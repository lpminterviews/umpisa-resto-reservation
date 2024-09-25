package co.umpisa.service;

import co.umpisa.dao.ReservationRepository;
import co.umpisa.model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class ReminderNotificationScheduledService {

    private static final Logger LOG = LoggerFactory.getLogger(ReminderNotificationScheduledService.class);

    // TODO parameterize
    private static final int NOTIFY_HOURS_BEFORE_RESERVED_TIME = 4;

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private SmsNotificationService smsNotificationService;
    @Autowired
    private EmailNotificationService emailNotificationService;

    // TODO fix duplicate notifications
    @Scheduled(cron = "0 0 * * * *")
    public void notifyCustomersBeforeReservationTime() {
        LOG.info("Cron: Sending notifications to customers");

        List<Reservation> reservationList = reservationRepository.getReservations(ZonedDateTime.now(), ZonedDateTime.now().plusHours(NOTIFY_HOURS_BEFORE_RESERVED_TIME));
        reservationList.stream()
                .forEach(reservation -> {
                    String readableDateTime = reservation.getReservationTime().format(DateTimeFormatter.RFC_1123_DATE_TIME);
                    String message = String.format("Hi %s, this is to remind you of your reservation at Umpisa Restaurant. You are reserved for %s guests on %s."
                            , reservation.getCustomer().getCustomerName(), reservation.getNumberOfGuests(), readableDateTime);

                    smsNotificationService.sendSms(reservation.getCustomer().getPhoneNumber(), message);
                    emailNotificationService.sendEmail(reservation.getCustomer().getEmail(),
                            "Reservation Reminder - Umpisa Restaurant",
                            message);
                });
    }

}
