package com.nagel.config;

import com.google.common.collect.ImmutableMap;
import com.nagel.api.response.Flight;
import com.nagel.api.response.OperatingInstruction;
import com.nagel.common.Airplane;
import com.nagel.common.AirplaneConstructor;
import com.nagel.common.AirportSign;
import com.nagel.service.AirportService;
import com.nagel.service.OperationInstructionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

@SpringBootApplication(scanBasePackages = {"com.nagel"})
@Slf4j
public class Application {

    @Autowired
    private AirportService airportService;

    @Autowired
    private OperationInstructionService operationInstructionService;

    // only needed to store temporarily which airplanes are at which airport
    private final Map<AirportSign, Stack<Airplane>> airports = ImmutableMap.<AirportSign, Stack<Airplane>>builder()
            .put(AirportSign.TXL, new Stack<>())
            .put(AirportSign.MUC, new Stack<>())
            .put(AirportSign.HAM, new Stack<>())
            .put(AirportSign.LHR, new Stack<>())
            .build();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @PostConstruct
    private void initSchedule() {
        log.debug("Begin to setup data");

        Map<String, Airplane> registrations = ImmutableMap.<String, Airplane>builder()
                .put("FL-0001", new Airplane(AirplaneConstructor.BOING, "737", "FL-0001"))
                .put("FL-0002", new Airplane(AirplaneConstructor.AIRBUS, "A321", "FL-0002"))
                .put("FL-0003", new Airplane(AirplaneConstructor.BOING, "747-400", "FL-0003"))
                .put("FL-0004", new Airplane(AirplaneConstructor.AIRBUS, "A320", "FL-0004"))
                .build();
        // initial setup
        airports.get(AirportSign.TXL).push(registrations.get("FL-0001"));
        airports.get(AirportSign.MUC).push(registrations.get("FL-0002"));
        airports.get(AirportSign.LHR).push(registrations.get("FL-0003"));
        airports.get(AirportSign.HAM).push(registrations.get("FL-0004"));

        // parse flight dates arrange ranges
        scheduleFlightDates();
    }

    private void scheduleFlightDates() {
        // TODO this has to be done via file parsing and make it more flexible also for testing
        // TODO move to helper class

        final ImmutableMap.Builder<AirportSign, List<Schedule>> scheduleMapBuilder = ImmutableMap.<AirportSign, List<Schedule>>builder();
        List<Schedule> tmpSchedules = new ArrayList<>();
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 10, 0, 0), AirportSign.MUC, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 15, 0, 0), AirportSign.MUC, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 16, 0, 0), AirportSign.MUC, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 18, 0, 0), AirportSign.MUC, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 21, 0, 0), AirportSign.HAM, Duration.of(40, ChronoUnit.MINUTES)));

        scheduleMapBuilder.put(AirportSign.TXL, tmpSchedules);

        tmpSchedules = new ArrayList<>();
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 10, 0, 0), AirportSign.LHR, Duration.of(2, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 13, 0, 0), AirportSign.TXL, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 15, 0, 0), AirportSign.TXL, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 15, 30, 0), AirportSign.LHR, Duration.of(2, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 17, 30, 0), AirportSign.HAM, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 18, 0, 0), AirportSign.LHR, Duration.of(150, ChronoUnit.MINUTES)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 20, 0, 0), AirportSign.LHR, Duration.of(2, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 22, 0, 0), AirportSign.TXL, Duration.of(1, ChronoUnit.HOURS)));

        scheduleMapBuilder.put(AirportSign.MUC, tmpSchedules);

        tmpSchedules = new ArrayList<>();
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 9, 0, 0), AirportSign.HAM, Duration.of(150, ChronoUnit.MINUTES)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 12, 0, 0), AirportSign.TXL, Duration.of(2, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 17, 0, 0), AirportSign.TXL, Duration.of(2, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 20, 30, 0), AirportSign.MUC, Duration.of(2, ChronoUnit.HOURS)));

        scheduleMapBuilder.put(AirportSign.LHR, tmpSchedules);

        tmpSchedules = new ArrayList<>();
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 10, 0, 0), AirportSign.MUC, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 13, 0, 0), AirportSign.MUC, Duration.of(1, ChronoUnit.HOURS)));
        tmpSchedules.add(new Schedule(LocalDateTime.of(2018, 4, 13, 20, 0, 0), AirportSign.MUC, Duration.of(1, ChronoUnit.HOURS)));

        scheduleMapBuilder.put(AirportSign.HAM, tmpSchedules);
        final Map<AirportSign, List<Schedule>> schedules = scheduleMapBuilder.build();

        // airports to work on, needed to stop loop
        final Set<AirportSign> todo = new HashSet<>();
        todo.add(AirportSign.MUC);
        todo.add(AirportSign.TXL);
        todo.add(AirportSign.LHR);
        todo.add(AirportSign.HAM);

        // iterate over input schedules to setup time ranges, where airplanes are from to
        while (!todo.isEmpty()) {
            schedules.keySet().forEach(sign -> {
                List<Schedule> list = schedules.get(sign);

                if (list.isEmpty()) {
                    todo.remove(sign);
                }

                Stack<Airplane> availableAirplanes = airports.get(sign);
                while (!availableAirplanes.isEmpty()) {
                    // all schedules fulfilled already
                    if (list.isEmpty()) {
                        break;
                    }

                    // airplanes have to switch to new destination
                    final Airplane airplane = availableAirplanes.pop();
                    final Schedule schedule = list.get(0);
                    airports.get(schedule.getDestination()).push(airplane);

                    LocalDateTime arrival = schedule.getDeparture().plus(schedule.getDuration());

                    airportService.addFlight(new Flight(sign, schedule.getDestination(), schedule.getDeparture(), arrival, airplane.getRegistration()));
                    operationInstructionService.addOperationInstruction(airplane.getRegistration(), new OperatingInstruction(sign, schedule.destination, schedule.getDeparture()));

                    list.remove(schedule);
                }
            });
        }
        log.debug("Done with setting up data");
    }

    // internal helper class to build structure
    @AllArgsConstructor
    @Getter
    private class Schedule {
        private LocalDateTime departure;
        private AirportSign destination;
        private Duration duration;
    }
}