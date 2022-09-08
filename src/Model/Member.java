package Model;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.UUID;

public class Member {

    private String customerID;
    private String name;
    private String phoneNumb;
    private String address;
    private String username;
    private String password;
    //default membership type is "NONE"
    private String type = "NONE";

    public Member() {};

    public Member(String name, String phoneNumb, String address, String username, String password) {
        this.customerID = UUID.randomUUID().toString();
        this.name = name;
        this.phoneNumb = phoneNumb;
        this.address = address;
        this.username = username;
        this.password = password;
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

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public void setPhoneNumb(String phoneNumb) {
        this.phoneNumb = phoneNumb;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}