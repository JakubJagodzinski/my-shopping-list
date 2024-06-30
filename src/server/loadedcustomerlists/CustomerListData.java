package server.loadedcustomerlists;

import common.shoppinglistapi.CustomerList;

public class CustomerListData {

    private final CustomerList customerList;
    private boolean isOwnerConnected;
    private int usersConnected;

    public CustomerListData(CustomerList customerList, boolean isOwnerConnected, int usersConnected) {
        this.customerList = customerList;
        this.isOwnerConnected = isOwnerConnected;
        this.usersConnected = usersConnected;
    }

    public CustomerList getCustomerList() {
        return this.customerList;
    }

    @Override
    public String toString() {
        return this.getCustomerListName() + " / " + this.isOwnerConnected + " / " + this.usersConnected;
    }

    public String getCustomerListName() {
        return this.customerList.getListName();
    }

    public boolean isOwnerConnected() {
        return this.isOwnerConnected;
    }

    public void setOwnerConnected(boolean ownerConnected) {
        this.isOwnerConnected = ownerConnected;
    }

    public int getUsersConnected() {
        return this.usersConnected;
    }

    public void incrementUsersConnected() {
        ++this.usersConnected;
    }

    public void decrementUsersConnected() {
        --this.usersConnected;
    }

}
