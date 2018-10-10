package com.nagel.api.response;

import com.nagel.common.AirportSign;
import lombok.*;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Flight {
    @NonNull
    private final AirportSign origin;
    @NonNull
    private final AirportSign destination;
    @NonNull
    private final LocalDateTime departure;
    @NonNull
    private final LocalDateTime arrival;
    @NonNull
    private final String equipment;

}
