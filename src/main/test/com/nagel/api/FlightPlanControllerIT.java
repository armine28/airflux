package com.nagel.api;

import com.google.common.collect.ImmutableList;
import com.nagel.api.error.exception.AirportNotFoundException;
import com.nagel.api.response.Flight;
import com.nagel.common.AirportSign;
import com.nagel.config.Application;
import com.nagel.service.AirportService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Application.class)
@AutoConfigureMockMvc
public class FlightPlanControllerIT {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AirportService airportService;

    @Test
    public void shouldFailCauseAirportNotFound() throws Exception {
        final String testCity = "TEST_CITY";

        given(airportService.getFlights(testCity)).willThrow(new AirportNotFoundException("Foo"));

        mvc.perform(get("/flightplan")
                .param("airport", testCity))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnFlights() throws Exception {
        final List<Flight> flights = ImmutableList.of(
                new Flight(AirportSign.MUC,
                        AirportSign.TXL,
                        LocalDateTime.of(2018, 10, 10, 20, 00),
                        LocalDateTime.of(2018, 10, 10, 22, 00),
                        "FL1"),
                new Flight(AirportSign.HAM,
                        AirportSign.LHR,
                        LocalDateTime.of(2018, 10, 10, 18, 00),
                        LocalDateTime.of(2018, 10, 10, 19, 30),
                        "FL2"));
        given(airportService.getFlights()).willReturn(flights);

        mvc.perform(get("/flightplan"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].origin", is(AirportSign.MUC.name())))
                .andExpect(jsonPath("$[0].destination", is(AirportSign.TXL.name())))
                .andExpect(jsonPath("$[0].equipment", is("FL1")));
    }
}
