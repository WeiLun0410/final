import java.time.LocalDate;

public interface Purchasable {
    default void AddPurchaseOrder(PurchaseOrder po) {
        LocalDate currentDate = LocalDate.now();
        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        for (PurchaseRequisition pr : po.getPurchaseRequisitionList()) {
            poFile.addRow(po.getPurchaseOrderID(), pr.getPurchaseRequisitionID(), po.getUser().getUserID(), pr.getUserID(), pr.getItem().getItemID(), String.valueOf(pr.getQuantity()), String.valueOf(currentDate), pr.getRequestDate());
            prFile.deleteRow("purchaseRequisitionID", pr.getPurchaseRequisitionID());
        }
    }

    default void DeletePurchaseOrder(String poID) {
        if (poID != null) {
            FileHandling poFile = new FileHandling("PurchaseOrder.txt");
            poFile.deleteRow("purchaseOrderID", poID);
        } else {
            System.out.println("No Purchase Order has been added");
        }
    }

    default void EditPurchaseOrder(String poID, String editCol, String newContent) {
        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
//        if (editCol.equalsIgnoreCase("purchaseRequisitionID")) {
//            String salesManagerID = poFile.searchRow("purchaseOrderID", poID).split(",")[3];
//            String quantity = poFile.searchRow("purchaseOrderID", poID).split(",")[4];
//        }
        poFile.editRow("purchaseOrderID", poID, editCol, newContent);
        System.out.println("Purchase Order Edited Successfully");
    }

//    default void AddPurchaseOrder(PurchaseOrder po) {
//        LocalDate currentDate = LocalDate.now();
//        FileHandling poFile = new FileHandling("PurchaseOrder.txt");
//        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
//        for (PurchaseRequisition pr : po.getPurchaseRequisitionList()) {
//            poFile.addRow(po.getPurchaseOrderID(), pr.getPurchaseRequisitionID(), po.getUser().getUserID(), pr.getUserID(), pr.getItem().getItemID(), String.valueOf(pr.getQuantity()), String.valueOf(currentDate));
//            prFile.deleteRow("purchaseRequisitionID", pr.getPurchaseRequisitionID());
//        }
//    }
}
