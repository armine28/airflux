package com.nagel.service.impl;

import com.google.common.collect.ImmutableMap;
import com.nagel.api.response.OperatingInstruction;
import com.nagel.service.OperationInstructionService;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class OperationInstructionServiceImpl implements OperationInstructionService {
    private final ImmutableMap<String, List<OperatingInstruction>> registrationToOperations = ImmutableMap.<String, List<OperatingInstruction>>builder()
            .put("FL-0001", new ArrayList<>())
            .put("FL-0002", new ArrayList<>())
            .put("FL-0003", new ArrayList<>())
            .put("FL-0004", new ArrayList<>())
            .build();


    @Override
    public void addOperationInstruction(@NonNull final String registration, @NonNull final OperatingInstruction operatingInstruction) {
        registrationToOperations.get(registration).add(operatingInstruction);
    }

    @Override
    public Collection<OperatingInstruction> getOperationInstructions(@NonNull final String registration) {
        return registrationToOperations.get(registration);
    }
}
