package Uber.ride;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Fleet {
    private List<Vehicle> listOfVehicles;
    
    public Fleet() {
        this.listOfVehicles = new ArrayList<>();
    }
    
    public boolean addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            // Check if vehicle with same ID already exists
            boolean vehicleExists = listOfVehicles.stream()
                .anyMatch(v -> v.getVehicleId().equals(vehicle.getVehicleId()));
            
            if (!vehicleExists) {
                listOfVehicles.add(vehicle);
                System.out.println("Vehicle added to fleet: " + vehicle.getLicensePlate());
                return true;
            } else {
                System.out.println("Vehicle with ID " + vehicle.getVehicleId() + " already exists in fleet");
                return false;
            }
        }
        return false;
    }
    
    public boolean removeVehicle(String vehicleId) {
        Vehicle vehicleToRemove = listOfVehicles.stream()
            .filter(v -> v.getVehicleId().equals(vehicleId))
            .findFirst()
            .orElse(null);
        
        if (vehicleToRemove != null) {
            listOfVehicles.remove(vehicleToRemove);
            System.out.println("Vehicle removed from fleet: " + vehicleToRemove.getLicensePlate());
            return true;
        } else {
            System.out.println("Vehicle with ID " + vehicleId + " not found in fleet");
            return false;
        }
    }
    
    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(listOfVehicles);
    }
    
    public List<Vehicle> getAvailableVehicles() {
        return listOfVehicles.stream()
            .filter(Vehicle::isActive)
            .collect(Collectors.toList());
    }
    
    public List<Vehicle> getVehiclesByType(String type) {
        return listOfVehicles.stream()
            .filter(v -> v.getType().equalsIgnoreCase(type) && v.isActive())
            .collect(Collectors.toList());
    }
    
    public Vehicle getVehicleById(String vehicleId) {
        return listOfVehicles.stream()
            .filter(v -> v.getVehicleId().equals(vehicleId))
            .findFirst()
            .orElse(null);
    }
    
    public int getTotalVehicleCount() {
        return listOfVehicles.size();
    }
    
    public int getActiveVehicleCount() {
        return (int) listOfVehicles.stream()
            .filter(Vehicle::isActive)
            .count();
    }
}