package Uber.ride;

public interface Payment {
    boolean processPayment(Ride ride);
    String getPaymentMethod();
    double calculateFee(double amount);
}