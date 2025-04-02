package Uber.ride;

public class Car extends Vehicle {
    private int numSeats;
    private String luxuryType; // "ECONOMY", "PREMIUM", "LUXURY"
    private boolean hasAC;
    
    public Car(String vehicleId, String licensePlate, String model, int year, 
              int numSeats, String luxuryType, boolean hasAC) {
        super(vehicleId, "CAR", licensePlate, model, year);
        this.numSeats = numSeats;
        this.luxuryType = luxuryType;
        this.hasAC = hasAC;
        
        // Set base fare based on luxury type
        switch (luxuryType) {
            case "ECONOMY":
                setBaseFarePerKm(1.5);
                break;
            case "PREMIUM":
                setBaseFarePerKm(2.0);
                break;
            case "LUXURY":
                setBaseFarePerKm(3.0);
                break;
            default:
                setBaseFarePerKm(1.5);
        }
    }
    
    @Override
    public double calculateFare(double distance, double duration) {
        // Base fare calculation
        double baseFare = getBaseFarePerKm() * distance;
        
        // Time-based fare component (per minute)
        double timeFare = duration * 0.2;
        
        // Luxury multiplier
        double luxuryMultiplier = 1.0;
        switch (luxuryType) {
            case "PREMIUM":
                luxuryMultiplier = 1.2;
                break;
            case "LUXURY":
                luxuryMultiplier = 1.5;
                break;
        }
        
        // AC surcharge
        double acSurcharge = hasAC ? 10 : 0;
        
        return (baseFare + timeFare) * luxuryMultiplier + acSurcharge;
    }
    
    // Getters and setters
    public int getNumSeats() {
        return numSeats;
    }
    
    public String getLuxuryType() {
        return luxuryType;
    }
    
    public boolean hasAC() {
        return hasAC;
    }
    
    public void setHasAC(boolean hasAC) {
        this.hasAC = hasAC;
    }
}