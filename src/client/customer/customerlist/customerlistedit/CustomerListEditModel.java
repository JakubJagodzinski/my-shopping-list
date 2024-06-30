package client.customer.customerlist.customerlistedit;

import client.ConnectionResources;
import client.ShopReadOnlyModel;
import common.request.RequestData;
import common.request.RequestType;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.Product;

import java.util.ArrayList;

public class CustomerListEditModel {

    private final String ownerName;
    private final String editorName;
    private final String customerListName;
    private final ShopReadOnlyModel shop;
    private final boolean hasFullAccess;

    public CustomerListEditModel(String ownerName, String editorName, String shopName, String customerListName, boolean hasFullAccess) {
        this.ownerName = ownerName;
        this.editorName = editorName;
        this.shop = new ShopReadOnlyModel(shopName);
        this.customerListName = customerListName;
        this.hasFullAccess = hasFullAccess;
    }

    public String getOwnerName() {
        return this.ownerName;
    }

    public String getEditorName() {
        return this.editorName;
    }

    public String getShopName() {
        return this.shop.getListName();
    }

    public String getCustomerListName() {
        return this.customerListName;
    }

    public boolean hasFullAccess() {
        return this.hasFullAccess;
    }

    public double getTotalPrice() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_GET_TOTAL_PRICE, null));
            double totalPrice = (double) ConnectionResources.getOis().readObject();
            return Math.round(totalPrice * 100) / 100.0;
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean removeUnavailableProducts() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_REMOVE_UNAVAILABLE_PRODUCTS, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean open() {
        try {
            ArrayList<String> data = new ArrayList<>(4);
            data.add(this.editorName);
            data.add(this.ownerName);
            data.add(this.shop.getListName());
            data.add(this.customerListName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_EDIT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean clear() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_CLEAR, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public ArrayList<String> getCategoriesNames() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_GET_CATEGORIES_NAMES, null));
            return (ArrayList<String>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean removeCategory(String categoryName) {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_REMOVE_CATEGORY, categoryName));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception ignore) {
            return false;
        }
    }

    public ArrayList<Product> getProducts(String categoryName) {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_GET_PRODUCTS, categoryName));
            return (ArrayList<Product>) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean removeProduct(String categoryName, String productName) {
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(categoryName);
            data.add(productName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_REMOVE_PRODUCT, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean increaseProductQuantity(String categoryName, String productName) {
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(categoryName);
            data.add(productName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_INCREASE_PRODUCT_QUANTITY, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception ignore) {
            return false;
        }
    }

    public boolean decreaseProductQuantity(String categoryName, String productName) {
        try {
            ArrayList<String> data = new ArrayList<>(2);
            data.add(categoryName);
            data.add(productName);
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_DECREASE_PRODUCT_QUANTITY, data));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception ignore) {
            return false;
        }
    }

    public CurrencyType getCurrencyType() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_GET_CURRENCY_TYPE, null));
            return (CurrencyType) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return CurrencyType.PLN;
        }
    }

    public boolean changeCurrencyType(CurrencyType newCurrencyType) {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_CHANGE_CURRENCY_TYPE, newCurrencyType));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean save() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_SAVE, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean reload() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_RELOAD, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_DELETE, null));
            return (boolean) ConnectionResources.getOis().readObject();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean close() {
        try {
            ConnectionResources.getOos().writeObject(new RequestData(RequestType.CUSTOMER_LIST_CLOSE, null));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
