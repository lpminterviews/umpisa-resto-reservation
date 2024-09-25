package co.umpisa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsNotificationService {

    private static final Logger LOG = LoggerFactory.getLogger(SmsNotificationService.class);

    public void sendSms(String number, String text) {
        LOG.info("SMS notification sent to {}, {}", number, text);
    }

}
