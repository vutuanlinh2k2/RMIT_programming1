import java.io.IOException;
import java.util.*;

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
            System.out.println("\nChoose one of the following actions (0-7): ");
            System.out.println("1 - viewProducts");
            System.out.println("2 - viewOrders");
            System.out.println("3 - viewMembers");
            System.out.println("4 - addNewProduct");
            System.out.println("5 - updateProductPrice");
            System.out.println("6 - orderByCustomerId");
            System.out.println("7 - changeOrderStatus");
            System.out.println("0 - quit\n");

            int option = scanner.nextInt();
            switch (option) {
                case 1:
                case 2:
                case 3:
                case 4:
                    admin.addProduct();
                    newCommandSeparator();
                    break;
                case 5:
                    admin.updateProductPrice();
                    newCommandSeparator();
                    break;
                case 6:
                case 7:
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
