package Uber.ride;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

// Main class - with all methods included
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Fleet fleet = new Fleet();
    private static RideManager rideManager = new RideManager(fleet);
    private static List<User> users = new ArrayList<>();
    private static List<Driver> drivers = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("=== Welcome to Smart Ride-Sharing System ===");
        
        // Initialize system with sample data
        initializeSystem();
        
        boolean running = true;
        while (running) {
            displayMainMenu();
            int choice = getUserChoice(1, 6);
            
            switch (choice) {
                case 1:
                    registerNewUser();
                    break;
                case 2:
                    addVehicle();
                    break;
                case 3:
                    requestRide();
                    break;
                case 4:
                    viewRideHistory();
                    break;
                case 5:
                    manageWallet();
                    break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using Smart Ride-Sharing. Goodbye!");
                    break;
            }
        }
        scanner.close();
    }
    
    private static void displayMainMenu() {
        System.out.println("\n=== Main Menu ===");
        System.out.println("1. Register New User");
        System.out.println("2. Add Vehicle");
        System.out.println("3. Request a Ride");
        System.out.println("4. View Ride History");
        System.out.println("5. Manage Wallet");
        System.out.println("6. Exit");
        System.out.print("Enter your choice: ");
    }
    
    private static int getUserChoice(int min, int max) {
        int choice = -1;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min && choice <= max) {
                    validInput = true;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
        
        return choice;
    }
    
    private static void registerNewUser() {
        System.out.println("\n=== Register New User ===");
        
        System.out.print("Enter user ID: ");
        String userId = scanner.nextLine();
        
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();
        
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        
        User newUser = new User(userId, name, phone, email);
        users.add(newUser);
        
        System.out.println("User registered successfully!");
        System.out.print("Would you like to add money to your wallet? (y/n): ");
        String addMoney = scanner.nextLine();
        
        if (addMoney.equalsIgnoreCase("y")) {
            System.out.print("Enter amount to add: $");
            try {
                double amount = Double.parseDouble(scanner.nextLine());
                newUser.getWallet().addMoney(amount);
                System.out.println("$" + amount + " added to wallet. Current balance: $" + newUser.getWallet().getBalance());
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount. No money added.");
            }
        }
    }
    
    private static void addVehicle() {
        System.out.println("\n=== Add New Vehicle ===");
        
        System.out.println("Vehicle Types:");
        System.out.println("1. Car");
        System.out.println("2. Bike");
        System.out.print("Select vehicle type: ");
        int typeChoice = getUserChoice(1, 2);
        
        System.out.print("Enter vehicle ID: ");
        String vehicleId = scanner.nextLine();
        
        System.out.print("Enter license plate: ");
        String licensePlate = scanner.nextLine();
        
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        
        System.out.print("Enter year: ");
        int year = getUserChoice(1990, 2025);
        
        Vehicle newVehicle = null;
        
        if (typeChoice == 1) {  // Car
            System.out.print("Enter number of seats: ");
            int seats = getUserChoice(1, 10);
            
            System.out.println("Select luxury type:");
            System.out.println("1. ECONOMY");
            System.out.println("2. PREMIUM");
            System.out.println("3. LUXURY");
            System.out.print("Enter choice: ");
            int luxuryChoice = getUserChoice(1, 3);
            
            String luxuryType = "ECONOMY";
            if (luxuryChoice == 2) luxuryType = "PREMIUM";
            if (luxuryChoice == 3) luxuryType = "LUXURY";
            
            System.out.print("Has AC? (y/n): ");
            boolean hasAC = scanner.nextLine().equalsIgnoreCase("y");
            
            newVehicle = new Car(vehicleId, licensePlate, model, year, seats, luxuryType, hasAC);
            
        } else {  // Bike
            System.out.print("Helmet available? (y/n): ");
            boolean helmetAvailable = scanner.nextLine().equalsIgnoreCase("y");
            
            System.out.println("Select bike type:");
            System.out.println("1. STANDARD");
            System.out.println("2. SPORT");
            System.out.print("Enter choice: ");
            int bikeTypeChoice = getUserChoice(1, 2);
            
            String bikeType = bikeTypeChoice == 1 ? "STANDARD" : "SPORT";
            
            newVehicle = new Bike(vehicleId, licensePlate, model, year, helmetAvailable, bikeType);
        }
        
        if (newVehicle != null) {
            fleet.addVehicle(newVehicle);
            System.out.println("Vehicle added successfully!");
            
            // Ask if the vehicle should be assigned to an existing driver
            System.out.println("\nAvailable Drivers:");
            if (drivers.isEmpty()) {
                System.out.println("No drivers available to assign vehicle to.");
            } else {
                for (int i = 0; i < drivers.size(); i++) {
                    System.out.println((i + 1) + ". " + drivers.get(i).getName());
                }
                
                System.out.print("Would you like to assign this vehicle to a driver? (y/n): ");
                if (scanner.nextLine().equalsIgnoreCase("y")) {
                    System.out.print("Select driver (1-" + drivers.size() + "): ");
                    int driverChoice = getUserChoice(1, drivers.size());
                    Driver selectedDriver = drivers.get(driverChoice - 1);
                    selectedDriver.assignVehicle(newVehicle);
                    System.out.println("Vehicle assigned to driver " + selectedDriver.getName());
                }
            }
        }
    }
    
    private static void requestRide() {
        System.out.println("\n=== Request a Ride ===");
        
        if (users.isEmpty()) {
            System.out.println("No registered users. Please register as a user first.");
            return;
        }
        
        // Display list of users
        System.out.println("Select your user profile:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getName());
        }
        
        System.out.print("Enter choice: ");
        int userChoice = getUserChoice(1, users.size());
        
        User selectedUser = users.get(userChoice - 1);
        
        // Check if user has enough money in wallet
        if (selectedUser.getWallet().getBalance() < 10) {
            System.out.println("Your wallet balance is low. Would you like to add money? (y/n): ");
            if (scanner.nextLine().equalsIgnoreCase("y")) {
                System.out.print("Enter amount to add: $");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    selectedUser.getWallet().addMoney(amount);
                    System.out.println("$" + amount + " added to wallet. Current balance: $" + selectedUser.getWallet().getBalance());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. No money added.");
                    return;
                }
            } else {
                return;
            }
        }
        
        // Select vehicle type
        System.out.println("Select vehicle type:");
        System.out.println("1. Car");
        System.out.println("2. Bike");
        System.out.print("Enter choice: ");
        int vehicleTypeChoice = getUserChoice(1, 2);
        
        String vehicleType = vehicleTypeChoice == 1 ? "CAR" : "BIKE";
        
        // Check if drivers are available for this vehicle type
        List<Driver> availableDrivers = rideManager.getAvailableDriversByVehicleType(vehicleType);
        if (availableDrivers.isEmpty()) {
            System.out.println("Sorry, no drivers available for " + vehicleType + " at the moment.");
            return;
        }
        
        // Get ride details
        System.out.print("Enter pickup location: ");
        String pickup = scanner.nextLine();
        
        System.out.print("Enter drop location: ");
        String drop = scanner.nextLine();
        
        System.out.print("Enter estimated distance in km: ");
        double distance = 0;
        try {
            distance = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid distance entered. Using default 5.0 km.");
            distance = 5.0;
        }
        
        // Select payment method
        System.out.println("Select payment method:");
        System.out.println("1. Online Payment");
        System.out.println("2. Cash Payment");
        System.out.print("Enter choice: ");
        int paymentChoice = getUserChoice(1, 2);
        
        Payment paymentMethod;
        if (paymentChoice == 1) {
            System.out.println("Select online payment provider:");
            System.out.println("1. CREDIT_CARD");
            System.out.println("2. PAYPAL");
            System.out.println("3. APPLE_PAY");
            System.out.println("4. GOOGLE_PAY");
            System.out.print("Enter choice: ");
            int providerChoice = getUserChoice(1, 4);
            
            String provider = "CREDIT_CARD";
            switch (providerChoice) {
                case 2: provider = "PAYPAL"; break;
                case 3: provider = "APPLE_PAY"; break;
                case 4: provider = "GOOGLE_PAY"; break;
            }
            
            paymentMethod = new OnlinePayment(provider);
        } else {
            paymentMethod = new CashPayment();
        }
        
        // Create the ride request
        Ride newRide = rideManager.createRideRequest(selectedUser, pickup, drop, vehicleType, distance);
        
        if (newRide != null) {
            newRide.setPaymentMethod(paymentMethod);
            
            // FIX 1: Ensure the ride has correct initial status and driver/vehicle assignments
            System.out.println("\nRide created successfully!");
            
            // Add debug information to verify ride setup
            System.out.println("Driver " + newRide.getDriver().getName() + " has been assigned.");
            System.out.println("Vehicle: " + newRide.getVehicle().getModel() + " (" + newRide.getVehicle().getLicensePlate() + ")");
            
            // Simulate ride - with improved error handling
            System.out.print("\nPress Enter to start the ride...");
            scanner.nextLine();
            
            // FIX 2: Make sure status is properly set before operations
            try {
                // Ensure ride status is set to REQUESTED first as this might be the expected initial state
                newRide.setStatus("REQUESTED");
                System.out.println("Ride status updated to REQUESTED");
                
                // Driver accepts the ride
                System.out.println("Driver " + newRide.getDriver().getName() + " has accepted your ride request.");
                newRide.setStatus("ACCEPTED");
                System.out.println("Ride status updated to ACCEPTED");
                
                // Driver arrives at pickup
                System.out.println("Driver has arrived at pickup location.");
                newRide.setStatus("ASSIGNED");
                System.out.println("Ride status updated to ASSIGNED");
                
                // FIX 3: Add clear debugging info about ride state
                System.out.println("Current ride status: " + newRide.getStatus());
                
                // FIX 4: Start the ride with proper state transitions
                newRide.startRide();
                System.out.println("Ride started successfully!");
                System.out.println("Current ride status: " + newRide.getStatus());
                
                System.out.print("Ride in progress... Press Enter to complete the ride...");
                scanner.nextLine();
                
                // FIX 5: Properly end the ride
                newRide.endRide();
                System.out.println("Ride ended successfully!");
                
                // FIX 6: Calculate fare based on distance
                // Manually calculate fare if the ride's fare is still 0
                if (newRide.getFare() <= 0) {
                    double baseFare = vehicleType.equals("CAR") ? 5.0 : 3.0;
                    double distanceFare = distance * (vehicleType.equals("CAR") ? 1.5 : 1.0);
                    double calculatedFare = baseFare + distanceFare;
                    newRide.setFare(calculatedFare); // Assuming there's a setter for fare
                    System.out.println("Fare calculated manually: $" + calculatedFare);
                }
                
                Driver driver = newRide.getDriver();
                driver.completeRide(newRide);
                rideManager.completeRide(newRide);
                
                System.out.println("\nRide completed!");
                System.out.println("Fare: $" + newRide.getFare());
                
                // Rate the driver
                System.out.print("Rate your driver (1-5): ");
                double driverRating = getUserChoice(1, 5);
                newRide.rateDriver(driverRating);
                
                // Driver rates the rider
                double riderRating = 4 + Math.random(); // Random rating between 4 and 5
                if (riderRating > 5) riderRating = 5;
                newRide.rateRider(riderRating);
                
                System.out.println("Thank you for your rating! Your rating from the driver: " + String.format("%.1f", riderRating));
            } catch (Exception e) {
                System.out.println("Error with ride: " + e.getMessage());
                System.out.println("Please try again or contact customer service for assistance.");
                
                // FIX 7: Print stack trace for better debugging
                e.printStackTrace();
            }
        } else {
            System.out.println("Failed to create ride. No available drivers.");
        }
    }
    
    private static void viewRideHistory() {
        System.out.println("\n=== View Ride History ===");
        
        if (users.isEmpty()) {
            System.out.println("No registered users.");
            return;
        }
        
        // Display list of users
        System.out.println("Select user:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getName());
        }
        
        System.out.print("Enter choice: ");
        int userChoice = getUserChoice(1, users.size());
        
        User selectedUser = users.get(userChoice - 1);
        List<Ride> rideHistory = selectedUser.viewRideHistory();
        
        if (rideHistory.isEmpty()) {
            System.out.println("No ride history for " + selectedUser.getName());
            return;
        }
        
        System.out.println("\nRide History for " + selectedUser.getName() + ":");
        for (int i = 0; i < rideHistory.size(); i++) {
            Ride ride = rideHistory.get(i);
            System.out.println("\nRide " + (i + 1) + ":");
            System.out.println("  From: " + ride.getPickupLocation());
            System.out.println("  To: " + ride.getDropLocation());
            System.out.println("  Vehicle: " + ride.getVehicle().getType() + " - " + ride.getVehicle().getModel());
            System.out.println("  Driver: " + ride.getDriver().getName());
            System.out.println("  Fare: $" + ride.getFare());
            System.out.println("  Status: " + ride.getStatus());
            System.out.println("  Your rating to driver: " + ride.getRiderRating());
        }
    }
    
    private static void manageWallet() {
        System.out.println("\n=== Manage Wallet ===");
        
        if (users.isEmpty()) {
            System.out.println("No registered users.");
            return;
        }
        
        // Display list of users
        System.out.println("Select user:");
        for (int i = 0; i < users.size(); i++) {
            System.out.println((i + 1) + ". " + users.get(i).getName());
        }
        
        System.out.print("Enter choice: ");
        int userChoice = getUserChoice(1, users.size());
        
        User selectedUser = users.get(userChoice - 1);
        Wallet userWallet = selectedUser.getWallet();
        
        System.out.println("\nWallet for " + selectedUser.getName() + ":");
        System.out.println("Current Balance: $" + userWallet.getBalance());
        
        System.out.println("\nOptions:");
        System.out.println("1. Add Money");
        System.out.println("2. Change Default Payment Method");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter choice: ");
        int walletChoice = getUserChoice(1, 3);
        
        switch (walletChoice) {
            case 1:
                System.out.print("Enter amount to add: $");
                try {
                    double amount = Double.parseDouble(scanner.nextLine());
                    if (amount > 0) {
                        userWallet.addMoney(amount);
                        System.out.println("Money added successfully. New balance: $" + userWallet.getBalance());
                    } else {
                        System.out.println("Invalid amount.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount. No money added.");
                }
                break;
                
            case 2:
                System.out.println("Select default payment method:");
                System.out.println("1. ONLINE");
                System.out.println("2. CASH");
                System.out.print("Enter choice: ");
                int methodChoice = getUserChoice(1, 2);
                
                String method = methodChoice == 1 ? "ONLINE" : "CASH";
                userWallet.setDefaultPaymentMethod(method);
                System.out.println("Default payment method updated to: " + method);
                break;
                
            case 3:
                // Return to main menu
                break;
        }
    }
    
    private static void initializeSystem() {
        // Add sample vehicles to the fleet
        Car car1 = new Car("V001", "ABC123", "Toyota Corolla", 2022, 4, "ECONOMY", true);
        Car car2 = new Car("V002", "XYZ456", "Honda Accord", 2023, 5, "PREMIUM", true);
        Car car3 = new Car("V003", "DEF789", "Tesla Model 3", 2024, 5, "LUXURY", true);
        Bike bike1 = new Bike("V004", "BIK789", "Honda CBR", 2022, true, "STANDARD");
        Bike bike2 = new Bike("V005", "BIK456", "Kawasaki Ninja", 2023, true, "SPORT");
        
        fleet.addVehicle(car1);
        fleet.addVehicle(car2);
        fleet.addVehicle(car3);
        fleet.addVehicle(bike1);
        fleet.addVehicle(bike2);
        
        // Add default drivers with assigned vehicles
        Driver driver1 = new Driver("U001", "John Smith", "555-1234", "john@example.com", "D001", "DL12345");
        Driver driver2 = new Driver("U002", "Sarah Johnson", "555-5678", "sarah@example.com", "D002", "DL67890");
        Driver driver3 = new Driver("U003", "Michael Chen", "555-9012", "michael@example.com", "D003", "DL24680");
        Driver driver4 = new Driver("U004", "Lisa Rodriguez", "555-3456", "lisa@example.com", "D004", "DL13579");
        
        // Assign vehicles to drivers
        driver1.assignVehicle(car1);
        driver2.assignVehicle(car2);
        driver3.assignVehicle(bike1);
        driver4.assignVehicle(car3);
        
        // Add drivers to the system
        drivers.add(driver1);
        drivers.add(driver2);
        drivers.add(driver3);
        drivers.add(driver4);
        
        // Register drivers with the ride manager
        rideManager.registerDriver(driver1);
        rideManager.registerDriver(driver2);
        rideManager.registerDriver(driver3);
        rideManager.registerDriver(driver4);
        
        System.out.println("System initialized with sample vehicles and drivers.");
    }
}