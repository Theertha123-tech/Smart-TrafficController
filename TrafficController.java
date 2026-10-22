
package com.traffic;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class TrafficController {

    private List<Vehicle> vehicles = new ArrayList<>();
    private int activeLane = -1;
    private int passedCount = 0;

    // Add a vehicle
    @PostMapping("/vehicles")
    public String addVehicle(@RequestBody Vehicle vehicle) {
        vehicles.add(vehicle);
        return "Vehicle added successfully!";
    }

    // View traffic details
    @GetMapping("/traffic")
    public Map<String, Object> getTraffic() {

        Map<String, Object> data = new LinkedHashMap<>();

        data.put("totalWaiting", vehicles.size());
        data.put("activeLane", activeLane);
        data.put("passedCount", passedCount);
        data.put("vehicles", vehicles);

        return data;
    }

    // Control the next traffic signal
    @PostMapping("/signal/next")
    public String nextSignal() {

        if (vehicles.isEmpty()) {
            return "No vehicles waiting!";
        }

        Vehicle selected = null;

        // Find an emergency vehicle first
        for (Vehicle v : vehicles) {
            if (!v.type.equalsIgnoreCase("normal")) {
                selected = v;
                break;
            }
        }

        // If no emergency vehicle exists, select the first vehicle
        if (selected == null) {
            selected = vehicles.get(0);
        }

        activeLane = selected.lane;
        vehicles.remove(selected);
        passedCount++;

        return selected.type + " vehicle "
                + selected.number + " passed!";
    }

    // Reset traffic
    @DeleteMapping("/reset")
    public String resetTraffic() {
        vehicles.clear();
        activeLane = -1;
        passedCount = 0;

        return "Traffic reset successfully!";
    }

    // Vehicle details
    public static class Vehicle {
        public String number;
        public int lane;
        public String type;

        public Vehicle() {}

        public Vehicle(String number, int lane, String type) {
            this.number = number;
            this.lane = lane;
            this.type = type;
        }
    }
}
