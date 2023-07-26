import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Admin extends User implements Serializable {

    public Admin(String userID, String userPassword, String role, String userName, String contactNumber, String email) {
        super(userID, userPassword, role, userName, contactNumber, email);
    }

    public Admin() {

    }

    public void registration(String userID, String userPassword, String role, String userName, String contactNumber, String email) {
        User newUser;
        if (role.equals("Admin")) {
            newUser = new Admin(userID, userPassword, role, userName, contactNumber, email);
        } else if (role.equals("SalesManager")) {
            newUser = new SalesManager(userID, userPassword, role, userName, contactNumber, email);
        } else if (role.equals("PurchaseManager")) {
            newUser = new PurchaseManager(userID, userPassword, role, userName, contactNumber, email);
        } else {
            System.out.println("Invalid role. Registration failed.");
            return;
        }
        List<User> users = new ArrayList<>();
        boolean exist = false;
        try {
            FileInputStream fis = new FileInputStream("Users.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            while (true) {
                User temp;
                Object obj = ois.readObject();
                if (obj != null) {
                    temp = (User) obj;
                    users.add(temp);
                    if (temp.equals(newUser)) {
                        exist = true;
                        break;
                    }
                }
            }
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException ignored) {

        }
        if (!exist) {
            try {
                users.add(newUser);
                FileOutputStream fos = new FileOutputStream("Users.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                for (User tempUser : users) {
                    oos.writeObject(tempUser);
                }
                oos.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("Error occurred while registering account");
            }
        } else {
            System.out.println("Account Already Exist, Try to choose another User ID");
        }
    }

//    public static User printData() {
//        try {
//            FileInputStream fis = new FileInputStream("Users.ser");
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            while (true) {
//                User temp;
//                Object obj = ois.readObject();
//                if (obj != null) {
//                    temp = (User) obj;
//                    System.out.println(temp);
//                } else {
//                    return null;
//                }
//            }
//        } catch (IOException | ClassNotFoundException e) {
//            return null;
//        }
//    }
}
