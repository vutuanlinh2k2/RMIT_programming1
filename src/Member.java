
import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class Member {

    final private String memberId;
    final private String fullName;
    final private String phoneNumb;
    final private String address;
    final private String username;
    final private String password;
    //default membership type is "NONE"
    private String membership;
    double totalSpent;

    public Member(String memberId, String fullName, String phoneNumb, String address, String username, String password,
                  String membership, double totalSpent) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.phoneNumb = phoneNumb;
        this.address = address;
        this.username = username;
        this.password = password;
        this.membership = membership;
        this.totalSpent = totalSpent;
    }

    public static Member login() throws IOException {

        // set up scanner for user inputs
        Scanner scannerInput = new Scanner(System.in);

        System.out.println("\nLogging in as member!");

        // getting username
        System.out.println("Your Username: ");
        String usernameInput = scannerInput.nextLine();

        // getting user password
        System.out.println("Your password: ");
        String pwInput = scannerInput.nextLine();

        // setting up scanner for member.txt file
        Scanner scannerMember = new Scanner(new File("./member.txt"));

        // loop through the member.txt file to find matching username and password
        while (scannerMember.hasNextLine()) {
            String currentAdmin = scannerMember.nextLine();

            // getting the name and password of the current checking member
            String[] currentMemberAttrs = currentAdmin.split(",");
            String currentMemberUsername = currentMemberAttrs[2];
            String currentMemberPw = currentMemberAttrs[3];

            // if there was a match, finish executing the function and prompt user that they have successfully login
            if (usernameInput.equals(currentMemberUsername) && pwInput.equals(currentMemberPw)) {
                System.out.println("\nSuccessfully login as a member.\n");

                String currentMemberId = currentMemberAttrs[0];
                String currentMemberFullName = currentMemberAttrs[1];
                String currentMemberPhoneNumber = currentMemberAttrs[4];
                String currentMemberAddress = currentMemberAttrs[5];
                String currentMembership = currentMemberAttrs[6];
                String currentMemberTotalSpent = currentMemberAttrs[7];

                System.out.println("Full name: " + currentMemberFullName);
                System.out.println("Username: " + currentMemberUsername);
                System.out.println("Phone number: " + currentMemberPhoneNumber);
                System.out.println("Address: " + currentMemberAddress);
                System.out.println("Membership: " + currentMembership);
                System.out.println("Total spent: " + currentMemberTotalSpent + " mil VND\n");

                scannerMember.close();
                return new Member(currentMemberId, currentMemberFullName, currentMemberPhoneNumber, currentMemberAddress
                        , currentMemberUsername, currentMemberPw, currentMembership,
                        Double.parseDouble(currentMemberTotalSpent));
            }
        }

        // cannot find any matching username and password, prompt user that they entered the wrong username and password
        System.out.println("Wrong username and/or password! Please try again!");
        scannerMember.close();
        return null;
    }

    public void createOrder() throws IOException {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("\nPlease give us an address for delivery: ");
        String orderAddress = inputScanner.nextLine();

        HashMap<String, Integer> orderProductDetails = new HashMap<>();
        boolean continueAddingProduct = true;
        double total = 0;

        while (continueAddingProduct) {
            String productName = null;
            double productPrice = 0;

            boolean isGettingProduct = true;
            while (isGettingProduct) {

                Scanner scannerProduct = new Scanner(new File("./product.txt"));

                System.out.println("\nEnter the name of the product you want to purchase: ");
                String nameInput = inputScanner.nextLine();

                while (scannerProduct.hasNextLine()) {

                    String currentProduct = scannerProduct.nextLine();
                    String[] currentProductAttrs = currentProduct.split(",");
                    String currentProductName = currentProductAttrs[1];

                    if (currentProductName.equals(nameInput)) {
                        productName = currentProductName;
                        productPrice = Double.parseDouble(currentProductAttrs[2]);
                        isGettingProduct = false;
                    }
                }

                if (isGettingProduct) {
                    System.out.println("Cannot find the product with matching name. Please try again.");
                }
            }

            int num = InputValidator.getIntInput("Enter the amount that you want to buy: ",
                    "Please enter only number.");
            total += productPrice * num;
            orderProductDetails.put(productName, num);

            boolean validOption = false;

            System.out.println("Would you like to purchase more product (y/n): ");
            while (!validOption) {
                String isContinue = inputScanner.nextLine();

                if (isContinue.equals("n")) {
                    continueAddingProduct = false;
                    validOption = true;
                } else if (isContinue.equals("y")) {
                    validOption = true;
                } else {
                    System.out.println("Please enter only y (for yes) or n (for no).");
                    System.out.println("Would you like to purchase more product (y/n): ");
                }
            }
        }

        String orderId = UUID.randomUUID().toString();
        String orderDate = LocalDate.now().toString();
        String currentOrderProducts = orderProductDetails.keySet().toArray()[0].toString();
        String currentOrderProductAmounts = orderProductDetails.values().toArray()[0].toString();
        Object[] productsList = orderProductDetails.keySet().toArray();
        Object[] amountsList = orderProductDetails.values().toArray();

        for (int i = 1; i < orderProductDetails.size(); i++) {
            currentOrderProducts += ":" + productsList[i].toString();
        }
        for (int i = 1; i < orderProductDetails.size(); i++) {
            currentOrderProductAmounts += ":" + amountsList[i].toString();
        }

        double totalAfterDiscount = applyMembershipDiscount(total);

        String newOrder = String.join(",", orderId, this.getMemberId(), orderDate, orderAddress,
                currentOrderProducts, currentOrderProductAmounts, String.valueOf(totalAfterDiscount), "delivered");

        // append the line for the new product to the end of the product.txt file
        Writer output = new BufferedWriter(new FileWriter("./order.txt", true));
        output.append(System.lineSeparator() + newOrder);

        System.out.println("Successfully created a new order.\n");
        System.out.println("Your order info:");
        System.out.println("Order ID: " + orderId);
        System.out.println("Order date: " + orderDate);
        System.out.println("Order address: " + orderAddress);
        System.out.println("Order items:");
        for (int i = 0; i < productsList.length; i++) {
            System.out.println("\t" + productsList[i].toString() + " - " + amountsList[i].toString());
        }
        System.out.println("Total: " + totalAfterDiscount + " mil VND");
        output.close();

        updateMembership(totalAfterDiscount);
    }

    public void getOrderById() throws IOException {

        System.out.println("Getting orders by Id\n");

        // setup scanner for order.txt file
        Scanner scannerOrder = new Scanner(new File("./order.txt"));

        // set scanner for user input
        Scanner scannerInput = new Scanner(System.in);

        // getting customer id input
        System.out.println("Enter Order Id: ");
        String inputOrderId = scannerInput.nextLine();

        // a variable to check if there were any orders with this customerId
        boolean orderExisted = false;

        // going through the order.txt file
        while(scannerOrder.hasNextLine()) {

            // getting the customerId of each line
            String order = scannerOrder.nextLine();
            String[] orderAttrs = order.split(",");
            String orderId = orderAttrs[0];

            // if the order match with the customerId input, display info about it
            if (orderId.equals(inputOrderId)) {
                Order.displayOrderDetail(order);
                orderExisted = true;
            }
        }

        // if there were no orders with this customer id, prompt to admin
        if (!orderExisted) {
            System.out.println("Cannot find any order with this id!");
        }
    }

    public void getCurrentMemberDetail() {
        System.out.println("Full name: " + this.getFullName());
        System.out.println("Username: " + this.getUsername());
        System.out.println("Phone number: " + this.getPhoneNumb());
        System.out.println("Address: " + this.getAddress());
        System.out.println("Membership: " + this.getMembership());
        System.out.println("Total spent: " + this.getTotalSpent() + " mil VND\n");
    }

    private void updateMembership(double newSpending) throws IOException {

        double newTotalSpent = this.getTotalSpent() + newSpending;
        String newMemberShip;

        setTotalSpent(newTotalSpent);

        if (newTotalSpent >= 25) {
            newMemberShip = "Platinum";
        } else if (newTotalSpent >= 10) {
            newMemberShip = "Gold";
        } else if (newTotalSpent >= 5) {
            newMemberShip = "Silver";
        } else {
            newMemberShip = "Normal";
        }

        Scanner scannerMember = new Scanner(new File("./member.txt"));

        // create a writer for a temporary file to store updated data
        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // loop through each line of product.txt file
        while (scannerMember.hasNextLine()) {

            // get the name of each product
            String currentMember = scannerMember.nextLine();
            String[] currentMemberAttrs = currentMember.split(",");
            String currentMemberId = currentMemberAttrs[0];
            String currentMemberTotalSpent = currentMemberAttrs[7];

            // if the name is not matched with user input
            if (!currentMemberId.equals(this.getMemberId())) {
                // write the current line to the temp file
                writer.write(currentMember + (scannerMember.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }

            // updated the new price for the product
            String currentMembership = currentMemberAttrs[6];
            String updatedMember = currentMember.replace(currentMembership, newMemberShip)
                    .replace(currentMemberTotalSpent, String.valueOf(newTotalSpent));

            // write the updated line to the temp file
            writer.write(updatedMember + (scannerMember.hasNextLine() ? System.lineSeparator() : ""));

            // rename the temp file to product.txt, replacing the old one
            tempFile.renameTo(new File("./member.txt"));

            if (!newMemberShip.equals(this.getMembership())) {
                System.out.println("Congratulations! You have been upgraded to " + newMemberShip + " membership!");
            }
            setMembership(newMemberShip);
        }
        writer.close();
    }

    private double applyMembershipDiscount(double beforeDiscount) {
        switch(this.getMembership()) {
            case "Platinum":
                System.out.println("\nYou are a Platinum member. You get 15% discount.");
                return (Math.round((beforeDiscount * 0.85) * 100.0)) / 100.0;
            case "Gold":
                System.out.println("\nYou are a Gold member. You get 10% discount.");
                return (Math.round((beforeDiscount * 0.9) * 100.0)) / 100.0;
            case "Silver":
                System.out.println("\nYou are a Silver member. You get 5% discount.");
                return (Math.round((beforeDiscount * 0.95) * 100.0)) / 100.0;
            default:
                return beforeDiscount;
        }
    }

    public static boolean checkMemberExisted(String username) throws IOException {

        // a scanner for the member.txt file
        Scanner scannerMember = new Scanner(new File("./member.txt"));

        // loop through each line of the member.txt file
        while (scannerMember.hasNextLine()) {

            // get the username of the current checking member
            String currentMember = scannerMember.nextLine();
            String[] currentMemberAttrs = currentMember.split(",");
            String currentMemberUsername = currentMemberAttrs[2];

            // if the current member's name match with the input, return true
            if (username.equals(currentMemberUsername)) {
                return true;
            }
        }

        // no member's name match with the input, return false
        return false;
    }

    public static void displayMemberDetail(String memberInfo) {
        String [] memberAttrs = memberInfo.split(",");

        String memberFullName = memberAttrs[1];
        String memberUsername = memberAttrs[2];
        String memberPhone = memberAttrs[4];
        String memberAddress = memberAttrs[5];
        String membership = memberAttrs[6];
        String totalSpent = memberAttrs[7];

        System.out.println("Full name: " + memberFullName);
        System.out.println("Username: " + memberUsername);
        System.out.println("Phone number: " + memberPhone);
        System.out.println("Address: " + memberAddress);
        System.out.println("Membership: " + membership);
        System.out.println("Total spent: " + totalSpent + "\n");
    }

    public String getMemberId() {
        return memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public String getAddress() {
        return address;
    }

    public String getMembership() {
        return membership;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public void setTotalSpent(double totalSpent) {
        this.totalSpent = totalSpent;
    }
}