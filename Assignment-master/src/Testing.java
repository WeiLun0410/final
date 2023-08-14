public class Testing {
    public static void main(String[] args) {
//        Admin admin = new Admin();
//
//        admin.registration("A001", "123", "Admin", "Jerry", "012918291",
//                "wengkang@gmail.com");
//        admin.registration("P001", "123", "PurchaseManager", "Jerry", "012918291",
//                "wengkang@gmail.com");
//        admin.registration("S001", "123", "SalesManager", "Jerry", "012918291",
//                "wengkang@gmail.com");

        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
        poFile.editRow("purchaseOrderID", "o001", "purchaseRequisitionID", "P003");    }
}