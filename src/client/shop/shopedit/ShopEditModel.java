package client.shop.shopedit;

import client.ConnectionResources;
import client.ShopReadOnlyModel;
import common.request.RequestData;
import common.request.RequestType;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.Product;

import java.util.ArrayList;

public class ShopEditModel extends ShopReadOnlyModel {

    public ShopEditModel(String listName) {
        super(listName);
    }

    public boolean changeCurrencyType(CurrencyType newCurrencyType) {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_CHANGE_CURRENCY_TYPE, newCurrencyType));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean open(String username) {
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(username);
            data.add(this.getListName());
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_EDIT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception ignore) {
            return false;
        }
    }

    public boolean clear() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_CLEAR, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeCategory(String categoryName) {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_REMOVE_CATEGORY, categoryName));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeProduct(String categoryName, String productName) {
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(categoryName);
            data.add(productName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_REMOVE_PRODUCT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_DELETE, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean editProduct(String categoryName, String productName, Product newProductData) {
        try {
            ArrayList<String> data = new ArrayList<>(7);
            data.add(categoryName);
            data.add(productName);
            data.add(newProductData.getProductName());
            data.add(String.valueOf(newProductData.getUnitPrice()));
            data.add(newProductData.getCurrencyType().name());
            data.add(String.valueOf(newProductData.getUnitSize()));
            data.add(newProductData.getUnitType().name());
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_EDIT_PRODUCT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean increaseProductQuantity(String categoryName, String productName) {
        try {
            ArrayList<String> data = new ArrayList<>(3);
            data.add(categoryName);
            data.add(productName);
            data.add(String.valueOf(1));
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_INCREASE_PRODUCT_QUANTITY, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean decreaseProductQuantity(String categoryName, String productName) {
        try {
            ArrayList<String> data = new ArrayList<>(3);
            data.add(categoryName);
            data.add(productName);
            data.add(String.valueOf(1));
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_DECREASE_PRODUCT_QUANTITY, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean loadLocalVersion() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_LOAD_LOCAL_VERSION, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean loadPublicVersion() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_LOAD_PUBLIC_VERSION, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean saveLocalVersion() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_SAVE_LOCAL_VERSION, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean pushChangesToPublicVersion() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_PUSH_CHANGES_TO_PUBLIC_VERSION, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean close() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.SHOP_LIST_CLOSE, null));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
