import java.util.*;
import java.io.*;

public class Order implements Serializable {
    private HashMap<Product, Integer> productDetails; // <productID, <quantity, price>>
    // or HashMap<Product, Integer> if product already contains info of price
    private String orderID;
    private String customerId;
    private String address;
    private double total;
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

//    public double calculatePriceSum(HashMap<Product, Integer> productDetails) {
//        // obtain price x quantity from productDetails
//        double total = 0;
//        for (var index : productDetails.entrySet()) {
//            Product product = index.getKey();
//            double price = product.getPrice();
//            int quantity = index.getValue();
//            total += price * quantity;
//        }
//        return total;
//    }


//    public void viewOrderDetails() {
//        System.out.println("Order:" +
//                " orderID=" + orderID +
//                ", address='" + address + '\'' +
//                ", phoneNumb=" + phoneNumb +
//                ", name='" + name + '\'');
//        for (var index : productDetails.entrySet()) {
//            Product product = index.getKey();
//            String productName = product.getTitle();
//            double price = product.getPrice();
//            int quantity = index.getValue();
//            System.out.println("Product= " + productName + " Price= " + price + " Quantity: " + quantity);
//        }
//    }

}
