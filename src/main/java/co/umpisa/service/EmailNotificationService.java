package co.umpisa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailNotificationService.class);

    public void sendEmail(String emailAddress, String subject, String message) {
        LOG.info("Email notification sent to {}, {}, {}", emailAddress, subject, message);
    }

}
