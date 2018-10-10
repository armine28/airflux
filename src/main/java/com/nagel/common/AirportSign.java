package com.nagel.common;

import com.google.common.collect.Maps;

import java.util.Map;

public enum AirportSign {
    MUC("Muenchen"), // could end up in encoding issues
    TXL("Berlin"),
    LHR("London"),
    HAM("Hamburg");

    final String city;

    AirportSign(final String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    private static final Map<String, AirportSign> nameIndex =
            Maps.newHashMapWithExpectedSize(AirportSign.values().length);

    static {
        for (AirportSign airportSign : AirportSign.values()) {
            nameIndex.put(airportSign.getCity(), airportSign);
        }
    }

    public static AirportSign lookupByCity(String city) {
        return nameIndex.get(city);
    }
}
