package com.nagel.common;

import lombok.*;

@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Getter
public class Airplane {
    @NonNull
    private final AirplaneConstructor airplaneConstructor;
    @NonNull
    private final String type;
    @NonNull
    private final String registration;
}

