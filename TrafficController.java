package com.traffic;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TrafficController {

    private static final int NUMBER_OF_LANES = 4;

    private final List<LinkedList<Vehicle>> lanes =
            new ArrayList<>();

    private final PriorityQueue<Vehicle> emergencyQueue =
            new PriorityQueue<>(
                Comparator.comparingInt(Vehicle::priority)
                    .thenComparingLong(Vehicle::sequence)
            );

    private final AtomicLong sequence = new AtomicLong();

    private int activeLane = -1;
    private int passedCount = 0;

    public TrafficController() {
        for (int i = 0; i < NUMBER_OF_LANES; i++) {
            lanes.add(new LinkedList<>());
        }
    }

    @PostMapping("/vehicles")
    public Map<String, Object> addVehicle(
            @RequestBody VehicleRequest request) {

        if (request.number() == null
                || request.number().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Vehicle number is required");
        }

        if (request.lane() < 0
                || request.lane() >= NUMBER_OF_LANES) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Lane must be between 0 and 3");
        }

        if (request.type() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Vehicle type is required");
        }

        String type = request.type().toLowerCase(Locale.ROOT);

        if (!List.of("normal", "ambulance", "fire", "police")
                .contains(type)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid vehicle type");
        }

        Vehicle vehicle = new Vehicle(
                request.number().trim(),
                request.lane(),
                type,
                getPriority(type),
                sequence.incrementAndGet()
        );

        lanes.get(request.lane()).addLast(vehicle);

        if (!type.equals("normal")) {
            emergencyQueue.offer(vehicle);
        }

        return result("Vehicle added successfully: "
                + vehicle.number());
    }

    @GetMapping("/traffic")
    public Map<String, Object> getTraffic() {
        return trafficData();
    }

    @PostMapping("/signal/next")
    public Map<String, Object> nextSignal() {

        Vehicle vehicleToPass;

        if (!emergencyQueue.isEmpty()) {
            vehicleToPass = emergencyQueue.poll();

            lanes.get(vehicleToPass.lane())
                    .remove(vehicleToPass);

            activeLane = vehicleToPass.lane();

        } else {
            int busiestLane = -1;
            int maximumVehicles = 0;

            for (int i = 0; i < NUMBER_OF_LANES; i++) {
                int count = lanes.get(i).size();

                if (count > maximumVehicles) {
                    maximumVehicles = count;
                    busiestLane = i;
                }
            }

            if (busiestLane == -1) {
                activeLane = -1;
                return result("No vehicles waiting.");
            }

            activeLane = busiestLane;
            vehicleToPass = lanes.get(busiestLane).pollFirst();
        }

        passedCount++;

        String message;

        if (vehicleToPass.type().equals("normal")) {
            message = "Normal vehicle passed: "
                    + vehicleToPass.number();
        } else {
            message = "Emergency priority granted to "
                    + vehicleToPass.type().toUpperCase(Locale.ROOT)
                    + ": " + vehicleToPass.number();
        }

        return result(message);
    }

    @DeleteMapping("/reset")
    public Map<String, Object> reset() {

        for (LinkedList<Vehicle> lane : lanes) {
            lane.clear();
        }

        emergencyQueue.clear();
        activeLane = -1;
        passedCount = 0;

        return result("Traffic simulation reset successfully.");
    }

    private int getPriority(String type) {
        return switch (type) {
            case "ambulance" -> 1;
            case "fire" -> 2;
            case "police" -> 3;
            default -> 4;
        };
    }

    private Map<String, Object> result(String message) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("message", message);
        response.put("traffic", trafficData());
        return response;
    }

    private Map<String, Object> trafficData() {

        List<Map<String, Object>> laneData = new ArrayList<>();
        int totalWaiting = 0;

        for (int i = 0; i < NUMBER_OF_LANES; i++) {
            LinkedList<Vehicle> queue = lanes.get(i);
            int count = queue.size();

            totalWaiting += count;

            String density;

            if (count <= 3) {
                density = "Low";
            } else if (count <= 6) {
                density = "Medium";
            } else {
                density = "High";
            }

            Map<String, Object> lane = new LinkedHashMap<>();
            lane.put("lane", i);
            lane.put("count", count);
            lane.put("density", density);
            lane.put("vehicles", new ArrayList<>(queue));

            laneData.add(lane);
        }

        List<Vehicle> emergencyVehicles =
                new ArrayList<>(emergencyQueue);

        emergencyVehicles.sort(
                Comparator.comparingInt(Vehicle::priority)
                    .thenComparingLong(Vehicle::sequence)
        );

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("totalWaiting", totalWaiting);
        data.put("emergencyCount", emergencyQueue.size());
        data.put("activeLane", activeLane);
        data.put("passedCount", passedCount);
        data.put("lanes", laneData);
        data.put("emergencies", emergencyVehicles);

        return data;
    }

    public record VehicleRequest(
            String number,
            int lane,
            String type
    ) {}

    public record Vehicle(
            String number,
            int lane,
            String type,
            int priority,
            long sequence
    ) {}
}