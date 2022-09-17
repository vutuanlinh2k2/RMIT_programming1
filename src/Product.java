import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Product {
    final private String id;
    final private String title;
    final private double price;
    final private String category;

    public Product(String id, String title, double price, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }

    // a method to check if there is a product with the matching name
    public static boolean checkProductExisted(String name) throws IOException {

        // a scanner for the product.txt file
        Scanner scannerProduct = new Scanner(new File("./src/product.txt"));

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

    // a static method to display info of a product
    public static void displayProductDetail(String productInfo) {

        // getting the info of product from the line
        String[] productAttrs = productInfo.split(",");
        String productId = productAttrs[0];
        String productName = productAttrs[1];
        String productPrice = productAttrs[2];
        String productCategory = productAttrs[3];

        // display the info of the product to the user
        System.out.println("Id: " + productId);
        System.out.println("Name: " + productName);
        System.out.println("Price: " + productPrice + " mil VND");
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

