package Uber.ride;

public class Bike extends Vehicle {
    private boolean helmetAvailable;
    private String bikeType; // "STANDARD", "SPORT"
    
    public Bike(String vehicleId, String licensePlate, String model, int year,
               boolean helmetAvailable, String bikeType) {
        super(vehicleId, "BIKE", licensePlate, model, year);
        this.helmetAvailable = helmetAvailable;
        this.bikeType = bikeType;
        
        // Set base fare based on bike type
        if ("SPORT".equals(bikeType)) {
            setBaseFarePerKm(1.2);
        } else {
            setBaseFarePerKm(1.0);
        }
    }
    
    @Override
    public double calculateFare(double distance, double duration) {
        // Bikes have a simpler fare structure
        double baseFare = getBaseFarePerKm() * distance;
        
        // Time-based fare component (per minute) - lower than cars
        double timeFare = duration * 0.1;
        
        // Helmet surcharge (if helmet is provided)
        double helmetCharge = helmetAvailable ? 5 : 0;
        
        return baseFare + timeFare + helmetCharge;
    }
    
    // Getters and setters
    public boolean isHelmetAvailable() {
        return helmetAvailable;
    }
    
    public void setHelmetAvailable(boolean helmetAvailable) {
        this.helmetAvailable = helmetAvailable;
    }
    
    public String getBikeType() {
        return bikeType;
    }
}