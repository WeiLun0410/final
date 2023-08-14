import java.util.Objects;

public class PurchaseRequisition {
    private String requestDate;
    private String purchaseRequisitionID;
    private Item item;
    private int quantity;
    private String userID;

    public PurchaseRequisition(String purchaseRequisitionID, String userID, Item item, int quantity, String requestDate) {
        this.purchaseRequisitionID = purchaseRequisitionID;
        this.userID = userID;
        this.item = item;
        this.quantity = quantity;
        this.requestDate = requestDate;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getPurchaseRequisitionID() {
        return purchaseRequisitionID;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUserID() {
        return userID;
    }

    public static PurchaseRequisition createPr(String prID) {
        FileHandling prFile = new FileHandling("PurchaseRequisition.txt");
        FileHandling itemFile = new FileHandling("Item.txt");
        String line = prFile.searchRow("purchaseRequisitionID", prID);

        if (line != null) {
            String[] pr = line.split(",");
            PurchaseRequisition prObj = new PurchaseRequisition(pr[0], pr[1], Item.createItem(pr[2]), Integer.parseInt(pr[3]), pr[4]);
            return prObj;
        } else {
            return null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PurchaseRequisition that = (PurchaseRequisition) o;
        return quantity == that.quantity && Objects.equals(requestDate, that.requestDate) && Objects.equals(purchaseRequisitionID, that.purchaseRequisitionID) && Objects.equals(item, that.item) && Objects.equals(userID, that.userID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestDate, purchaseRequisitionID, item, quantity, userID);
    }
}
