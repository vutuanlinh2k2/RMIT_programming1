//package Model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Customer {

    // 3 -a method to list all products and product detail
    public void viewProduct() throws IOException {

        System.out.println("Viewing all products.\n");

        // setup scanner for product.txt file
        Scanner scannerProduct = new Scanner(new File("./product.txt"));

        // using loop to display all information of each product
        while (scannerProduct.hasNextLine()) {
            String product = scannerProduct.nextLine();
            Product.displayProductDetail(product);
        }
    }

    // 4 - a method to search all products in a particular category
    public void searchProductByCategory() throws IOException {

        System.out.println("Searching products by category.\n");

        // setup scanner for product.txt file
        Scanner scannerProduct = new Scanner(new File("./product.txt"));

        // setup scanner for user input
        Scanner scannerInput = new Scanner(System.in);

        // getting category input
        System.out.println("Enter Category: ");
        String inputCategory = scannerInput.nextLine();

        // a variable to check if there were any products with this category
        boolean categoryFound = false;

        // going through the product.txt file
        while(scannerProduct.hasNextLine()) {

            // getting the category of each product
            String product = scannerProduct.nextLine();
            String[] productAttrs = product.split(",");
            String category = productAttrs[3];

            // if the category match with the input category, display info about it
            if (category.equals(inputCategory)) {
                Product.displayProductDetail(product);
                categoryFound = true;
            }
        }

        // if no product with this category was found, prompt user
        if (!categoryFound) {
            System.out.println("Cannot find any product with this category.");
        }
    }

    // 5 - a method to sort all products by product price
    public void sortProduct() throws IOException {
        System.out.println("Sorting the product by price");

        // set up scanner for product.txt file
        Scanner scannerProduct = new Scanner(new File("./product.txt"));

        // set up scanner for user input
        Scanner scannerInput = new Scanner(System.in);

        // a list to store all the products
        ArrayList<Product> productList = new ArrayList<>();

        // loop through each product and add them to the list
        while (scannerProduct.hasNextLine()) {
            String product = scannerProduct.nextLine();
            String[] productAttrs = product.split(",");
            String productId = productAttrs[0];
            String productName = productAttrs[1];
            double productPrice = Double.parseDouble(productAttrs[2]);
            String productCategory = productAttrs[3];

            Product currentProduct = new Product(productId, productName, productPrice, productCategory);
            productList.add(currentProduct);
        }

        // a loop to ask user if they want to sort products in ascending for descending order
        while (true) {
            System.out.println("Choosing sorting order (asc or des): ");
            String order = scannerInput.nextLine();
            // if the user input was not asc or des, ask them to try again
            if (!(order.equals("asc") || order.equals("des"))) {
                System.out.println("Please only enter asc or des!");
                continue;
            }
            // sorting the list based on customer option
            productList.sort((p1, p2) -> order.equals("asc")
                    ? (int) (p1.getPrice() - p2.getPrice())
                    : (int) (p2.getPrice() - p1.getPrice()));
            break;
        }

        // displaying all the products after being sorted
        for (Product product: productList) {
            System.out.println("Id: " + product.getId());
            System.out.println("Name: " + product.getTitle());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Category: " + product.getCategory() + "\n");
        }
    }
}
