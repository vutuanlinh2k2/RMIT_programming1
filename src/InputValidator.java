import java.util.Scanner;

public class InputValidator {

    // a method to ask user to enter a valid integer input
    public static int getIntInput(String askMessage ,String errMessage) {
        // set up scanner for user input
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // try to convert user input into integer
            try {
                System.out.println(askMessage);
                return Integer.parseInt(scanner.nextLine());
            }
            // if there were any errors, prompt the user and let them try again
            catch (Exception e) {
                System.out.println(errMessage + " Please try again.");
            }
        }
    }

    // a method to ask user to enter a valid decimal number input
    public static double getDoubleInput(String askMessage ,String errMessage) {
        // set up scanner for user input
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // try to convert user input into a double
            try {
                System.out.println(askMessage);
                return Double.parseDouble(scanner.nextLine());
            }
            // if there were any errors, prompt the user and let them try again
            catch (Exception e) {
                System.out.println(errMessage + " Please try again.\n");
            }
        }
    }
}
