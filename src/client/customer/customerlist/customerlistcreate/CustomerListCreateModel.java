package client.customer.customerlist.customerlistcreate;

import client.ConnectionResources;
import client.InputValidator;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class CustomerListCreateModel {

    private final String username;

    public CustomerListCreateModel(String username) {
        this.username = username;
    }

    public boolean createNewShoppingList(String shopName, String shoppingListName) {
        if (!InputValidator.isCustomerListNameValid(shoppingListName)) {
            return false;
        }
        try {
            ArrayList<String> data = new ArrayList<>(3);
            data.add(this.username);
            data.add(shopName);
            data.add(shoppingListName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_CREATE, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<String> getDatabaseShopsNames() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.GET_DATABASE_SHOPS_NAMES, null));
            return (ArrayList<String>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }
    }

}
