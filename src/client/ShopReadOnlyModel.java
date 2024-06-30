package client;

import common.request.RequestData;
import common.request.RequestType;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.Product;

import java.util.ArrayList;

public class ShopReadOnlyModel {

    private final String listName;

    public ShopReadOnlyModel(String shopName) {
        this.listName = shopName;
    }

    public String getListName() {
        return this.listName;
    }

    public double getTotalPrice() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_GET_TOTAL_PRICE, null));
            double totalPrice = (double) ConnectionResources.getOis().readObject();
            return Math.round(totalPrice * 100) / 100.0;
        } catch (Exception e) {
            return 0;
        }
    }

    public CurrencyType getCurrencyType() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_GET_CURRENCY_TYPE, null));
            return (CurrencyType) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return CurrencyType.PLN;
        }
    }

    public ArrayList<String> getCategoriesNames() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_GET_CATEGORIES_NAMES, null));
            return (ArrayList<String>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }

    }

    public ArrayList<Product> getProducts(String categoryName) {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_GET_PRODUCTS, categoryName));
            return (ArrayList<Product>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }
    }

}
