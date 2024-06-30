package client.customer.customersharedlists;

public class CustomerListInfo {

    private final String ownerName;
    private final String shopName;
    private final String customerListName;
    private final boolean hasFullAccess;

    public CustomerListInfo(String sharedListPath) {
        String[] info = sharedListPath.split("/");
        this.ownerName = info[0];
        this.shopName = info[1];
        this.customerListName = info[2];
        this.hasFullAccess = info[3].equals("full access");
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getShopName() {
        return this.shopName;
    }

    public String getCustomerListName() {
        return this.customerListName;
    }

    public boolean hasFullAccess() {
        return this.hasFullAccess;
    }

}
