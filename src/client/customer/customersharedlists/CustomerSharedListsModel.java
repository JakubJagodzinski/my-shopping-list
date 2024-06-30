package client.customer.customersharedlists;

import client.ConnectionResources;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class CustomerSharedListsModel {

    private final String username;

    public CustomerSharedListsModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<String> getSharedLists() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.GET_CUSTOMER_SHARED_LISTS, username));
            return (ArrayList<String>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }
    }

}
