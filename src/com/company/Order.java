package com.company;


import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    HashMap<Product, Integer> productDetails; // <productID, <quantity, price>>
    // or HashMap<Product, Integer> if product already contains info of price
    String orderID;
    String address;
    int phoneNumb;
    String name;

    // an order would contain info of the product ordered,
    // the quantity
    public Order(HashMap<Product, Integer> productDetails, String orderID, String address, int phoneNumb, String memberName) {
        this.productDetails = productDetails;
        this.orderID = orderID;
        this.address = address;
        this.phoneNumb = phoneNumb;
        this.name = memberName;
    }

    public double calculatePriceSum(HashMap<Product, Integer> productDetails) {
        // obtain price x quantity from productDetails
        double total = 0;
        for (var index : productDetails.entrySet()) {
            Product product = index.getKey();
            double price = product.getPrice();
            int quantity = index.getValue();
            total += price * quantity;
        }
        return total;
    }


    public void viewOrderDetails() {
        System.out.println("Order:" +
                " orderID=" + orderID +
                ", address='" + address + '\'' +
                ", phoneNumb=" + phoneNumb +
                ", name='" + name + '\'');
        for (var index : productDetails.entrySet()) {
            Product product = index.getKey();
            String productName = product.getTitle();
            double price = product.getPrice();
            int quantity = index.getValue();
            System.out.println("Product= " + productName + " Price= " + price + " Quantity: " + quantity);
        }
    }

}
