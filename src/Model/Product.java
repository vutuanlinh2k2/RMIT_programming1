package Model;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

public class Product {
    private String id;
    private String title;
    private double price;
    private String category;

    public Product(String id, String title, double price, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    public static boolean checkProductExisted(String name) throws IOException {

        // a scanner for the product.txt file
        Scanner scannerProduct = new Scanner(new File("./product.txt"));

        // loop through each line of the product.txt file
        while (scannerProduct.hasNextLine()) {

            // getting the name of the current product
            String currentProduct = scannerProduct.nextLine();
            String[] currentProductAttrs = currentProduct.split(",");
            String currentProductName = currentProductAttrs[1];

            // if the current product's name match with the input, return true
            if (name.equals(currentProductName)) {
                return true;
            }
        }

        // no product's name match with the input, return false
        return false;
    }

    public static void displayProductDetail(String productInfo) {

        String[] productAttrs = productInfo.split(",");

        String productId = productAttrs[0];
        String productName = productAttrs[1];
        String productPrice = productAttrs[2];
        String productCategory = productAttrs[3];

        System.out.println("Id: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Price: " + productPrice);
        System.out.println("Category: " + productCategory + "\n");
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }
}

