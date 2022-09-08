import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Member {
    private String name;
    private int phoneNumb;
    private String address;
    private String username;
    private String password;
    private String memberID;
    public Member(String memberID,String name, int phoneNumb, String address, String username,
                  String password) {
        this.memberID = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumb = phoneNumb;
        this.address = address;
        this.username = username;
        this.password = password;
    }

    public Member() {
    }

    public void signUp() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you want to create a member account?: Y/N");
        String answ = scanner.nextLine();
        if (Objects.equals(answ, "Y")) {
            System.out.println("Please enter your name: ");
            String name = scanner.nextLine();
            System.out.println("Your username: ");
            String userName = scanner.nextLine();
            System.out.println("Your phone numb: ");
            String phoneNumb = scanner.nextLine();
            System.out.println("Your home address: ");
            String address = scanner.nextLine();
            System.out.println("Please provide a password: ");
            String pass = scanner.nextLine();
        }

    }

    public boolean login() throws IOException {

        // set up scanner for user inputs
        Scanner scannerInput = new Scanner(System.in);

        System.out.println("\nLogging in as a member!");

        // getting username
        System.out.println("Your Username: ");
        String nameInput = scannerInput.nextLine();

        // getting user password
        System.out.println("Your password: ");
        String pwInput = scannerInput.nextLine();

        // setting up scanner for admin.txt file
        Scanner scannerMember = new Scanner(new File("./member.txt"));

        // loop through the member.txt file to find matching username and password
        while (scannerMember.hasNextLine()) {
            String currentAdmin = scannerMember.nextLine();

            // getting the name and password of the current checking admin
            String[] currentAdminAttrs = currentAdmin.split(",");
            String currentAdminName = currentAdminAttrs[0];
            String currentAdminPw = currentAdminAttrs[1];

            // if there was a match, finish executing the function and prompt user that they have successfully login
            if (nameInput.equals(currentAdminName) && pwInput.equals(currentAdminPw)) {
                System.out.println("\nSuccessfully login as a member.");
                scannerMember.close();
                return true;
            }
        }

        // cannot find any matching username and password, prompt user that they entered the wrong username and password
        System.out.println("Wrong username and/or password! Please try again!");
        scannerMember.close();
        return false;
    }
    public static Order createOrder(String customerId) throws IOException {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Please give us an address for delivery: ");
        String orderAddress = inputScanner.nextLine();

        HashMap<Product, Integer> orderProductDetails = new HashMap<Product, Integer>();
        boolean continueAddingProduct = true;

        while (continueAddingProduct) {
            Product product = null;

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
                        String currentProductId = currentProductAttrs[0];
                        double currentProductPrice = Double.parseDouble(currentProductAttrs[2]);
                        String currentProductCategory = currentProductAttrs[3];
                        product = new Product(currentProductId, currentProductName, currentProductPrice, currentProductCategory);
                        isGettingProduct = false;
                    }
                }

                if (isGettingProduct) {
                    System.out.println("Cannot find the product with matching name. Please try again.");
                }
            }
            System.out.println("Enter the amount that you want to buy: ");
            int num = inputScanner.nextInt();

            orderProductDetails.put(product, num);

            boolean validOption = false;

            System.out.println("Would you like to purchase more product (y/n): ");
            while (!validOption) {
                String isContinue = inputScanner.nextLine();

                if (isContinue.equals("")) {
                    continue;
                }
                else if (isContinue.equals("n")) {
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
        Order currentOrder = new Order(orderProductDetails, orderAddress, customerId);
        PrintWriter output = null;
        try {
            output = new PrintWriter(new FileWriter("order.txt", true));
            output.println(currentOrder.getOrderID()+ "," + customerId + "," + LocalDate.now() +","+orderAddress + "," + orderProductDetails+","+currentOrder.getTotal()+","+"delivered");
        }
        catch(IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        finally {
            if (output!=null) {
                output.close();
            }
        }
        return currentOrder;
    }


    public static void displayMemberDetail(String memberInfo) {
        String [] memberAttrs = memberInfo.split(",");

        String memberFullName = memberAttrs[0];
        String memberUsername = memberAttrs[1];
        String memberPhone = memberAttrs[3];
        String memberAddress = memberAttrs[4];
        String membership = memberAttrs[5];

        System.out.println("Full name: " + memberFullName);
        System.out.println("Username: " + memberUsername);
        System.out.println("Phone number: " + memberPhone);
        System.out.println("Address: " + memberAddress);
        System.out.println("Membership: " + membership);
    }

    public String displayMemberDetails() {
        return "Member{" +
                "name='" + name + '\'' +
                ", phoneNumb=" + phoneNumb +
                ", address='" + address + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getPhoneNumb() {
        return phoneNumb;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getMemberID() {
        return memberID;
    }
}