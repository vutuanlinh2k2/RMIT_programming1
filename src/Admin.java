import java.util.*;
import java.io.*;
import java.lang.*;

public class Admin {

    // 8 - a method to let user login as an admin
    public boolean login() throws IOException {

        // set up scanner for user inputs
        Scanner scannerInput = new Scanner(System.in);

        System.out.println("\nLogging in as admin!");

        // getting username
        System.out.println("Your Username: ");
        String nameInput = scannerInput.nextLine();

        // getting user password
        System.out.println("Your password: ");
        String pwInput = scannerInput.nextLine();

        // setting up scanner for admin.txt file
        Scanner scannerAdmin = new Scanner(new File("./admin.txt"));

        // loop through the admin.txt file to find matching username and password
        while (scannerAdmin.hasNextLine()) {
            String currentAdmin = scannerAdmin.nextLine();

            // getting the name and password of the current checking admin
            String[] currentAdminAttrs = currentAdmin.split(",");
            String currentAdminName = currentAdminAttrs[0];
            String currentAdminPw = currentAdminAttrs[1];

            // if there was a match, finish executing the function and prompt user that they have successfully login
            if (nameInput.equals(currentAdminName) && pwInput.equals(currentAdminPw)) {
                System.out.println("\nSuccessfully login as an admin.");
                scannerAdmin.close();
                return true;
            }
        }

        // cannot find any matching username and password, prompt user that they entered the wrong username and password
        System.out.println("Wrong username and/or password! Please try again!");
        scannerAdmin.close();
        return false;
    }

    // 8 - a method to view all products
    public void viewProduct() throws IOException {

        System.out.println("Viewing all products\n");
        Scanner scannerProduct = new Scanner(new File("./product.txt"));

        while (scannerProduct.hasNextLine()) {
            String currentProduct = scannerProduct.nextLine();
            String[] currentProductAttrs = currentProduct.split(",");
            String currentProductId = currentProductAttrs[0];
            String currentProductName = currentProductAttrs[1];
            String currentProductPrice = currentProductAttrs[2];
            String currentProductCategory = currentProductAttrs[3];

            System.out.println("Id: " + currentProductId);
            System.out.println("Name: " + currentProductName);
            System.out.println("Price: " + currentProductPrice);
            System.out.println("Category: " + currentProductCategory + "\n");
        }
    }

    // 9 - a method to let admin add a new product
    public void addProduct() throws IOException {

        // set up scanner for user inputs
        Scanner scannerInput = new Scanner(System.in);

        System.out.println("Adding new product");

        // generate new product id
        String productId = UUID.randomUUID().toString();

        // getting new product name
        String productName;

        // a loop to get a valid name for the new product
        while (true) {

            // getting the input name
            System.out.println("Product's name: ");
            productName = scannerInput.nextLine();

            // if there is no product with this name yet, the input name is valid
            if (!Utility.checkProductExisted(productName)) {
                break;
            }

            // if there is a product with this name, let user know and ask them to enter another name
            else {
                System.out.println("There is already a product with this name, please enter another name! \n");
            }
        }

        // getting new product category
        System.out.println("Product's category: ");
        String productCategory = scannerInput.nextLine();

        // getting new product price
        System.out.println("Product's price: ");
        String productPrice = String.valueOf(scannerInput.nextDouble());

        // concat new info into a line to add the product.txt file
        String newProduct = String.join(",", productId, productName, productPrice, productCategory);

        // append the line for the new product to the end of the product.txt file
        Writer output = new BufferedWriter(new FileWriter("./product.txt", true));
        output.append(System.lineSeparator() + newProduct);

        // prompt the admin that the product has been added successfully
        System.out.println("Successfully added new product.");
        output.close();
    }

    // 10 - a method to let the admin change the price of a product
    public void updateProductPrice() throws IOException {

        // set up scanner for user inputs
        Scanner scannerInput = new Scanner(System.in);

        System.out.println("You are changing the price of a product.");

        // getting the input of product name
        System.out.println("Enter product's name: ");
        String inputName = scannerInput.nextLine();

        // a scanner for the file product.txt
        Scanner scannerProduct = new Scanner(new File("./product.txt"));

        // create a writer for a temporary file to store updated data
        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // loop through each line of product.txt file
        while (scannerProduct.hasNextLine()) {

            // get the name of each product
            String currentProduct = scannerProduct.nextLine();
            String[] currentProductAttrs = currentProduct.split(",");
            String currentProductName = currentProductAttrs[1];

            // if the name is not matched with user input
            if (!inputName.equals(currentProductName)) {
                // write the current line to the temp file
                writer.write(currentProduct + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));

                continue;
            }

            // ask the admin for the updated price
            System.out.println("Changing the price to: ");
            String newPrice = String.valueOf(scannerInput.nextDouble());

            // updated the new price for the product
            String currentPrice = currentProduct.split(",")[2];
            String updatedProduct = currentProduct.replace(currentPrice, newPrice);

            // write the updated line to the temp file
            writer.write(updatedProduct + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
            writer.close();

            // rename the temp file to product.txt, replacing the old one
            tempFile.renameTo(new File("./product.txt"));
            return;
        }

        writer.close();

        // cannot find a product matching the name input, prompt the admin
        System.out.println("Cannot find any product matching this name.");
    }
}
