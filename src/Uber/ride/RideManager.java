package Uber.ride;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class RideManager {
    private Fleet fleet;
    private List<Driver> availableDrivers;
    private List<Ride> activeRides;
    private List<Ride> completedRides;
    private static final double BASE_FARE = 5.0; // Minimum fare
    
    public RideManager(Fleet fleet) {
        this.fleet = fleet;
        this.availableDrivers = new ArrayList<>();
        this.activeRides = new ArrayList<>();
        this.completedRides = new ArrayList<>();
    }
    
    public Ride createRideRequest(User rider, String pickupLocation, String dropLocation, 
                                  String vehicleType, double estimatedDistance) {
        // Generate unique ride ID
        String rideId = "RIDE_" + UUID.randomUUID().toString().substring(0, 8);
        
        // Create new ride
        Ride ride = new Ride(rideId, rider, pickupLocation, dropLocation, estimatedDistance);
        
        // Find suitable driver
        Driver assignedDriver = assignDriver(ride, vehicleType);
        if (assignedDriver != null) {
            ride.setDriver(assignedDriver);
            activeRides.add(ride);
            System.out.println("Ride created and driver assigned. Ride ID: " + rideId);
            return ride;
        } else {
            System.out.println("No available drivers for the requested vehicle type.");
            return null;
        }
    }
    
    public Driver assignDriver(Ride ride, String vehicleType) {
        // Find available drivers with the requested vehicle type
        List<Driver> suitableDrivers = availableDrivers.stream()
            .filter(driver -> driver.isAvailable() && 
                   driver.getVehicle() != null && 
                   driver.getVehicle().getType().equalsIgnoreCase(vehicleType) &&
                   driver.getVehicle().isActive())
            .collect(Collectors.toList());
        
        if (!suitableDrivers.isEmpty()) {
            // Sort by driver rating (highest first)
            suitableDrivers.sort(Comparator.comparing(Driver::getDriverRating).reversed());
            
            // Assign the highest rated available driver
            Driver selectedDriver = suitableDrivers.get(0);
            selectedDriver.setAvailable(false);
            
            return selectedDriver;
        }
        
        return null;
    }
    
    public double calculateFare(Ride ride) {
        if (ride.getVehicle() != null) {
            // Use vehicle-specific fare calculation
            double calculatedFare = ride.getVehicle().calculateFare(ride.getDistance(), ride.getDuration());
            
            // Ensure minimum fare
            return Math.max(calculatedFare, BASE_FARE);
        } else {
            // Default calculation if vehicle not available
            return BASE_FARE + (ride.getDistance() * 1.5) + (ride.getDuration() * 0.2);
        }
    }
    
    public void completeRide(Ride ride) {
        if (activeRides.contains(ride)) {
            activeRides.remove(ride);
            completedRides.add(ride);
            
            // Make driver available again
            Driver driver = ride.getDriver();
            if (driver != null) {
                driver.setAvailable(true);
            }
        }
    }
    
    public void registerDriver(Driver driver) {
        if (driver != null && !availableDrivers.contains(driver)) {
            availableDrivers.add(driver);
        }
    }
    
    public void removeDriver(Driver driver) {
        availableDrivers.remove(driver);
    }
    
    public List<Ride> getActiveRides() {
        return new ArrayList<>(activeRides);
    }
    
    public List<Ride> getCompletedRides() {
        return new ArrayList<>(completedRides);
    }
    
    public List<Driver> getAvailableDrivers() {
        return availableDrivers.stream()
            .filter(Driver::isAvailable)
            .collect(Collectors.toList());
    }
    
    public List<Driver> getAvailableDriversByVehicleType(String vehicleType) {
        return availableDrivers.stream()
            .filter(driver -> driver.isAvailable() && 
                   driver.getVehicle() != null && 
                   driver.getVehicle().getType().equalsIgnoreCase(vehicleType))
            .collect(Collectors.toList());
    }
}