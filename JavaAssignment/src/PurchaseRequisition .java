public class PurchaseRequisition {
    private String requestDate;
    private String purchaseRequisitionID;
    private  Item item;

    private int quantity;
    private User user;

    public PurchaseRequisition(String requestDate, String purchaseRequisitionID, Item item, int quantity, User user) {
        this.requestDate = requestDate;
        this.purchaseRequisitionID = purchaseRequisitionID;
        this.item = item;
        this.quantity = quantity;
        this.user  = user;
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

    public User getUser() {
        return user;
    }
}
