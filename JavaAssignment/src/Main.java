import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String userID, userPassword;
        int choice, attemp = 0;
        while (true) {
            ConsoleTextDisplay.printMainMenu();
            choice = InputValidation.getInteger(1, 2);
            if (choice == 1) {
                while (true) {
                    {
                        ConsoleTextDisplay.printSystemMainTitle();
                        System.out.println("Dear User Please Login Your Account : ");
                        System.out.print("Username : ");
                        userID = input.nextLine();
                        System.out.print("Password : ");
                        userPassword = input.nextLine();
                        User user = Admin.login(userID, userPassword);
                        if (user instanceof Admin admin) {
                            attemp = 0;
                            System.out.println("Admin");
                        } else if (user instanceof SalesManager salesManager) {
                            attemp = 0;
                            System.out.println("Sales Manager");
                        } else if (user instanceof PurchaseManager purchaseManager) {
                            attemp = 0;
                            System.out.println("Purchase Manager");
                        } else {
                            ConsoleTextDisplay.printHeadingInConsole("Incorrect Username or Password", 80);
                            attemp++;
                        }
                        if (attemp > 3) {
                            ConsoleTextDisplay.lockingSystem(10);
                        }
                    }
                }
            } else {
                break;
            }
        }
    }
}

//    Admin admin = new Admin();
//        admin.registration("A001", "123", "Admin",
//                "Patrick", "014", "pat@gmail.com");
//
//                admin.registration("A002", "123", "PurchaseManager",
//                "Patrick", "014", "pat@gmail.com");
//
//                admin.registration("A003", "123", "SalesManager",
//                "Patrick", "014", "pat@gmail.com");