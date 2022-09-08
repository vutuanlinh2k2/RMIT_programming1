package Model;

import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Order implements Serializable {
    private HashMap<Product, Integer> productDetails; // <productID, <quantity, price>>
    // or HashMap<Model.Product, Integer> if product already contains info of price
    private String orderID;
    private String customerId;
    private String address;
    private double total;
    private LocalDate date;
    // an order would contain info of the product ordered,
    // the quantity
    public Order(HashMap<Product, Integer> productDetails, String address, String customerId) {
        this.productDetails = productDetails;
        this.orderID = UUID.randomUUID().toString();
        this.address = address;
        this.customerId = customerId;
        double total = 0;
        for (Map.Entry<Product, Integer> set: productDetails.entrySet()) {
            total += set.getValue() * set.getKey().getPrice();
        }
        this.total = total;
        this.date = LocalDate.now();
    }

    public static Order createOrder(String customerId) throws IOException {
        Scanner inputScanner = new Scanner(System.in);

        System.out.println("Please give us an address for delivery: ");
        String orderAddress = inputScanner.nextLine();

        HashMap<Product, Integer> orderProductDetails = new HashMap<Product, Integer>();
        boolean continueAddingProduct = true;

        while (continueAddingProduct) {
            Product product = null;

            boolean isGettingProduct = true;
            while (isGettingProduct) {

                Scanner scannerProduct = new Scanner(new File("./product.txt"));

                System.out.println("\nEnter the name of the product you want to purchase: ");
                String nameInput = inputScanner.nextLine();

                while (scannerProduct.hasNextLine()) {

                    String currentProduct = scannerProduct.nextLine();
                    String[] currentProductAttrs = currentProduct.split(",");
                    String currentProductName = currentProductAttrs[1];

                    if (currentProductName.equals(nameInput)) {
                        String currentProductId = currentProductAttrs[0];
                       double currentProductPrice = Double.parseDouble(currentProductAttrs[2]);
                       String currentProductCategory = currentProductAttrs[3];
                        PrintWriter output = null;
                        try {
                            output = new PrintWriter(new FileWriter("order.txt", true));
                            output.println(currentProductId + "," + customerId + "," +LocalDate.now() +","+orderAddress + "," + currentProductName+","+currentProductPrice
                            +",");

                        }
                        catch(IOException ioe) {
                            System.err.println(ioe.getMessage());
                        }
                        finally {
                            if (output!=null) {
                                output.close();
                            }
                        }
                       product = new Product(currentProductId, currentProductName, currentProductPrice, currentProductCategory);
                       isGettingProduct = false;
                    }
                }

                if (isGettingProduct) {
                    System.out.println("Cannot find the product with matching name. Please try again.");
                }
            }

            System.out.println("Enter the amount that you want to buy: ");
            int num = inputScanner.nextInt();

            orderProductDetails.put(product, num);

            boolean validOption = false;

            System.out.println("Would you like to purchase more product (y/n): ");
            while (!validOption) {
                String isContinue = inputScanner.nextLine();

                if (isContinue.equals("")) {
                    continue;
                }
                else if (isContinue.equals("n")) {
                    continueAddingProduct = false;
                    validOption = true;
                } else if (isContinue.equals("y")) {
                    validOption = true;
                } else {
                    System.out.println("Please enter only y (for yes) or n (for no).");
                    System.out.println("Would you like to purchase more product (y/n): ");
                }
            }
        }

        return new Order(orderProductDetails, orderAddress, customerId);
    }

    public static void displayOrderDetail(String productInfo) {

        String[] orderAttrs = productInfo.split(",");

        String orderId = orderAttrs[0];
        String customerId = orderAttrs[1];
        String orderDate = orderAttrs[2];
        String orderAddress = orderAttrs[3];
        String orderProducts = orderAttrs[4];
        String orderProductsNum = orderAttrs[5];
        String orderTotal = orderAttrs[6];
        String orderStatus = orderAttrs[7];

        String[] orderProductList = orderProducts.split(":");
        String [] orderProductNumList = orderProductsNum.split(":");

        System.out.println("Model.Order Id: " + orderId);
        System.out.println("Model.Customer Id: "  + customerId);
        System.out.println("Model.Order date: " + orderDate);
        System.out.println("Address: " + orderAddress);
        System.out.println("Model.Order products: ");
        for (int i = 0; i < orderProductList.length; i++) {
            System.out.println("\t" + orderProductList[i] + " - " + orderProductNumList[i]);
        }
        System.out.println("Model.Order total: " + orderTotal);
        System.out.println("Model.Order status: " + orderStatus + "\n");
    }

//    public double calculatePriceSum(HashMap<Model.Product, Integer> productDetails) {
//        // obtain price x quantity from productDetails
//        double total = 0;
//        for (var index : productDetails.entrySet()) {
//            Model.Product product = index.getKey();
//            double price = product.getPrice();
//            int quantity = index.getValue();
//            total += price * quantity;
//        }
//        return total;
//    }


//    public void viewOrderDetails() {
//        System.out.println("Model.Order:" +
//                " orderID=" + orderID +
//                ", address='" + address + '\'' +
//                ", phoneNumb=" + phoneNumb +
//                ", name='" + name + '\'');
//        for (var index : productDetails.entrySet()) {
//            Model.Product product = index.getKey();
//            String productName = product.getTitle();
//            double price = product.getPrice();
//            int quantity = index.getValue();
//            System.out.println("Model.Product= " + productName + " Price= " + price + " Quantity: " + quantity);
//        }
//    }

}
