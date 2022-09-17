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

    // 6 - a method to let the member create an order
    public void createOrder() throws IOException {

        // set up scanner for user inputs
        Scanner inputScanner = new Scanner(System.in);

        // getting the delivery address
        System.out.println("\nPlease give us an address for delivery: ");
        String orderAddress = inputScanner.nextLine();

        // a hash map to store each product and its quantity
        HashMap<String, Integer> orderProductDetails = new HashMap<>();

        // tracking to total cost of the order
        double total = 0;

        // a boolean to track if the user want to add more product to the order
        boolean continueAddingProduct = true;

        // a while loop to let user select products and quantity to add to the order
        while (continueAddingProduct) {

            // setting up product variables
            String productName = null;
            double productPrice = 0;

            // a boolean to track if there was a product was found with users' input
            boolean productFound = false;

            // a loop to ask user to select an existed product
            while (!productFound) {

                // a scanner for product.txt file
                Scanner scannerProduct = new Scanner(new File("./src/product.txt"));

                // getting the product name from users
                System.out.println("\nEnter the name of the product you want to purchase: ");
                String nameInput = inputScanner.nextLine();

                // a loop to go through each line in the product.txt file
                while (scannerProduct.hasNextLine()) {

                    // getting the name of each product
                    String currentProduct = scannerProduct.nextLine();
                    String[] currentProductAttrs = currentProduct.split(",");
                    String currentProductName = currentProductAttrs[1];

                    // if the current product's name match with the input, set the product variables
                    if (currentProductName.equals(nameInput)) {
                        productName = currentProductName;
                        productPrice = Double.parseDouble(currentProductAttrs[2]);
                        productFound = true;
                    }
                }

                // if there was no matching product was found, prompt to the user
                if (!productFound) {
                    System.out.println("Cannot find the product with matching name. Please try again.");
                }
            }

            // ask the user for the quantity of the product they want to purchase
            int num = InputValidator.getIntInput("Enter the amount that you want to buy: ",
                    "Please enter only number.");

            // update the total cost of the order
            total += productPrice * num;

            // update map with the product and its quantity
            orderProductDetails.put(productName, num);

            // a boolean to track if the user enter the right option
            boolean validOption = false;

            System.out.println("Would you like to purchase more product (y/n): ");

            // a loop to ask user want to add more product to the order or not
            while (!validOption) {

                // getting the user option
                String isContinue = inputScanner.nextLine();

                // if user enter n, set the continueAddingProduct to false
                if (isContinue.equals("n")) {
                    continueAddingProduct = false;
                    validOption = true;
                }
                // if user y, let them continue to add another product
                else if (isContinue.equals("y")) {
                    validOption = true;
                }
                // user enter invalid input, ask them to try again
                else {
                    System.out.println("Please enter only y (for yes) or n (for no).");
                    System.out.println("Would you like to purchase more product (y/n): ");
                }
            }
        }

        // generate new order id
        String orderId = UUID.randomUUID().toString();

        // getting the date for the order
        String orderDate = LocalDate.now().toString();

        // getting the list of all products in the order and their quantities
        Object[] productsList = orderProductDetails.keySet().toArray();
        Object[] amountsList = orderProductDetails.values().toArray();

        // generate strings to store the list of products and their quantities
        String currentOrderProducts = productsList[0].toString();
        String currentOrderProductAmounts = amountsList[0].toString();
        for (int i = 1; i < orderProductDetails.size(); i++) {
            currentOrderProducts += ":" + productsList[i].toString();
        }
        for (int i = 1; i < orderProductDetails.size(); i++) {
            currentOrderProductAmounts += ":" + amountsList[i].toString();
        }

        // applying discount to the total cost of the order
        double totalAfterDiscount = applyMembershipDiscount(total);

        // append the line for the new order to the end of the order.txt file
        String newOrder = String.join(",", orderId, this.getMemberId(), orderDate, orderAddress,
                currentOrderProducts, currentOrderProductAmounts, String.valueOf(totalAfterDiscount), "delivered");
        Writer output = new BufferedWriter(new FileWriter("./src/order.txt", true));
        output.append(System.lineSeparator() + newOrder);

        // let the user check back all the info of the product
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

        // update the membership for the user
        updateMembership(totalAfterDiscount);
    }

    // 7 - a method to let member login to their account
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
        Scanner scannerMember = new Scanner(new File("./src/member.txt"));

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

                // getting the user info
                String currentMemberId = currentMemberAttrs[0];
                String currentMemberFullName = currentMemberAttrs[1];
                String currentMemberPhoneNumber = currentMemberAttrs[4];
                String currentMemberAddress = currentMemberAttrs[5];
                String currentMembership = currentMemberAttrs[6];
                String currentMemberTotalSpent = currentMemberAttrs[7];

                // showing all the user info
                System.out.println("Full name: " + currentMemberFullName);
                System.out.println("Username: " + currentMemberUsername);
                System.out.println("Phone number: " + currentMemberPhoneNumber);
                System.out.println("Address: " + currentMemberAddress);
                System.out.println("Membership: " + currentMembership);
                System.out.println("Total spent: " + currentMemberTotalSpent + " mil VND\n");

                scannerMember.close();

                // create a new member
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

    public void getOrderById() throws IOException {

        System.out.println("Getting orders by Id\n");

        // setup scanner for order.txt file
        Scanner scannerOrder = new Scanner(new File("./src/order.txt"));

        // set scanner for user input
        Scanner scannerInput = new Scanner(System.in);

        // getting order id input
        System.out.println("Enter Order Id: ");
        String inputOrderId = scannerInput.nextLine();

        // a variable to check if there were any orders with this id
        boolean orderExisted = false;

        // going through the order.txt file
        while(scannerOrder.hasNextLine()) {

            // getting the order id of each order
            String order = scannerOrder.nextLine();
            String[] orderAttrs = order.split(",");
            String orderId = orderAttrs[0];

            // if the order match with the input id, display info about it
            if (orderId.equals(inputOrderId)) {
                Order.displayOrderDetail(order);
                orderExisted = true;
            }
        }

        // if there were no orders with this id, prompt to the user
        if (!orderExisted) {
            System.out.println("Cannot find any order with this id!");
        }
    }

    // a method to display all the info of the current member
    public void getCurrentMemberDetail() {
        System.out.println("Full name: " + this.getFullName());
        System.out.println("Username: " + this.getUsername());
        System.out.println("Phone number: " + this.getPhoneNumb());
        System.out.println("Address: " + this.getAddress());
        System.out.println("Membership: " + this.getMembership());
        System.out.println("Total spent: " + this.getTotalSpent() + " mil VND\n");
    }

    // a method to update the membership of the current member
    private void updateMembership(double newSpending) throws IOException {

        //  update the total money spent by the user
        double newTotalSpent = this.getTotalSpent() + newSpending;
        setTotalSpent(newTotalSpent);

        // getting the new membership of the current user
        String newMemberShip;
        if (newTotalSpent >= 25) {
            newMemberShip = "Platinum";
        } else if (newTotalSpent >= 10) {
            newMemberShip = "Gold";
        } else if (newTotalSpent >= 5) {
            newMemberShip = "Silver";
        } else {
            newMemberShip = "Normal";
        }

        // a scanner for the member.txt file
        Scanner scannerMember = new Scanner(new File("./src/member.txt"));

        // create a writer for a temporary file to store updated data
        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // loop through each line of member.txt file
        while (scannerMember.hasNextLine()) {

            // get the id of each product
            String currentMember = scannerMember.nextLine();
            String[] currentMemberAttrs = currentMember.split(",");
            String currentMemberId = currentMemberAttrs[0];

            // if the id is not matched with user input
            if (!currentMemberId.equals(this.getMemberId())) {
                // write the current line to the temp file
                writer.write(currentMember + (scannerMember.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }

            // getting the current membership and total spent of the current member
            String currentMembership = currentMemberAttrs[6];
            String currentMemberTotalSpent = currentMemberAttrs[7];

            // write the updated line to the temp file
            String updatedMember = currentMember.replace(currentMembership, newMemberShip)
                    .replace(currentMemberTotalSpent, String.valueOf(newTotalSpent));
            writer.write(updatedMember + (scannerMember.hasNextLine() ? System.lineSeparator() : ""));

            // rename the temp file to product.txt, replacing the old one
            tempFile.renameTo(new File("./src/member.txt"));

            // if there was a upgrade in the membership, congratulate the user
            if (!newMemberShip.equals(this.getMembership())) {
                System.out.println("Congratulations! You have been upgraded to " + newMemberShip + " membership!");
            }

            // setting the new membership for the current member
            setMembership(newMemberShip);
        }
        writer.close();
    }

    // a method to apply discount for the current member based on their membership
    private double applyMembershipDiscount(double beforeDiscount) {

        // applying different discount for different membership
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

    // a method to check if there is a member match with this username
    public static boolean checkMemberExisted(String username) throws IOException {

        // a scanner for the member.txt file
        Scanner scannerMember = new Scanner(new File("./src/member.txt"));

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

    // a static method to display information of a specific member
    public static void displayMemberDetail(String memberInfo) {

        // getting all the info of the member
        String [] memberAttrs = memberInfo.split(",");
        String memberFullName = memberAttrs[1];
        String memberUsername = memberAttrs[2];
        String memberPhone = memberAttrs[4];
        String memberAddress = memberAttrs[5];
        String membership = memberAttrs[6];
        String totalSpent = memberAttrs[7];

        // displaying all the info of the user
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