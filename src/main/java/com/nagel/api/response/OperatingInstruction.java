package com.nagel.api.response;

import com.nagel.common.AirportSign;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class OperatingInstruction {
    @NonNull
    private final AirportSign origin;
    @NonNull
    private final AirportSign operation;
    @NonNull
    private final LocalDateTime departure;
}
