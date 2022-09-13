import java.util.*;
import java.io.*;
import java.lang.*;
import java.time.LocalDate;

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

    // 8 - method to view all products
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

    // 8 - a method to view all orders
    public void viewOrder() throws IOException {

        System.out.println("Viewing all orders.\n");

        // setup scanner for order.txt file
        Scanner scannerOrder = new Scanner(new File("./order.txt"));

        // using loop to display all information of each order
        while(scannerOrder.hasNextLine()) {

            String order = scannerOrder.nextLine();
            Order.displayOrderDetail(order);
        }
    }

    // 8 - a method to view all member details
    public void viewMember() throws IOException {

        System.out.println("Viewing all members.\n");

        // setup scanner for member.txt file
        Scanner scannerMember = new Scanner(new File("./member.txt"));

        // using loop to display all information of each member
        while(scannerMember.hasNextLine()) {
            String member = scannerMember.nextLine();
            Member.displayMemberDetail(member);
        }
    }

    // 9 - a method to let admin add a new product
    public void addProduct() throws IOException {

        // set up scanner for user inputs
        Scanner scannerInput = new Scanner(System.in);

        System.out.println("Adding new product\n");

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
            if (!Product.checkProductExisted(productName)) {
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
        String productPrice = String.valueOf(InputValidator.getDoubleInput("Product's price: ",
                "Product price must be an integer or decimal number."));

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
            String newPrice = String.valueOf(InputValidator.getDoubleInput("Product's new price: ",
                    "Product price must be an integer or decimal number."));

            // updated the new price for the product
            String currentPrice = currentProduct.split(",")[2];
            String updatedProduct = currentProduct.replace(currentPrice, newPrice);

            // write the updated line to the temp file
            writer.write(updatedProduct + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
            writer.close();

            // rename the temp file to product.txt, replacing the old one
            tempFile.renameTo(new File("./product.txt"));
            System.out.println("The price of the product has been successfully updated.\n");
            return;
        }

        writer.close();

        // cannot find a product matching the name input, prompt the admin
        System.out.println("Cannot find any product matching this name.");
    }

    // 11 - a method to let admin get orders by customer id
    public void getOrderByCustomerId() throws IOException {

        System.out.println("Getting orders by customer Id\n");

        // setup scanner for order.txt file
        Scanner scannerOrder = new Scanner(new File("./order.txt"));

        // set scanner for user input
        Scanner scannerInput = new Scanner(System.in);

        // getting customer id input
        String inputCustomerId = scannerInput.nextLine();
        System.out.println("Enter Customer Id: ");

        // a variable to check if there were any orders with this customerId
        boolean customerExisted = false;

        // going through the order.txt file
        while(scannerOrder.hasNextLine()) {

            // getting the customerId of each line
            String order = scannerOrder.nextLine();
            String[] orderAttrs = order.split(",");
            String customerId = orderAttrs[1];

            // if the order match with the customerId input, display info about it
            if (customerId.equals(inputCustomerId)) {
                Order.displayOrderDetail(order);
                customerExisted = true;
            }
        }

        // if there were no orders with this customer id, prompt to admin
        if (!customerExisted) {
            System.out.println("Cannot find any order with this customer id!");
        }
    }

    // 12 - a method to update the status of an order
    public void changeOrderStatus() throws IOException {
        // set up scanner for user inputs
        Scanner scannerInput = new Scanner(System.in);

        System.out.println("Changing order status.\n");

        // getting orderId input
        System.out.println("Enter order id: ");
        String inputOrderId = scannerInput.nextLine();

        // a scanner for the file order.txt
        Scanner scannerOrder = new Scanner(new File("./order.txt"));

        // create a writer for a temporary file to store updated data
        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        // loop through each line of order.txt file
        while (scannerOrder.hasNextLine()) {

            // get the id of each product
            String order = scannerOrder.nextLine();
            String[] orderAttrs = order.split(",");
            String orderId = orderAttrs[0];

            // if the id is not matched with user input, move on the next line
            if (!inputOrderId.equals(orderId)) {
                // write the current line to the temp file
                writer.write(order + (scannerOrder.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }

            // getting the status of the current order
            String currentStatus = order.split(",")[6];

            // if the order status has already been updated, prompt the admin
            if (currentStatus.equals("paid")) {
                writer.write(order + (scannerOrder.hasNextLine() ? System.lineSeparator() : ""));
                System.out.println("This order already has paid status.");
                writer.close();
                return;
            }

            // changing order staus to paid
            String updatedOrder = order.replace(currentStatus, "paid");

            // write the updated line to the temp file
            writer.write(updatedOrder + (scannerOrder.hasNextLine() ? System.lineSeparator() : ""));
            writer.close();

            // rename the temp file to order.txt, replacing the old one
            tempFile.renameTo(new File("./order.txt"));
            System.out.println("This order has been updated to paid status.\n");
            return;
        }

        writer.close();

        // cannot find any order that match the name input, prompt the admin
        System.out.println("Cannot find any order matching this id.");
    }

    // a method to display all the order for today
    public void getOrdersDetailToday() throws IOException {
        System.out.println("Getting order details for today.\n");

        // set up a scanner for the file order.txt
        Scanner scannerOrder = new Scanner(new File("./order.txt"));

        // getting the current date
        String today = LocalDate.now().toString();

        // a variable to check if there were any orders for today
        boolean ordersExisted = false;

        // loop through each order in the file order.txt
        while(scannerOrder.hasNextLine()) {

            // getting the date of the current order
            String order = scannerOrder.nextLine();
            String[] orderAttrs = order.split(",");
            String orderDate = orderAttrs[2];

            // if the order is in today, display info about it
            if (orderDate.equals(today)) {
                Order.displayOrderDetail(order);
                ordersExisted = true;
            }
        }

        // if there were no orders in today, prompt the admin
        if (!ordersExisted) {
            System.out.println("There were no orders today!");
        }
    }

    // a method to get the total revenue today
    public void revenueToday() throws IOException {
        System.out.println("Getting total revenue for today.\n");

        // set up a scanner for the file order.txt
        Scanner scannerOrder = new Scanner(new File("./order.txt"));

        // getting the current date
        String today = LocalDate.now().toString();

        // a variavle to track the total revenue today
        double revenue = 0;

        // loop through each order in the order.txt file
        while(scannerOrder.hasNextLine()) {

            // getting the date of the current order
            String order = scannerOrder.nextLine();
            String[] orderAttrs = order.split(",");
            String orderDate = orderAttrs[2];

            // if the order is in today, add it's revenue to the total one
            if (orderDate.equals(today)) {
                double orderTotal = Double.parseDouble(orderAttrs[6]);
                revenue += orderTotal;
            }
        }

        System.out.println("Total revenue today is: " + revenue);
    }
    
    public void membershipValuate(String customerID) {
        OrderDAO orderDAO = new OrderDAO();
        List<Order> listAllorders = orderDAO.findAll();
        List<Order> newList = new ArrayList<>();

        for (Order o : listAllorders) {
            if (o.getCustomerId().equals(customerID) && o.getStatus().equals("paid")) {
                newList.add(o);
            }
        }
        double total = 0;
        for (Order o : newList) {
            total += o.getTotal();
        }

        MemberDAO memberDAO = new MemberDAO();
        Member member = memberDAO.findOne(customerID);
        member.modifyMembership(total);
    }
}
