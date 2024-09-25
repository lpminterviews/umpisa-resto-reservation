package co.umpisa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RestoReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestoReservationApplication.class, args);
    }

}
