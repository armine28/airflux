package com.nagel.api;

import com.nagel.api.response.OperatingInstruction;
import com.nagel.service.OperationInstructionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@Api("Help the flight crews to know where to fly to")
public class OperationsPlanController {
    @Autowired
    private OperationInstructionService operationInstructionService;

    @RequestMapping(
            value = "/operatinginstructions",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public Collection<OperatingInstruction> getOperatingInstructions(@ApiParam(required = true) @RequestParam(value = "registration") final String registration) {
        return operationInstructionService.getOperationInstructions(registration);
    }
}
