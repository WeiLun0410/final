import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder {
    private ArrayList<PurchaseRequisition> purchaseRequisitionList;
    private User user;
    private String requestDate;
    private String purchaseOrderID;


    public PurchaseOrder(String purchaseOrderID, List<PurchaseRequisition> prs, User user, String requestDate) {
        ArrayList<PurchaseRequisition> prArrayList = new ArrayList<>();
        this.user = user;
        this.requestDate = requestDate;
        this.purchaseOrderID = purchaseOrderID;
        this.purchaseRequisitionList = prArrayList;
        prArrayList.addAll(prs);
    }

    public ArrayList<PurchaseRequisition> getPurchaseRequisitionList() {
        return purchaseRequisitionList;
    }

    public User getUser() {
        return user;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public String getPurchaseOrderID() {
        return purchaseOrderID;
    }

}
