package Uber.ride;

public class CashPayment implements Payment {
    private double cashReceived;
    private double changeGiven;
    
    public CashPayment() {
        this.cashReceived = 0;
        this.changeGiven = 0;
    }
    
    @Override
    public boolean processPayment(Ride ride) {
        System.out.println("Processing CASH payment for ride: " + ride.getRideId());
        System.out.println("Amount to be collected: $" + ride.getFare());
        
        // In real implementation, driver would confirm receipt of cash
        this.cashReceived = ride.getFare();
        
        // Mark the payment as complete
        System.out.println("Cash payment of $" + cashReceived + " received successfully.");
        return true;
    }
    
    @Override
    public String getPaymentMethod() {
        return "CASH";
    }
    
    @Override
    public double calculateFee(double amount) {
        // Cash payments typically don't have processing fees
        return 0;
    }
    
    public void setCashReceived(double cashReceived) {
        this.cashReceived = cashReceived;
    }
    
    public double getCashReceived() {
        return cashReceived;
    }
    
    public void setChangeGiven(double changeGiven) {
        this.changeGiven = changeGiven;
    }
    
    public double getChangeGiven() {
        return changeGiven;
    }
}
