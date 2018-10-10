package com.nagel.service;

import com.nagel.api.response.Flight;

import java.util.Collection;

public interface AirportService {
    /**
     * Adds a flight.
     *
     * @param flight the flight to add
     */
    void addFlight(Flight flight);

    /**
     * Returns all scheduled flights.
     *
     * @return collection of flights
     */
    Collection<Flight> getFlights();

    /**
     * Returns all scheduled flights for specific airport (city).
     *
     * @param city the city of the
     * @return collection of flights
     */
    Collection<Flight> getFlights(String city);
}
