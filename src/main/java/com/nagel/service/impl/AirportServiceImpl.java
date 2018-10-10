package com.nagel.service.impl;

import com.google.common.collect.ImmutableMap;
import com.nagel.api.error.exception.AirportNotFoundException;
import com.nagel.api.response.Flight;
import com.nagel.common.AirportSign;
import com.nagel.service.AirportService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AirportServiceImpl implements AirportService {
    private final ImmutableMap<AirportSign, List<Flight>> airportToFlight = ImmutableMap.<AirportSign, List<Flight>>builder()
            .put(AirportSign.TXL, new ArrayList<>())
            .put(AirportSign.MUC, new ArrayList<>())
            .put(AirportSign.LHR, new ArrayList<>())
            .put(AirportSign.HAM, new ArrayList<>())
            .build();

    @Override
    public void addFlight(@NonNull final Flight flight) {
        airportToFlight.get(flight.getOrigin()).add(flight);
    }

    @Override
    public Collection<Flight> getFlights() {
        return airportToFlight.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<Flight> getFlights(@NonNull String city) {
        final AirportSign airportSign = AirportSign.lookupByCity(city);
        if (airportSign == null) {
            throw new AirportNotFoundException(String.format("Airport of city %s could not be found", city));
        }

        return airportToFlight.get(airportSign);
    }
}
