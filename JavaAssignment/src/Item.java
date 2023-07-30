
public class Item {
    private String itemID;
    private String itemName;
    private int quantity;
    private String expireDate;
    private double sellPrice;
    private double supplyPrice;

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    public double getSupplyPrice() {
        return supplyPrice;
    }

    public Item(String itemID, String itemName, int quantity, String expireDate, double sellPrice, double supplyPrice) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.quantity = quantity;
        this.expireDate = expireDate;
        this.sellPrice = sellPrice;
        this.supplyPrice = supplyPrice;
    }
}
