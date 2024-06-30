package client.customer.customerchooseshop;

import client.ConnectionResources;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class CustomerChooseShopModel {

    private final String username;

    public CustomerChooseShopModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<String> getCustomerShopsNames() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.GET_USER_SHOPS_NAMES, this.username));
            return (ArrayList<String>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }
    }

}
