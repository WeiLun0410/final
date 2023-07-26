public class ConsoleTextDisplay {

    public static void printHeadingInConsole(String str, int length) {
        System.out.println(equalSignDivider(length));
        System.out.println(alignMiddle(str, length));
        System.out.println(equalSignDivider(length));
    }


    public static String alignMiddle(String str, int length) {
        return String.format("%-" + length + "s", String.format("%" + (str.length() + length) / 2 + "s", str));
    }

    public static String equalSignDivider(int length) {
        return "=".repeat(length);
    }

    public static void printMainMenu() {
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        String mainMenu = """
                1. Login To The System
                2. Quit
                Please enter your choice :\s""";
        System.out.print(mainMenu);
    }

    public static void printSystemMainTitle() {
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
    }

    public static void lockingSystem(int sec) {
        System.out.println("System will be lock for " + sec + " seconds");
        for (int i = 1; i < sec + 1; i++) {
            try {
                Thread.sleep(1000);
                System.out.println(i + " Sec");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}