//package Application;

import java.io.IOException;
import java.util.*;
//import Model.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String role;
        boolean roleSelected = false;
        while (!roleSelected) {
            System.out.println("Please enter your role (admin / member / customer): ");
            role = scanner.nextLine();

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
                default:
                    System.out.println("Please only choose between admin, member or customer!");
            }
        }
    }

    static public void adminFlow() throws IOException {

        Admin admin = new Admin();

        boolean isLogin = false;
        while (!isLogin) {
            isLogin = admin.login();
        }
        Scanner scanner = new Scanner(System.in);
        boolean isContinue = true;
        while (isContinue) {
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

            int option = InputValidator.getIntInput("Choose one of the actions above (0-9): ",
                    "Please only choose number 0-9");
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

    static public void memberFlow() throws IOException {


    }

    static public void customerFlow() throws IOException {
        Customer customer = new Customer();

        boolean isContinue = true;
        while (isContinue) {
            System.out.println("1 - register");
            System.out.println("2 - login");
            System.out.println("3 - see all product details");
            System.out.println("4 - search products by category");
            System.out.println("5 - sort product by price");
            System.out.println("0 - quit\n");

            int option = InputValidator.getIntInput("Choose one of the actions above (0-5): ",
                    "Please only choose number 0-5");
            switch (option) {
                case 1:
                    newCommandSeparator();
                    break;
                case 2:
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

    static public void welcomeScreen() {
        System.out.println("COSC2081 GROUP ASSIGNMENT ");
        System.out.println("STORE ORDER MANAGEMENT SYSTEM ");
        System.out.println("Instructor: Mr. Minh Vu");
        System.out.println("Group: Group Name");
        System.out.println("s3927502, Vu Tuan Linh");
        System.out.println("s3922086, Luong Thao My");
        System.out.println("s3927026, Lai Minh Tri");
        System.out.println("s3778972, Nguyen Hung Vinh");
        System.out.println("\n");
    }

    static public void newCommandSeparator() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press Enter to perform your next action.");
        scanner.nextLine();
        for (int i = 0; i < 50; i++) {
            System.out.println("\n");
        }
    }
}
