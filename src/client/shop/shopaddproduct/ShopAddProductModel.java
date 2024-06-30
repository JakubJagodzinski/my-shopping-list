package client.shop.shopaddproduct;

import client.ConnectionResources;
import client.InputValidator;
import common.request.RequestData;
import common.request.RequestType;
import common.shoppinglistapi.Product;

import java.util.ArrayList;

public class ShopAddProductModel {

    public boolean addProduct(String categoryName, Product product) {
        if (!InputValidator.isCategoryNameValid(categoryName) || !InputValidator.isProductNameValid(product.getProductName())) {
            return false;
        }
        try {
            ArrayList<String> data = new ArrayList<>(7);
            data.add(categoryName);
            data.add(product.getProductName());
            data.add(String.valueOf(product.getQuantity()));
            data.add(String.valueOf(product.getUnitPrice()));
            data.add(String.valueOf(product.getCurrencyType()));
            data.add(String.valueOf(product.getUnitSize()));
            data.add(String.valueOf(product.getUnitType()));
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_ADD_PRODUCT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

}
