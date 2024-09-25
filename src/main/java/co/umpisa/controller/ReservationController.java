package co.umpisa.controller;

import co.umpisa.exceptions.ExceptionResponse;
import co.umpisa.exceptions.ReservationException;
import co.umpisa.model.Reservation;
import co.umpisa.model.ReservationRequest;
import co.umpisa.service.ReservationService;
import jakarta.websocket.server.PathParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class ReservationController {

    private static final Logger LOG = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    private ReservationService reservationService;

    @PostMapping(
            value = "/reservation",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Reservation addReservation(@RequestBody ReservationRequest reservationRequest) {
        return reservationService.addReservation(reservationRequest);
    }

    @GetMapping(value = "/reservation/{id}",
            produces = APPLICATION_JSON_VALUE)
    public Reservation getReservation(@PathVariable("id") UUID reservationId) {
        return reservationService.getReservation(reservationId);
    }

    @GetMapping(value = "/reservations", produces = APPLICATION_JSON_VALUE)
    public List<Reservation> getReservations(@PathParam("customerName") String customerName) {
        return reservationService.getReservations(customerName);
    }

    @DeleteMapping("/reservation/{id}")
    public void deleteReservation(@PathVariable("id") UUID reservationId) {
        reservationService.deleteReservation(reservationId);
    }

    @PutMapping(value = "/reservation/{id}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    public Reservation updateReservation(@PathVariable("id") UUID reservationId, @RequestBody ReservationRequest reservationRequest) {
        return reservationService.updateReservation(reservationId, reservationRequest);
    }

    // TODO too generic
    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<Object> handleReservationException(Exception ex) {
        return new ResponseEntity<Object>(new ExceptionResponse(ex), new HttpHeaders(), 500);
    }

    // TODO need to be specific
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Object> handleNotFound(Exception ex) {
        return new ResponseEntity<Object>(new ExceptionResponse(ex), new HttpHeaders(), 404);
    }

}
