package com.company;

public class Member {
    private String name;
    private int phoneNumb;
    private String address;
    private String username;
    private String password;
    public Member(String name, int phoneNumb, String address, String username,
                  String password) {
        this.name = name;
        this.phoneNumb = phoneNumb;
        this.address = address;
        this.username = username;
        this.password = password;
    }
//    public boolean signUp() {
//    }
//    public boolean login() {
//    }

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
}
