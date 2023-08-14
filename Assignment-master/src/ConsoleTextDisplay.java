import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public static String starSignDivider(int length) {
        return "*".repeat(length);
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
            } catch (InterruptedException ignored) {
            }
        }
    }

    public static int salesManagerMenu() {
        Scanner scanner = new Scanner(System.in);
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        String salesManagerMenu = """
                1. Add Item
                2. Delete Item
                3. Edit Item
                4. Add Supplier
                5. Delete Supplier
                6. Edit Supplier
                7. Add Daily Item Wise Sales
                8. Delete Daily Item Wise Sales
                9. Edit Daily Item Wise Sales
                10. Add Purchase Requisition
                11. Delete Purchase Requisition
                12. Edit Purchase Requisition
                13. Display Purchase Requisition
                14. Display Purchase Order
                15. Quit
                Please enter your choice :\s""";

        System.out.print(salesManagerMenu);
        return InputValidation.getInteger(1, 15);
    }

    public static Item AddItemMenu() {
        Scanner scanner = new Scanner(System.in);
        FileHandling f = new FileHandling("Item.txt");
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        String itemID = """
                                Enter item detail below :
                                1. ItemID :\s""" + f.generateID();
        System.out.println(itemID);
        System.out.print("2. ItemName : ");
        String itemName = scanner.nextLine();
        System.out.print("3. Sell Price : ");
        double sellPrice = InputValidation.getPrice();
        System.out.print("4. Quantity : ");
        int quantity = InputValidation.getInteger(1, 999999);
        System.out.print("5. ExpireDate : ");
        String expiredDate = InputValidation.getValidDate();
        System.out.print("6. Supply Price : RM ");
        double supplyPrice = InputValidation.getPrice();
        System.out.println();
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        System.out.println("Please do confirm item detail is correct");
        System.out.println("ItemID : " + f.generateID());
        System.out.println("ItemName : " + itemName);
        System.out.println("Sell Price : " + sellPrice);
        System.out.println("Quantity : " + quantity);
        System.out.println("Expire Date : " + expiredDate);
        System.out.println("Supply Price : RM " + supplyPrice);
        System.out.println(equalSignDivider(80));
        System.out.println("1. Confirm");
        System.out.println("2. Discard change");
        System.out.print("Please enter your choice : ");
        String choice = scanner.nextLine();
        return choice.equals("1") ? new Item(f.generateID(), itemName, quantity, expiredDate, sellPrice, supplyPrice) : null;
    }

    public static Item deleteItemMenu() {
        FileHandling fh = new FileHandling("Item.txt");
        fh.printData();
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the itemID which you want to remove :");
        System.out.print("ItemID : ");
        String itemID = scanner.next();
        return Item.createItem(itemID);
    }


    public static String EditItemMainMenu() {
        printSystemMainTitle();
        FileHandling fh = new FileHandling("Item.txt");
        fh.printData();
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Item ID : ");
            String itemID = scanner.nextLine();
            if (fh.searchRow("itemID", itemID) != null) {
                return itemID;
            } else {
                System.out.println("Item with this itemID is not found !");
            }
        }
    }

    public static String[] EditItemSubMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("What kind of information you want to modify ?");
        System.out.print(""" 
                1. Item Name
                2. Quantity
                3. Expire Date
                4. Sell Price
                5. Supply Price
                Please enter your choice :\s""");
        int choice = InputValidation.getInteger(1, 5);
        String editCol = "", overrideValue;
        if (choice == 1) {
            editCol = "itemName";
            System.out.print("Please enter the new item name : ");
            overrideValue = scanner.nextLine();
        } else if (choice == 2) {
            editCol = "quantity";
            System.out.print("Please enter the new quantity : ");
            overrideValue = Integer.toString(InputValidation.getInteger(1, 999999));
        } else if (choice == 3) {
            editCol = "expireDate";
            System.out.print("Please enter the new expire date : ");
            overrideValue = InputValidation.getValidDate();
        } else if (choice == 4) {
            editCol = "sellPrice";
            System.out.print("Please enter the new sell price : ");
            overrideValue = Double.toString(InputValidation.getPrice());
        } else {
            editCol = "supplyPrice";
            System.out.print("Please enter the new supply price : ");
            overrideValue = scanner.nextLine();
        }
        return new String[]{editCol, overrideValue};
    }


    public static Supplier AddSupplierMenu() {
        Scanner scanner = new Scanner(System.in);
        FileHandling supplierFile = new FileHandling("Supplier.txt");
        ArrayList<Item> itemArrayList = new ArrayList<>();
        ArrayList<Double> supplyPriceArrayList = new ArrayList<>();
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        String supplierID = """
                                    Enter item detail below:
                                    1. SupplierID :\s""" + supplierFile.generateID();
        System.out.println(supplierID);
        System.out.print("2. SupplierName : ");
        String supplierName = scanner.nextLine();
        System.out.print("3. Location : ");
        String location = InputValidation.getValidAddress();
        int count = 0;
        String itemID;
        double itemSupplyPrice;
        FileHandling fh = new FileHandling("Item.txt");
        fh.printData();
        while (true) {
//            - First round Cannot done
            if (count == 0) {
                System.out.print("Please Enter the itemID: ");
                itemID = scanner.next();
                Item item = Item.createItem(itemID);
                if (item == null) {
                    System.out.println("Item ID Not Found");
                    continue;
                }
                System.out.print("Enter item's supply price: ");
                itemArrayList.add(item);
                itemSupplyPrice = InputValidation.getPrice();
                supplyPriceArrayList.add(itemSupplyPrice);
                count++;
            } else {
                System.out.print("Please Enter the itemID (type 'done' to finish adding items) : ");
                itemID = scanner.next();
                boolean added = false;
                for (Item item : itemArrayList) {
                    if (item.getItemID().equals(itemID)) {
                        System.out.println("This item has been added");
                        added = true;
                    }
                }
                if (added) continue;
                if (itemID.equalsIgnoreCase("done")) {
                    break;
                } else {
                    Item item = Item.createItem(itemID);
                    if (item == null) {
                        System.out.println("Item ID Not Found");
                        continue;
                    }
                    System.out.println("Enter item's supply price: ");
                    itemSupplyPrice = InputValidation.getPrice();
                    itemArrayList.add(item);
                    supplyPriceArrayList.add(itemSupplyPrice);
                }
            }
        }
        return new Supplier(supplierFile.generateID(), supplierName, location, itemArrayList, supplyPriceArrayList);

    }

    public static String deleteSupplierMenu() {
        Scanner scanner = new Scanner(System.in);
        FileHandling fh = new FileHandling("Supplier.txt");
        fh.printData();
        System.out.println();
        while (true) {
            System.out.print("Enter supplierID : ");
            String supplierID = scanner.next();
            if (fh.searchRow("SupplierID", supplierID) != null) {
                return supplierID;
            } else {
                System.out.println("Supplier ID not found");
            }
        }
    }

    public static ArrayList<String> editSupplierMenu() {
        FileHandling fh = new FileHandling("Supplier.txt");
        FileHandling fh1 = new FileHandling("Item.txt");
        fh.printData();
        System.out.println();
        Scanner input = new Scanner(System.in);
        String supplierID, itemID;
        while (true) {
            System.out.print("Enter SupplierID : ");
            supplierID = input.next();
            if (fh.searchRow("SupplierID", supplierID) != null) {
                break;
            } else {
                System.out.println("Supplier Not Found");
            }
        }
        input.nextLine();
        System.out.println("What kind of information you want to edit?");
        System.out.print(""" 
                1. Supplier Name
                2. Location
                3. ItemID
                4. Supply Price
                Please enter your choice :\s""");
        int choice = InputValidation.getInteger(1, 4);
        String editCol, overrideContent;
        ArrayList<String> editDetail = new ArrayList<>();
        if (choice == 1) {
            editCol = "SupplierName";
            System.out.println("Please enter the new supplier name.");
            System.out.print("Supplier Name : ");
            overrideContent = input.nextLine();
        } else if (choice == 2) {
            editCol = "location";
            System.out.println("Please enter the new location");
            System.out.print("Location : ");
            overrideContent = input.nextLine();
        } else if (choice == 3) {
            editCol = "itemID";
            while (true) {
                System.out.println("Please enter the new item ID : ");
                System.out.print("Item ID : ");
                overrideContent = input.next();
                if (fh1.searchRow("itemID", overrideContent) != null) {
                    break;
                } else {
                    System.out.println("Item Not Found");
                }
            }
        } else {
            editCol = "supplyPrice";
            System.out.println("Please enter the new Supply Price");
            System.out.print("Supply Price : ");
            overrideContent = Double.toString(InputValidation.getPrice());
        }
        if (choice == 1 || choice == 2) {
            editDetail.add(supplierID);
            editDetail.add(editCol);
            editDetail.add(overrideContent);
        } else {
            while (true) {
                System.out.println("Enter the replaced ItemID : ");
                System.out.print("ItemID : ");
                itemID = input.next();
                ArrayList<String> itemIDs = fh.searchMultipleRow("SupplierID", supplierID, "itemID");
                if (!itemIDs.contains(itemID)) {
                    System.out.println("The Supplier Not Selling This Item");
                    continue;
                }
                break;
            }
            editDetail.add(supplierID);
            editDetail.add(itemID);
            editDetail.add(editCol);
            editDetail.add(overrideContent);
        }
        return editDetail;
    }


    public static DailySales AddDailySalesMenu(SalesManager salesManager) {
        Scanner scanner = new Scanner(System.in);
        int quantity;
        String itemID;
        FileHandling salesFile = new FileHandling("ItemSales.txt");
        FileHandling fh = new FileHandling("Item.txt");
        ArrayList<Item> itemArrayList = new ArrayList<>();
        ArrayList<Integer> quantityArrayList = new ArrayList<>();
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        fh.printData();
//        * First Round
        while (true) {
            System.out.print("Item ID : ");
            itemID = scanner.next();
            if (fh.searchRow("itemID", itemID) == null) {
                System.out.println("Item ID not found in the database.");
                continue;
            }
            itemArrayList.add(Item.createItem(itemID));
            while (true) {
                System.out.print("Quantity : ");
                quantity = InputValidation.getInteger(1, 9999);
                if (!InputValidation.checkStock(itemID, quantity)) {
                    System.out.println("Stock insufficient");
                    continue;
                }
                break;
            }
            quantityArrayList.add(quantity);
            break;
        }
        while (true) {
            System.out.println("Please Enter the next item ID (Type \"Done\" to finish adding items) :");
            System.out.print("Item ID : ");
            itemID = scanner.next();
            if (itemID.equalsIgnoreCase("done")) {
                break;
            } else if (fh.searchRow("itemID", itemID) == null) {
                System.out.println("Item ID not found in the database.");
                continue;
            }else if (itemArrayList.contains(Item.createItem(itemID))){
                System.out.println("This item has been added.");
                continue;
            }
            itemArrayList.add(Item.createItem(itemID));
            System.out.print("Please enter the quantity : ");
            quantity = InputValidation.getInteger(1, 9999);
            quantityArrayList.add(quantity);
        }
        LocalDate currentDate = LocalDate.now();
        DailySales dailySales = new DailySales(salesFile.generateID(), String.valueOf(currentDate), itemArrayList, quantityArrayList, salesManager);
        System.out.println("Daily Item Wise Sales Added Successfully");
        return dailySales;
    }

    public static String deleteDailySalesMenu() {
        FileHandling itemFile = new FileHandling("ItemSales.txt");
        itemFile.groupPrinting();
        Scanner scanner = new Scanner(System.in);
        String salesID;
        while (true) {
            System.out.print("SalesID : ");
            salesID = scanner.next();
            if (itemFile.searchRow("salesID", salesID) == null) {
                System.out.println("Sales ID Not Found");
                continue;
            }
            break;
        }
        return salesID;
    }

    public static ArrayList<Object> editDailySales() {
        FileHandling salesFile = new FileHandling("ItemSales.txt");
        salesFile.groupPrinting();
        Scanner input = new Scanner(System.in);
        String salesID, itemID, newContent;
        ArrayList<String> itemIDs;
        while (true) {
            System.out.print("Sales ID : ");
            salesID = input.next();
            if (salesFile.searchRow("salesID", salesID) == null) {
                System.out.println("Sales ID Not Found");
                continue;
            }
            break;
        }
        while (true) {
            System.out.print("ItemID : ");
            itemID = input.next();
            itemIDs = salesFile.searchMultipleRow("salesID", salesID, "salesItem");
            if (!itemIDs.contains(itemID)) {
                System.out.println("The Daily Sales Not Selling This Item");
                continue;
            }
            break;
        }
        ArrayList<Object> editDetail = new ArrayList<>();
        editDetail.add(Item.createItem(itemID));

        System.out.println("What kind of information you want to edit?");
        System.out.print(""" 
                1. Sales Item
                2. Quantity
                3. Date
                Please enter your choice :\s""");
        int choice = InputValidation.getInteger(1, 4);
        String editCol;
        if (choice == 1) {
            FileHandling itemFile = new FileHandling("Item.txt");
            itemFile.printData();
            editCol = "salesItem";
            while (true) {
                System.out.print("New Sales Item ID : ");
                newContent = input.next();
//                Handle Duplicate
                if (itemIDs.contains(newContent)) {
                    System.out.println("This item is included in this daily sales");
                    continue;
                } else if (itemFile.searchRow("itemID", newContent) == null) {
                    System.out.println("The item with this itemID is not found");
                    continue;
                }
                break;
            }
        } else if (choice == 2) {
            editCol = "quantity";
            System.out.print("Enter new Quantity : ");
            newContent = Integer.toString(InputValidation.getInteger(1, 999999));
        } else {
            editCol = "date";
            System.out.print("Enter new Data :");
            newContent = InputValidation.getValidDate();
        }
        System.out.println(" ");
        editDetail.add(salesID);
        editDetail.add(editCol);
        editDetail.add(newContent);
        return editDetail;
    }

    public static ArrayList<Object> AddPrMenu(SalesManager salesManager) {
        String itemID;
        Scanner scanner = new Scanner(System.in);
        FileHandling f = new FileHandling("PurchaseRequisition.txt");
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        String purchaseRequisitionID = """
                                               Enter item detail below:
                                               1. PurchaseRequisitionIDID:\s""" + f.generateID();
        System.out.println(purchaseRequisitionID);
        System.out.println("2. UserID: " + salesManager.getUserID());
        FileHandling itemFiles = new FileHandling("Item.txt");
        while (true) {
            itemFiles.printData();
            System.out.print("3. itemID: ");
            itemID = scanner.next();
            if (itemFiles.searchRow("itemID", itemID) == null) {
                System.out.println("Item Not Found");
                continue;
            }
            break;
        }
        System.out.print("4. Quantity: ");
        int quantity = InputValidation.getInteger(1, 9999999);
        System.out.print("5. RequestDate: ");
        String requestDate = InputValidation.getValidDate();
        System.out.println();
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        System.out.println("Confirm purchaseRequisition detail is correct");
        System.out.println();
        System.out.println("PurchaseRequisitionID : " + f.generateID());
        System.out.println("UserID : " + salesManager.getUserID());
        System.out.println("ItemID : " + itemID);
        System.out.println("Quantity : " + quantity);
        System.out.println("Request Date : " + requestDate);

        System.out.println("=".repeat(80));
        System.out.println("1. Confirm");
        System.out.println("2. Discard Change");
        System.out.print("Please enter your choice : ");
        String choice = Integer.toString(InputValidation.getInteger(1, 2));
        ArrayList<Object> objects = new ArrayList<>();
        objects.add(choice);
        PurchaseRequisition purchaseRequisition = new PurchaseRequisition(f.generateID(), salesManager.getUserID(), Item.createItem(itemID), quantity, requestDate);
        objects.add(purchaseRequisition);
        System.out.println("Purchase Requisition Added Successfully");
        return objects;
    }

    public static String deletePrMenu() {
        FileHandling purchaseRequisitionFile = new FileHandling("PurchaseRequisition.txt");
        purchaseRequisitionFile.printData();
        Scanner scanner = new Scanner(System.in);
        String prID;
        while (true) {
            System.out.print("Enter PurchaseRequisitionID : ");
            prID = scanner.nextLine();
            if (purchaseRequisitionFile.searchRow("purchaseRequisitionID", prID) == null) {
                System.out.println("Purchase Requisition Not Found");
                continue;
            }
            break;
        }
        System.out.println("PurchaseRequisition Deleted Successfully");
        return prID;
    }

    public static ArrayList<String> editPrMenu(SalesManager salesManager) {
        String prID, newContent;
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        prFile.printData("userID", salesManager.getUserID());
        Scanner scanner = new Scanner(System.in);
        List<String> availableID =
                prFile.searchMultipleRow("userID", salesManager.getUserID(), "purchaseRequisitionID");
        System.out.println();
        while (true) {
            System.out.print("Enter PurchaseRequisitionID : ");
            prID = scanner.nextLine();
            if (!availableID.contains(prID)) {
                System.out.println("Purchase Requisition ID Not Found");
                continue;
            }
            break;
        }
        System.out.println("What kind of information you want to edit ? ");
        System.out.print(""" 
                1. ItemID
                2. Quantity
                Please enter your choice :\s""");
        int choice = InputValidation.getInteger(1, 2);
        String editCol;
        if (choice == 1) {
            editCol = "itemID";
            FileHandling itemFile = new FileHandling("Item.txt");
            itemFile.printData();
            while (true) {
                System.out.println("Please Enter The New Item ID");
                System.out.print("Item ID : ");
                newContent = scanner.next();
                if (itemFile.searchRow("itemID", newContent) == null) {
                    System.out.println("Item ID Not Found");
                    continue;
                }else if (prFile.searchRow("purchaseRequisitionID",prID).split(",")[2].equalsIgnoreCase(newContent)){
                    System.out.println("You cannot change back to the same item ID");
                    continue;
                }
                break;
            }
        } else {
            editCol = "quantity";
            System.out.print("Please Enter the new Quantity : ");
            newContent = Integer.toString(
                    InputValidation.getInteger(1, 99999999)
            );
        }
        ArrayList<String> editDetail = new ArrayList<>();
        editDetail.add(prID);
        editDetail.add(editCol);
        editDetail.add(newContent);
        System.out.println("Purchase Requisition Edited Successfully");
        return editDetail;
    }

    public static int purchaseManagerMenu() {
        Scanner scanner = new Scanner(System.in);
        printHeadingInConsole("Purchase Order Management System (POM)", 80);
        String salesManagerMenu = """
                1. Display Item
                2. Display Supplier
                3. Display Purchase Requisition
                4. Add Purchase Order
                5. Delete Purchase Order
                6. Edit Purchase Order
                7. Display Purchase Order
                8. Quit
                Please enter your choice :\s""";
        System.out.print(salesManagerMenu);
        int choice = scanner.nextInt();
        return choice;
    }

    public static PurchaseOrder AddPurchaseOrder(PurchaseManager purchaseManager) {
        Scanner scanner = new Scanner(System.in);
        String purchaseRequisitionID;
        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
        FileHandling purchaseRequisitionFile = new FileHandling("PurchaseRequisition.txt");
        String poID = poFile.generateID();
        ArrayList<PurchaseRequisition> prArrayList = new ArrayList<>();
        printHeadingInConsole("Purchase Order Management System (POM)", 100);
        purchaseRequisitionFile.printData();
        while (true) {
            System.out.print("Purchase Requisition ID : ");
            purchaseRequisitionID = scanner.next();
            if (purchaseRequisitionFile.searchRow("purchaseRequisitionID", purchaseRequisitionID) == null) {
                System.out.println("Purchase Requisition ID Not Found");
                continue;
            }
            prArrayList.add(PurchaseRequisition.createPr(purchaseRequisitionID));
            break;
        }
//        while (true) {
//            System.out.print("Purchase Requisition ID (Type \"Done\" to finish adding): ");
//            purchaseRequisitionID = scanner.next();
//            if (purchaseRequisitionID.equalsIgnoreCase("done"))
//                break;
//            if (prArrayList.contains(PurchaseRequisition.createPr(purchaseRequisitionID))) {
//                System.out.println("This Item Has Been Added");
//                continue;
//            } else if (purchaseRequisitionFile.searchRow("purchaseRequisitionID", purchaseRequisitionID) == null) {
//                System.out.println("Purchase Requisition ID Not Found");
//                continue;
//            }
//            prArrayList.add(PurchaseRequisition.createPr(purchaseRequisitionID));
//        }
        LocalDate currentDate = LocalDate.now();
        PurchaseOrder po = new PurchaseOrder(poID, prArrayList, purchaseManager, String.valueOf(currentDate));
        System.out.println("Purchase Order Added Successfully");
        return po;
    }


    public static String deletePurchaseOder() {
        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
        if (poFile.getRows() > 0) {
            Scanner scanner = new Scanner(System.in);
            String poID;

            poFile.printData();
            while (true) {
                System.out.print("Enter PurchaseOrderID : ");
                poID = scanner.next();
                if (poFile.searchRow("purchaseOrderID", poID) == null) {
                    System.out.println("Purchase Order ID Not Found");
                    continue;
                }
                break;
            }
            return poID;
        } else {
            return null;
        }
    }

    public static ArrayList<String> editPoMenu() {
        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
        String newContent, poID;
        poFile.printData();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Enter Purchase Order ID : ");
            poID = scanner.next();
            if (poFile.searchRow("purchaseOrderID", poID) == null) {
                System.out.println("Purchase Order Not Found");
                continue;
            }
            break;
        }
        System.out.println("What kind of information you want to edit ? ");
        System.out.print(""" 
                1. Purchase Requisition ID
                2. Quantity
                Please enter your choice :\s""");
        int choice = InputValidation.getInteger(1, 2);
        String editCol;
        scanner.nextLine();
        if (choice == 1) {
            FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
            prFile.printData();
            editCol = "purchaseRequisitionID";
            while (true) {
                System.out.print("Please Enter the new Purchase Requisition ID : ");
                newContent = scanner.nextLine();
                if (prFile.searchRow("purchaseRequisitionID", newContent) == null) {
                    System.out.println("Purchase Requisition Not Found");
                    continue;
                }
                break;
            }
        } else {
            editCol = "quantity";
            System.out.print("Please Enter The New Quantity : ");
            newContent = Integer.toString(
                    InputValidation.getInteger(1, 9999999)
            );
        }
        ArrayList<String> editDetail = new ArrayList<>();
        editDetail.add(poID);
        editDetail.add(editCol);
        editDetail.add(newContent);
        return editDetail;
    }
}
