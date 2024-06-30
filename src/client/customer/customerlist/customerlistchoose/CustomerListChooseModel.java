package client.customer.customerlist.customerlistchoose;

import client.ConnectionResources;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class CustomerListChooseModel {

    private final String username;
    private final String shopName;

    public CustomerListChooseModel(String username, String shopName) {
        this.username = username;
        this.shopName = shopName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getShopName() {
        return this.shopName;
    }

    public ArrayList<String> getShoppingListsNames() {
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(this.username);
            data.add(this.shopName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.GET_CUSTOMER_LISTS_NAMES, data));
            return (ArrayList<String>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }
    }

}
