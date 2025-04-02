package Uber.ride;

import java.time.LocalDateTime;

public class Ride {
    private String rideId;
    private User rider;
    private Driver driver;
    private Vehicle vehicle;
    private String pickupLocation;
    private String dropLocation;
    private double distance;
    private double fare;
    private String status; // "REQUESTED", "ACCEPTED", "IN_PROGRESS", "COMPLETED", "CANCELLED"
    private LocalDateTime requestTime;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Payment paymentMethod;
    private double duration; // in minutes
    private double riderRating; // Rating given by rider to driver
    private double driverRating; // Rating given by driver to rider
    
    public Ride(String rideId, User rider, String pickupLocation, String dropLocation, double distance) {
        this.rideId = rideId;
        this.rider = rider;
        this.pickupLocation = pickupLocation;
        this.dropLocation = dropLocation;
        this.distance = distance;
        this.status = "REQUESTED";
        this.requestTime = LocalDateTime.now();
        this.riderRating = 0;
        this.driverRating = 0;
    }
    
    public void startRide() {
        if (("ACCEPTED".equals(status) || "ASSIGNED".equals(status)) && driver != null && vehicle != null) {
            this.status = "IN_PROGRESS";
            this.startTime = LocalDateTime.now();
            if (vehicle != null) {
                vehicle.startRide();
            }
            System.out.println("Ride started from " + pickupLocation + " to " + dropLocation);
        } else {
            System.out.println("Cannot start ride - driver or vehicle not assigned or ride not accepted");
        }
    }
    
    public void endRide() {
        if ("IN_PROGRESS".equals(status)) {
            this.status = "COMPLETED";
            this.endTime = LocalDateTime.now();
            
            // Calculate duration in minutes
            this.duration = java.time.Duration.between(startTime, endTime).toMinutes();
            
            // Calculate fare based on vehicle type
            this.fare = vehicle.calculateFare(distance, duration);
            
            vehicle.stopRide();
            System.out.println("Ride completed. Fare: $" + fare);
            
            // Process payment
            if (paymentMethod != null) {
                paymentMethod.processPayment(this);
            }
            
            // Add to ride history of both rider and driver
            rider.addRideToHistory(this);
        } else {
            System.out.println("Cannot end ride - ride not in progress");
        }
    }
    
    public void cancelRide() {
        if ("REQUESTED".equals(status) || "ACCEPTED".equals(status)) {
            this.status = "CANCELLED";
            System.out.println("Ride cancelled");
        } else {
            System.out.println("Cannot cancel ride - ride already in progress or completed");
        }
    }
    
    public void setPaymentMethod(Payment paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public void rateDriver(double rating) {
        if ("COMPLETED".equals(status) && rating >= 1 && rating <= 5) {
            this.riderRating = rating;
            if (driver != null) {
                driver.updateDriverRating(rating);
            }
        }
    }
    
    public void rateRider(double rating) {
        if ("COMPLETED".equals(status) && rating >= 1 && rating <= 5) {
            this.driverRating = rating;
            if (rider != null) {
                rider.updateRating(rating);
            }
        }
    }
    
    // Getters and setters
    public String getRideId() {
        return rideId;
    }
    
    public User getRider() {
        return rider;
    }
    
    public Driver getDriver() {
        return driver;
    }
    
    public void setDriver(Driver driver) {
        this.driver = driver;
        if (driver != null) {
            this.vehicle = driver.getVehicle();
            if (this.vehicle == null) {
                System.out.println("Warning: Driver has no assigned vehicle");
            }
        }
    }
    
    public Vehicle getVehicle() {
        return vehicle;
    }
    
    public String getPickupLocation() {
        return pickupLocation;
    }
    
    public String getDropLocation() {
        return dropLocation;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public double getFare() {
        return fare;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public LocalDateTime getRequestTime() {
        return requestTime;
    }
    
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public double getDuration() {
        return duration;
    }
    
    public double getRiderRating() {
        return riderRating;
    }
    
    public double getDriverRating() {
        return driverRating;
    }

    public void setFare(double calculatedFare) {
        this.fare = calculatedFare;
    }
}