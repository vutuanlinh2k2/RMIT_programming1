public class Order {

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
        System.out.println("Order total: " + orderTotal + " mil VND");
        System.out.println("Order status: " + orderStatus + "\n");
    }

}
