package com.company;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Member {
    private String name;
    private String phoneNumb;
    private String address;
    private String username;
    private String password;
    public Member(String name, String phoneNumb, String address, String username,
                  String password) {
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


    public String getName() {
        return name;
    }

    public String getPhoneNumb() {
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
}
