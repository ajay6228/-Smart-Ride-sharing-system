package Uber.ride;

public class Wallet {
    private double balance;
    private String defaultPaymentMethod;
    
    public Wallet() {
        this.balance = 0.0;
        this.defaultPaymentMethod = "ONLINE";
    }
    
    public boolean addMoney(double amount) {
        if (amount > 0) {
            this.balance += amount;
            System.out.println("Added " + amount + " to wallet. New balance: " + balance);
            return true;
        }
        return false;
    }
    
    public boolean deductMoney(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
            System.out.println("Deducted " + amount + " from wallet. New balance: " + balance);
            return true;
        }
        return false;
    }
    
    public double getBalance() {
        return balance;
    }
    
    public String getDefaultPaymentMethod() {
        return defaultPaymentMethod;
    }
    
    public void setDefaultPaymentMethod(String defaultPaymentMethod) {
        this.defaultPaymentMethod = defaultPaymentMethod;
    }
    
    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }
}
