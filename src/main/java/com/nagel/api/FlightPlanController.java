package com.nagel.api;

import com.nagel.api.response.Flight;
import com.nagel.service.AirportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Api("Help the airports assign gates that fit the departing aircraft")
public class FlightPlanController {
    @Autowired
    private AirportService airportService;

    @RequestMapping(
            value = "/flightplan",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public Collection<Flight> getFlights(@ApiParam @RequestParam(name = "airport", required = false) final String airport) {
        if (airport != null) {
            return airportService.getFlights(airport);
        } else return airportService.getFlights();
    }
}
