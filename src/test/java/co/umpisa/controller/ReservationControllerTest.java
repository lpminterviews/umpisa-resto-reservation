package co.umpisa.controller;

import co.umpisa.model.Reservation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReservationControllerTest {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MockMvc mockMvc;
    private List<UUID> reservationIdList = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        objectMapper.findAndRegisterModules();
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() throws Exception {
        reservationIdList.stream()
                .forEach(reservationId -> {
                    try {
                        this.mockMvc.perform(delete("/reservation/" + reservationId)
                                .contentType(APPLICATION_JSON_VALUE));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        reservationIdList.clear();
    }

    @Test
    void shouldReturnPostResult() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/reservation").content(
                                """
                                        {
                                            "customerName": "Test Customer 1",
                                            "phoneNumber": "+63 999 1111111",
                                            "email": "customer1@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 11,
                                            "reservationTime": "2024-09-25T20:11:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 1")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 1111111")))
                .andExpect(jsonPath("$.customer.email", is("customer1@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(11)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:11:00Z")))
                .andReturn();

        String result = mvcResult.getResponse().getContentAsString();
        Reservation reservation = objectMapper.readValue(result, Reservation.class);
        reservationIdList.add(reservation.getReservationId());
    }

    @Test
    void shouldReturnGetResult() throws Exception {
        MvcResult mvcResult = this.mockMvc.perform(post("/reservation").content(
                                """
                                        {
                                            "customerName": "Test Customer 2",
                                            "phoneNumber": "+63 999 2222222",
                                            "email": "customer2@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 22,
                                            "reservationTime": "2024-09-25T20:22:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 2")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 2222222")))
                .andExpect(jsonPath("$.customer.email", is("customer2@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(22)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:22:00Z")))
                .andReturn();


        String result = mvcResult.getResponse().getContentAsString();
        Reservation reservation = objectMapper.readValue(result, Reservation.class);
        reservationIdList.add(reservation.getReservationId());

        this.mockMvc.perform(get("/reservation/" + reservation.getReservationId())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 2")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 2222222")))
                .andExpect(jsonPath("$.customer.email", is("customer2@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(22)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:22:00Z")));
    }

    @Test
    void shouldReturnUpdatedResult() throws Exception {
        MvcResult postMvcResult = this.mockMvc.perform(post("/reservation").content(
                                """
                                        {
                                            "customerName": "Test Customer 3",
                                            "phoneNumber": "+63 999 3333333",
                                            "email": "customer3@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 33,
                                            "reservationTime": "2024-09-25T20:33:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 3")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 3333333")))
                .andExpect(jsonPath("$.customer.email", is("customer3@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(33)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:33:00Z")))
                .andReturn();


        String result = postMvcResult.getResponse().getContentAsString();
        Reservation postReservation = objectMapper.readValue(result, Reservation.class);
        reservationIdList.add(postReservation.getReservationId());

        this.mockMvc.perform(get("/reservation/" + postReservation.getReservationId())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 3")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 3333333")))
                .andExpect(jsonPath("$.customer.email", is("customer3@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(33)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:33:00Z")));


        // PUT Request
        MvcResult putMvcResult = this.mockMvc.perform(put("/reservation/" + postReservation.getReservationId()).content(
                                """
                                        {
                                            "customerName": "Test Customer 3",
                                            "phoneNumber": "+63 999 4444444",
                                            "email": "customer4@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 44,
                                            "reservationTime": "2024-09-25T20:44:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 3")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 4444444")))
                .andExpect(jsonPath("$.customer.email", is("customer4@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(44)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:44:00Z")))
                .andReturn();

        String putResult = putMvcResult.getResponse().getContentAsString();
        Reservation putReservation = objectMapper.readValue(putResult, Reservation.class);
        reservationIdList.add(putReservation.getReservationId());

        this.mockMvc.perform(get("/reservation/" + putReservation.getReservationId())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 3")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 4444444")))
                .andExpect(jsonPath("$.customer.email", is("customer4@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(44)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:44:00Z")));
    }

    @Test
    void shouldDeleteReservation() throws Exception {
        MvcResult postMvcResult = this.mockMvc.perform(post("/reservation").content(
                                """
                                        {
                                            "customerName": "Test Customer 5",
                                            "phoneNumber": "+63 999 5555555",
                                            "email": "customer5@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 55,
                                            "reservationTime": "2024-09-25T20:55:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 5")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 5555555")))
                .andExpect(jsonPath("$.customer.email", is("customer5@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(55)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:55:00Z")))
                .andReturn();


        String result = postMvcResult.getResponse().getContentAsString();
        Reservation postReservation = objectMapper.readValue(result, Reservation.class);
        reservationIdList.add(postReservation.getReservationId());

        this.mockMvc.perform(get("/reservation/" + postReservation.getReservationId())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 5")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 5555555")))
                .andExpect(jsonPath("$.customer.email", is("customer5@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.numberOfGuests", is(55)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:55:00Z")));


        // PUT Request
        this.mockMvc.perform(put("/reservation/" + postReservation.getReservationId()).content(
                                """
                                        {
                                            "customerName": "Test Customer 5",
                                            "phoneNumber": "+63 999 5555556",
                                            "email": "customer56@test.com",
                                            "preferredChannels": ["SMS"],
                                            "numberOfGuests": 56,
                                            "reservationTime": "2024-09-25T20:56:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 5")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 5555556")))
                .andExpect(jsonPath("$.customer.email", is("customer56@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("SMS"))))
                .andExpect(jsonPath("$.numberOfGuests", is(56)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:56:00Z")))
                .andReturn();

        this.mockMvc.perform(get("/reservation/" + postReservation.getReservationId())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reservationId", notNullValue()))
                .andExpect(jsonPath("$.reservationId", hasLength(36)))
                .andExpect(jsonPath("$.customer.customerName", is("Test Customer 5")))
                .andExpect(jsonPath("$.customer.phoneNumber", is("+63 999 5555556")))
                .andExpect(jsonPath("$.customer.email", is("customer56@test.com")))
                .andExpect(jsonPath("$.customer.preferredChannels", is(List.of("SMS"))))
                .andExpect(jsonPath("$.numberOfGuests", is(56)))
                .andExpect(jsonPath("$.reservationTime", is("2024-09-25T20:56:00Z")));


        // DELETE request
        this.mockMvc.perform(delete("/reservation/" + postReservation.getReservationId()))
                .andExpect(status().isOk())
                .andExpect(content().string(""));

        // GET request
        this.mockMvc.perform(get("/reservation/" + postReservation.getReservationId()))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnMultipleReservations() throws Exception {
        MvcResult postMvcResult1 = this.mockMvc.perform(post("/reservation").content(
                                """
                                        {
                                            "customerName": "Test Customer List",
                                            "phoneNumber": "+63 999 0000001",
                                            "email": "customer1@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 1,
                                            "reservationTime": "2024-09-25T20:01:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andReturn();

        String result1 = postMvcResult1.getResponse().getContentAsString();
        Reservation postReservation1 = objectMapper.readValue(result1, Reservation.class);
        reservationIdList.add(postReservation1.getReservationId());

        MvcResult postMvcResult2 = this.mockMvc.perform(post("/reservation").content(
                                """
                                        {
                                            "customerName": "Test Customer List",
                                            "phoneNumber": "+63 999 0000001",
                                            "email": "customer1@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 1,
                                            "reservationTime": "2024-09-26T21:02:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andReturn();

        String result2 = postMvcResult2.getResponse().getContentAsString();
        Reservation postReservation2 = objectMapper.readValue(result2, Reservation.class);
        reservationIdList.add(postReservation2.getReservationId());

        this.mockMvc.perform(get("/reservations?customerName=Test Customer List")
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].reservationId", notNullValue()))
                .andExpect(jsonPath("$.[0].reservationId", hasLength(36)))
                .andExpect(jsonPath("$.[0].customer.customerName", is("Test Customer List")))
                .andExpect(jsonPath("$.[0].customer.phoneNumber", is("+63 999 0000001")))
                .andExpect(jsonPath("$.[0].customer.email", is("customer1@test.com")))
                .andExpect(jsonPath("$.[0].customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.[0].numberOfGuests", is(1)))
                .andExpect(jsonPath("$.[0].reservationTime", is("2024-09-25T20:01:00Z")))

                .andExpect(jsonPath("$.[1].reservationId", notNullValue()))
                .andExpect(jsonPath("$.[1].reservationId", hasLength(36)))
                .andExpect(jsonPath("$.[1].customer.customerName", is("Test Customer List")))
                .andExpect(jsonPath("$.[1].customer.phoneNumber", is("+63 999 0000001")))
                .andExpect(jsonPath("$.[1].customer.email", is("customer1@test.com")))
                .andExpect(jsonPath("$.[1].customer.preferredChannels", is(List.of("EMAIL"))))
                .andExpect(jsonPath("$.[1].numberOfGuests", is(1)))
                .andExpect(jsonPath("$.[1].reservationTime", is("2024-09-26T21:02:00Z")));
    }

    @Test
    void shouldFailNonExistingReservationUpdate() throws Exception {
        this.mockMvc.perform(put("/reservation/" + UUID.randomUUID()).content(
                                """
                                        {
                                            "customerName": "Test Customer 5",
                                            "phoneNumber": "+63 999 5555556",
                                            "email": "customer56@test.com",
                                            "preferredChannels": ["SMS"],
                                            "numberOfGuests": 56,
                                            "reservationTime": "2024-09-25T20:56:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailNonExistingReservationGet() throws Exception {
        this.mockMvc.perform(get("/reservation/" + UUID.randomUUID())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());

    }

    @Test
    void shouldFailNonExistingReservationDelete() throws Exception {
        this.mockMvc.perform(delete("/reservation/" + UUID.randomUUID())
                        .contentType(APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldFailInvalidNumberOfGuests() throws Exception {
        this.mockMvc.perform(post("/reservation").content(
                                """
                                        {
                                            "customerName": "Test Customer 5",
                                            "phoneNumber": "+63 999 5555555",
                                            "email": "customer5@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 0,
                                            "reservationTime": "2024-09-25T20:55:00Z"
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

    @Test
    void shouldFailInvalidReservationTime() throws Exception {
        this.mockMvc.perform(post("/reservation").content(
                                """
                                        {
                                            "customerName": "Test Customer 5",
                                            "phoneNumber": "+63 999 5555555",
                                            "email": "customer5@test.com",
                                            "preferredChannels": ["EMAIL"],
                                            "numberOfGuests": 2,
                                            "reservationTime": ""
                                        }
                                        """
                        ).contentType(APPLICATION_JSON_VALUE)
                        .accept(APPLICATION_JSON_VALUE))
                .andExpect(status().is5xxServerError())
                .andReturn();
    }

}