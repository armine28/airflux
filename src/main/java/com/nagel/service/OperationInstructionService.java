package com.nagel.service;

import com.nagel.api.response.OperatingInstruction;

import java.util.Collection;

public interface OperationInstructionService {
    /**
     * Adds operationInstruction
     *
     * @param registration         the registration to add instruction for
     * @param operatingInstruction the instruction
     */
    void addOperationInstruction(String registration, OperatingInstruction operatingInstruction);

    /**
     * Returns collection of operating instructions
     *
     * @param registration the registration to add the instructions for
     * @return collection of operating instructions
     */
    Collection<OperatingInstruction> getOperationInstructions(String registration);
}
