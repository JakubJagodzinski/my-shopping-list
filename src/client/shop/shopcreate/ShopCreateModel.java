package client.shop.shopcreate;

import client.ConnectionResources;
import client.InputValidator;
import common.request.RequestData;
import common.request.RequestType;

import java.util.ArrayList;

public class ShopCreateModel {

    private final String username;

    public ShopCreateModel(String username) {
        this.username = username;
    }

    public boolean createNewShop(String shopName) {
        if (!InputValidator.isShopNameValid(shopName)) {
            return false;
        }
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(this.username);
            data.add(shopName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_CREATE, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

}
