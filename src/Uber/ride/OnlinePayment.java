package Uber.ride;

public class OnlinePayment implements Payment {
    private String paymentProvider; // "CREDIT_CARD", "PAYPAL", "APPLE_PAY", etc.
    private String transactionId;
    private double processingFeePercentage;
    
    public OnlinePayment(String paymentProvider) {
        this.paymentProvider = paymentProvider;
        
        // Set processing fee based on payment provider
        switch (paymentProvider) {
            case "CREDIT_CARD":
                this.processingFeePercentage = 2.5; // 2.5%
                break;
            case "PAYPAL":
                this.processingFeePercentage = 2.9; // 2.9%
                break;
            case "APPLE_PAY":
            case "GOOGLE_PAY":
                this.processingFeePercentage = 1.5; // 1.5%
                break;
            default:
                this.processingFeePercentage = 2.0; // Default
        }
    }
    
    @Override
    public boolean processPayment(Ride ride) {
        System.out.println("Processing " + paymentProvider + " payment for ride: " + ride.getRideId());
        
        double amount = ride.getFare();
        double fee = calculateFee(amount);
        double totalAmount = amount + fee;
        
        User rider = ride.getRider();
        Wallet riderWallet = rider.getWallet();
        
        if (riderWallet.hasEnoughBalance(totalAmount)) {
            // Deduct from rider's wallet
            boolean paymentSuccess = riderWallet.deductMoney(totalAmount);
            
            if (paymentSuccess) {
                // Generate a transaction ID
                this.transactionId = generateTransactionId(ride.getRideId());
                System.out.println(paymentProvider + " payment successful. Transaction ID: " + transactionId);
                return true;
            } else {
                System.out.println("Payment failed: Unable to deduct from wallet");
                return false;
            }
        } else {
            System.out.println("Payment failed: Insufficient funds in wallet");
            return false;
        }
    }
    
    @Override
    public String getPaymentMethod() {
        return paymentProvider;
    }
    
    @Override
    public double calculateFee(double amount) {
        return (amount * processingFeePercentage) / 100;
    }
    
    private String generateTransactionId(String rideId) {
        // Simple implementation to generate a transaction ID
        return "TXN_" + System.currentTimeMillis() + "_" + rideId;
    }
    
    public String getTransactionId() {
        return transactionId;
    }
    
    public double getProcessingFeePercentage() {
        return processingFeePercentage;
    }
}