import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Utility {
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
}
