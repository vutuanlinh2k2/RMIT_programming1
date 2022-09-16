import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {

        // display welcome message
        welcomeScreen();
        newCommandSeparator();

        // setting up scanner for user inputs
        Scanner scanner = new Scanner(System.in);

        String role;

        // a boolean to track if user has selected a valid role
        boolean roleSelected = false;

        // a loop to let user choose a role
        while (!roleSelected) {

            // getting user's option
            System.out.println("Please enter your role (admin / member / customer): ");
            role = scanner.nextLine();

            // provide different flows for different roles
            switch (role) {
                case "admin":
                    adminFlow();
                    roleSelected = true;
                    break;
                case "member":
                    memberFlow();
                    roleSelected = true;
                    break;
                case "customer":
                    customerFlow();
                    roleSelected = true;
                    break;
                // if no valid role was chosen, ask the user to try again
                default:
                    System.out.println("Please only choose between admin, member or customer!");
            }
        }
    }

    // customer flow
    static public void customerFlow() throws IOException {

        // create a new customer instance
        Customer customer = new Customer();

        // a boolean to check if the user want to continue running the program
        boolean isContinue = true;

        // a loop to let user perform different actions as a customer
        while (isContinue) {

            // showing different options for customer
            System.out.println("1 - register");
            System.out.println("2 - login");
            System.out.println("3 - see all product details");
            System.out.println("4 - search products by category");
            System.out.println("5 - sort product by price");
            System.out.println("0 - quit\n");

            // getting user option
            int option = InputValidator.getIntInput("Choose one of the actions above (0-5): ",
                    "Please only choose number 0-5");

            // perform different customer actions based on user's option
            switch (option) {
                case 1:
                    customer.registerMember();
                    newCommandSeparator();
                    break;
                case 2:
                    customer.login();
                    newCommandSeparator();
                    break;
                case 3:
                    customer.viewProduct();
                    newCommandSeparator();
                    break;
                case 4:
                    customer.searchProductByCategory();
                    newCommandSeparator();
                    break;
                case 5:
                    customer.sortProduct();
                    newCommandSeparator();
                    break;
                case 0:
                    isContinue = false;
                    System.out.println("Stopping the program.");
                    break;
                default:
                    System.out.println("\nNo action correspond to this number! Please enter 0-5 only.\n");
            }
        }
    }

    // member flow
    static public void memberFlow() throws IOException {

        // login in as member
        Member member = null;
        while (member == null) {
            member = Member.login();
        }

        // a boolean to check if the user want to continue running the program
        boolean isContinue = true;

        // a loop to let user perform different actions as a member
        while (isContinue) {

            // showing different options for member
            System.out.println("1 - create new order");
            System.out.println("2 - get order by id");
            System.out.println("3 - view account details");
            System.out.println("0 - quit\n");

            // getting user option
            int option = InputValidator.getIntInput("Choose one of the actions above (0-5): ",
                    "Please only choose number 0-5");

            // perform different customer actions based on user's option
            switch (option) {
                case 1:
                    member.createOrder();
                    newCommandSeparator();
                    break;
                case 2:
                    member.getOrderById();
                    newCommandSeparator();
                    break;
                case 3:
                    member.getCurrentMemberDetail();
                    newCommandSeparator();
                    break;
                case 0:
                    isContinue = false;
                    System.out.println("Stopping the program.");
                    break;
                default:
                    System.out.println("\nNo action correspond to this number! Please enter 0-5 only.\n");
            }
        }
    }

    // admin flow
    static public void adminFlow() throws IOException {

        // create a new admin instance
        Admin admin = new Admin();

        // logging in as admin
        boolean isLogin = false;
        while (!isLogin) {
            isLogin = admin.login();
        }
        // a boolean to check if the user want to continue running the program
        boolean isContinue = true;

        // a loop to let user perform different actions as an admin
        while (isContinue) {

            // showing different options for admin
            System.out.println("1 - viewProducts");
            System.out.println("2 - viewOrders");
            System.out.println("3 - viewMembers");
            System.out.println("4 - addNewProduct");
            System.out.println("5 - updateProductPrice");
            System.out.println("6 - orderByCustomerId");
            System.out.println("7 - changeOrderStatus");
            System.out.println("8 - getOrderDetailsToday");
            System.out.println("9 - getRevenueToday");
            System.out.println("0 - quit\n");

            // getting user option
            int option = InputValidator.getIntInput("Choose one of the actions above (0-9): ",
                    "Please only choose number 0-9");

            // perform different customer actions based on user's option
            switch (option) {
                case 1:
                    admin.viewProduct();
                    newCommandSeparator();
                    break;
                case 2:
                    admin.viewOrder();
                    newCommandSeparator();
                    break;
                case 3:
                    admin.viewMember();
                    newCommandSeparator();
                    break;
                case 4:
                    admin.addProduct();
                    newCommandSeparator();
                    break;
                case 5:
                    admin.updateProductPrice();
                    newCommandSeparator();
                    break;
                case 6:
                    admin.getOrderByCustomerId();
                    newCommandSeparator();
                    break;
                case 7:
                    admin.changeOrderStatus();
                    newCommandSeparator();
                    break;
                case 8:
                    admin.getOrdersDetailToday();
                    newCommandSeparator();
                    break;
                case 9:
                    admin.revenueToday();
                    newCommandSeparator();
                    break;
                case 0:
                    isContinue = false;
                    System.out.println("Stopping the program.");
                    break;
                default:
                    System.out.println("\nNo action correspond to this number! Please enter 0-7 only.\n");
            }
        }
    }

    // a method to display the welcome screen
    static public void welcomeScreen() {
        System.out.println("COSC2081 GROUP ASSIGNMENT ");
        System.out.println("STORE ORDER MANAGEMENT SYSTEM ");
        System.out.println("Instructor: Mr. Minh Vu");
        System.out.println("Group: Javalade");
        System.out.println("s3927502, Vu Tuan Linh");
        System.out.println("s3922086, Luong Thao My");
        System.out.println("s3927026, Lai Minh Tri");
        System.out.println("s3778972, Nguyen Hung Vinh");
        System.out.println("\n");
    }

    // a method to let user continue to their next action
    static public void newCommandSeparator() {

        // setting scanner for user input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Press Enter to perform your next action.");

        // user just need to hit enter to move on to the next action
        scanner.nextLine();

        // print multiple lines to separate different actions
        for (int i = 0; i < 50; i++) {
            System.out.println("\n");
        }
    }
}
