import java.util.Scanner;

public class InputValidation {
    static Scanner input = new Scanner(System.in);



    public static int getInteger(int lowerBound, int upperBound) {
        int myInt;
        while (true) {
            try {
                myInt = input.nextInt();
                if (myInt > upperBound || myInt < lowerBound)
                    throw new Exception();
            } catch (Exception e) {
                System.out.println("Invalid Input Please Try again");
                ConsoleTextDisplay.printMainMenu();
                input.nextLine(); // - Consume EOL Char
                continue;
            }
            return myInt;
        }
    }
}
