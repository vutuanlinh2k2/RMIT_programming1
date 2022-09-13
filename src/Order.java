import java.util.*;
import java.io.*;
import java.time.LocalDate;

public class Order implements Serializable {

    private HashMap<Product, Integer> productDetails; // <productID, <quantity, price>>

    // or HashMap<Product, Integer> if product already contains info of price
    private String orderID;
    private String customerId;
    private String address;
    private double total;
    private LocalDate date;

    private String status = "unpaid";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Order() {

    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

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

        System.out.println("Order Id: " + orderId);
        System.out.println("Customer Id: "  + customerId);
        System.out.println("Order date: " + orderDate);
        System.out.println("Address: " + orderAddress);
        System.out.println("Order products: ");
        for (int i = 0; i < orderProductList.length; i++) {
            System.out.println("\t" + orderProductList[i] + " - " + orderProductNumList[i]);
        }
        System.out.println("Order total: " + orderTotal);
        System.out.println("Order status: " + orderStatus + "\n");
    }

    public HashMap<Product, Integer> getProductDetails() {
        return productDetails;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getAddress() {
        return address;
    }

    public double getTotal() {
        return total;
    }

    public LocalDate getDate() {
        return date;
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
