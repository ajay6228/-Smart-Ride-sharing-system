package Uber.ride;

public abstract class Vehicle {
    private String vehicleId;
    private String type;
    private String licensePlate;
    private String model;
    private int year;
    private boolean isActive;
    private double baseFarePerKm;
    
    public Vehicle(String vehicleId, String type, String licensePlate, String model, int year) {
        this.vehicleId = vehicleId;
        this.type = type;
        this.licensePlate = licensePlate;
        this.model = model;
        this.year = year;
        this.isActive = true;
    }
    
    public void startRide() {
        System.out.println("Starting ride with vehicle: " + licensePlate);
    }
    
    public void stopRide() {
        System.out.println("Stopping ride with vehicle: " + licensePlate);
    }
    
    // Abstract method to be implemented by child classes
    public abstract double calculateFare(double distance, double duration);
    
    // Getters and setters
    public String getVehicleId() {
        return vehicleId;
    }
    
    public String getType() {
        return type;
    }
    
    public String getLicensePlate() {
        return licensePlate;
    }
    
    public String getModel() {
        return model;
    }
    
    public int getYear() {
        return year;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public double getBaseFarePerKm() {
        return baseFarePerKm;
    }
    
    protected void setBaseFarePerKm(double baseFarePerKm) {
        this.baseFarePerKm = baseFarePerKm;
    }
}