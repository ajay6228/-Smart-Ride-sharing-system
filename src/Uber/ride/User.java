package Uber.ride;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userId;
    private String name;
    private String phoneNumber;
    private String email;
    private Wallet wallet; // Composition
    private List<Ride> rideHistory;
    private double rating;
    
    public User(String userId, String name, String phoneNumber, String email) {
        this.userId = userId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.wallet = new Wallet(); // Composition - User creates and owns a Wallet
        this.rideHistory = new ArrayList<>();
        this.rating = 5.0; // Default rating
    }
    
    public Ride requestRide(String pickupLocation, String dropLocation, String vehicleType) {
        // Logic to request a ride
        System.out.println(name + " is requesting a ride from " + pickupLocation + " to " + dropLocation);
        // Would call RideManager to create a ride request
        return null; // In a real system, this would return the created ride
    }
    
    public List<Ride> viewRideHistory() {
        return new ArrayList<>(rideHistory);
    }
    
    public void addRideToHistory(Ride ride) {
        rideHistory.add(ride);
    }
    
    // Getters and setters
    public String getUserId() {
        return userId;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public Wallet getWallet() {
        return wallet;
    }
    
    public double getRating() {
        return rating;
    }
    
    public void updateRating(double newRating) {
        // Logic to update user rating based on new feedback
        this.rating = (this.rating + newRating) / 2;
    }
}