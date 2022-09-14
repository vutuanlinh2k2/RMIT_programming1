import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class Customer {

    // 1 - customer can register to become a member
    public void registerMember() throws IOException {

        // set up scanner for user inputs
        Scanner scannerInput = new Scanner(System.in);

        // get user inputs
        System.out.println("Please enter your full name: ");
        String fullName = scannerInput.nextLine();

        String username;

        // a loop to get a valid username
        while (true) {

            // getting the input name
            System.out.println("Enter your username: ");
            username = scannerInput.nextLine();

            // if the username is not taken yet, break the loop
            if (!Member.checkMemberExisted(username)) {
                break;
            }
            // if the username is taken, prompt the user to try again
            else {
                System.out.println("This username has already been taken! Please choose to different one.\n");
            }
        }

        String phoneNumber;

        // a loop to get a valid phone number
        while (true) {

            // getting the input phone number
            System.out.println("Enter your phone number: ");
            phoneNumber = scannerInput.nextLine();

            // if the input is valid, break the loop
            if (phoneNumber.matches("^\\d{10}$")) {
                break;
            }
            // if the input is invalid, prompt the user to try again
            else {
                System.out.println("Wrong phone number format. Please try again. \n");
            }
        }

        // getting customer address
        System.out.println("Your home address: ");
        String address = scannerInput.nextLine();

        // ask them to provide a password for their account
        System.out.println("Please provide a password: ");
        String pass = scannerInput.nextLine();

        // generate customer id
        String customerId = UUID.randomUUID().toString();

        // add a new line of member to the member.txt file
        String newMember = String.join(",",customerId, fullName, username, pass, phoneNumber,
                address, "Normal", "0.0");
        Writer output = new BufferedWriter(new FileWriter("./member.txt", true));
        output.append(System.lineSeparator() + newMember);

        System.out.println("Successfully registered.");
        output.close();
    }

    // 2 - customer can log in with their registered usernames and passwords and view their information
    public void login() throws IOException {
        Member.login();
    }

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
