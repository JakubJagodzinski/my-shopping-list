package client.customer.customerlist.customerlistclone;

import client.ConnectionResources;
import client.InputValidator;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class CustomerListCloneModel {

    private final String clonedListOwnerName;
    private final String listName;

    public CustomerListCloneModel(String listName, String clonedListOwnerName) {
        this.listName = listName;
        this.clonedListOwnerName = clonedListOwnerName;
    }

    public boolean clone(String clonedListName) {
        if (clonedListName.equals(this.listName) || !InputValidator.isCustomerListNameValid(clonedListName)) {
            return false;
        }
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(clonedListName);
            data.add(this.clonedListOwnerName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_CLONE, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

}
