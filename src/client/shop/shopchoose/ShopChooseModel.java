package client.shop.shopchoose;

import client.ConnectionResources;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class ShopChooseModel {

    private final String username;

    public ShopChooseModel(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public ArrayList<String> getShopsNames() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.GET_USER_SHOPS_NAMES, this.username));
            return (ArrayList<String>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }
    }

}
