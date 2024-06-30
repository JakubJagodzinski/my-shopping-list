package client.customer.customerlist.customerlistaddproduct;

import client.ConnectionResources;
import common.request.RequestData;
import common.request.RequestType;
import common.shoppinglistapi.Product;

import java.util.ArrayList;

public class CustomerListAddProductModel {

    private final String shopName;

    public CustomerListAddProductModel(String shopName) {
        this.shopName = shopName;
    }

    public ArrayList<String> getShopCategoriesNames() {
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

    public boolean addProduct(String categoryName, String productName) {
        try {
            ArrayList<String> data = new ArrayList<>(3);
            data.add(categoryName);
            data.add(productName);
            data.add(String.valueOf(1));
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_ADD_PRODUCT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

}
