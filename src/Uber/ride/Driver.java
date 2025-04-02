package Uber.ride;

import java.util.ArrayList;
import java.util.List;

public class Driver extends User {
    private String driverId;
    private Vehicle vehicle; // Aggregation - Driver is associated with a Vehicle
    private boolean isAvailable;
    private List<Ride> completedRides;
    private double driverRating;
    private String licenseNumber;
    
    public Driver(String userId, String name, String phoneNumber, String email, 
                 String driverId, String licenseNumber) {
        super(userId, name, phoneNumber, email);
        this.driverId = driverId;
        this.licenseNumber = licenseNumber;
        this.isAvailable = true;
        this.completedRides = new ArrayList<>();
        this.driverRating = 5.0; // Default rating
    }
    
    public boolean acceptRide(Ride ride) {
        if (isAvailable && vehicle != null) {
            System.out.println("Driver " + getName() + " accepted ride request from " + 
                              ride.getRider().getName());
            ride.setDriver(this);
            ride.setStatus("ACCEPTED");
            isAvailable = false;
            return true;
        }
        return false;
    }
    
    public void completeRide(Ride ride) {
        ride.endRide();
        completedRides.add(ride);
        isAvailable = true;
        
        // Add earnings to driver's wallet
        double driverEarning = ride.getFare() * 0.8; // 80% to driver, 20% to platform
        getWallet().addMoney(driverEarning);
        
        System.out.println("Driver " + getName() + " completed ride. Earned: $" + driverEarning);
    }
    
    public void assignVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public String getDriverId() {
        return driverId;
    }
    
    public boolean isAvailable() {
        return isAvailable;
    }
    
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    
    public List<Ride> getCompletedRides() {
        return completedRides;
    }
    
    public double getDriverRating() {
        return driverRating;
    }
    
    public void updateDriverRating(double rating) {
        // Calculate new average rating
        int totalRides = completedRides.size();
        if (totalRides > 0) {
            driverRating = ((driverRating * (totalRides - 1)) + rating) / totalRides;
        } else {
            driverRating = rating;
        }
    }
    
    public String getLicenseNumber() {
        return licenseNumber;
    }
}