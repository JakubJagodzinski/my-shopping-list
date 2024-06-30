package client.customer.customerlist.customerlistshare;

import client.ConnectionResources;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class CustomerListShareModel {

    private final String username;

    public CustomerListShareModel(String username) {
        this.username = username;
    }

    public boolean share(String userToShareWithName, boolean fullAccess) {
        if (userToShareWithName.isEmpty() || this.username.equals(userToShareWithName)) {
            return false;
        }
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(userToShareWithName);
            data.add(String.valueOf(fullAccess));
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_SHARE, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

}
