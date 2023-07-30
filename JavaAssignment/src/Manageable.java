
import java.time.LocalDate;
import java.util.Iterator;
public interface Manageable {
    default void AddItem(Item item){
        FileHandling itemFile = new FileHandling("Item.txt");
        itemFile.addRow(item.getItemID(), item.getItemName(), String.valueOf(item.getSellPrice()),String.valueOf(item.getQuantity()),item.getExpireDate() );
    }

    default void EditItem(Item item,String editCol,String newContent){
        FileHandling itemFile = new FileHandling("Item.txt");
        itemFile.editRow("itemID", item.getItemID(),editCol,newContent );
    }
    default void DeleteItem(Item item){
        FileHandling itemFile = new FileHandling("Item.txt");
        itemFile.deleteRow("itemID",item.getItemID());
    }
    default void AddSupplier(Supplier supplier) {
        FileHandling supplierFile = new FileHandling("Supplier.txt");
        Iterator iterator = supplier.getSupplierItem().iterator();
        Iterator iterator2 = supplier.getSupplierItemPrice().iterator();

        while (iterator.hasNext() && iterator2.hasNext()) { //when one supplier supply many item
            Item supplierItem = (Item) iterator.next();
            Double supplierItemPrice = (Double) iterator2.next();
            supplierFile.addRow(supplier.getSupplierID(), supplier.getSupplierName(), supplier.getLocation(), supplierItem.getItemID(),String.valueOf(supplierItemPrice));
        }
    }


    default void EditSupplier(  Supplier supplier, Item item,String editCol,String newContent){
        FileHandling supplierFile = new FileHandling("Supplier.txt");
        supplierFile.editRow("SupplierID",supplier.getSupplierID(),"itemID",item.getItemID(),editCol,newContent);
    }
    default void DeleteSupplier(Supplier supplier){
        FileHandling supplierFile = new FileHandling("Supplier.txt");
        supplierFile.deleteRow("SupplierID",supplier.getSupplierID());
    }


    default void AddDailyItemWiseSales(DailySales dailySales){
        FileHandling itemSalesFile = new FileHandling("ItemSales.txt");
        FileHandling itemFile = new FileHandling("Item.txt");
        String row = itemFile.searchRow("itemID", dailySales.getItem().getItemID());
        int totalPrice;
        LocalDate currentDate = LocalDate.now();
        if (row != null) {
            String[] items = row.split(",");
            totalPrice = Integer.parseInt(items[2]) * dailySales.getQuantity();
            int balanceStock = Integer.parseInt(items[3]) - dailySales.getQuantity();
            // Rest of your code to process the 'item' array
            itemSalesFile.addRow(dailySales.getSalesID(),dailySales.getItem().getItemID(),String.valueOf(dailySales.getQuantity()),String.valueOf(totalPrice),String.valueOf(currentDate), dailySales.getUser().getUserID());
            itemFile.editRow("itemID", dailySales.getItem().getItemID(), "quantity",String.valueOf(balanceStock));
        } else {
            // Handle the case when no matching row is found
            System.out.println("No matching row found.");
        }
    }

    default void DeleteDailyItemWiseSales(DailySales dailySales){
        FileHandling itemSalesFile = new FileHandling("ItemSales.txt");
        FileHandling itemFile = new FileHandling("Item.txt");
        itemSalesFile.deleteRow("salesID", dailySales.getSalesID());
        itemFile.editRow("itemID", dailySales.getItem().getItemID(), "quantity",String.valueOf(dailySales.getItem().getQuantity()));
    }


    default void EditDailyItemWiseSales(Item item,String salesID,String editCol,String newContent){
        FileHandling itemSalesFile = new FileHandling("ItemSales.txt");
        FileHandling itemFile = new FileHandling("Item.txt");
        itemSalesFile.editRow("salesID",salesID,editCol,newContent);
        if(editCol.equals("quantity")){
            itemSalesFile.editRow("salesID",salesID,"totalPrice",String.valueOf (item.getSellPrice() *Integer.parseInt(newContent)));
            itemFile.editRow("itemID",item.getItemID(),"quantity", String.valueOf(item.getQuantity() - Integer.parseInt(newContent)));
        }
    }

    default void EditDailyItemWiseSales(DailySales dailySales,String editCol,String newContent){
        FileHandling itemSalesFile = new FileHandling("ItemSales.txt");
        FileHandling itemFile = new FileHandling("Item.txt");
        if(editCol.equals("quantity")){
            itemSalesFile.editRow("salesID",dailySales.getSalesID(),"quantity",newContent);
            itemSalesFile.editRow("salesID",dailySales.getSalesID(),"totalPrice",String.valueOf (dailySales.getItem().getSellPrice() *Integer.parseInt(newContent)));
            itemFile.editRow("itemID",dailySales.getItem().getItemID(),"quantity", String.valueOf(dailySales.getItem().getQuantity() - Integer.parseInt(newContent)));
        }else{
            itemSalesFile.editRow("salesID", dailySales.getSalesID(),editCol,newContent);
        }
    }
    default void AddPurchaseRequisition(PurchaseRequisition pr){
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        prFile.addRow(pr.getPurchaseRequisitionID(),pr.getUser().getUserID(),pr.getItem().getItemID(),String.valueOf(pr.getQuantity()),pr.getRequestDate());
    }

    default void DeletePurchaseRequisition(PurchaseRequisition pr){
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        prFile.deleteRow("purchaseRequisitionID",pr.getPurchaseRequisitionID());
    }

    default void EditPurcahseRequisition(PurchaseRequisition pr,String editCol,String newContent){
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        prFile.editRow("purchaseRequisitionID", pr.getPurchaseRequisitionID(), "quantity","100");
    }


}
