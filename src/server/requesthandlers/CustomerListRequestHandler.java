package server.requesthandlers;

import common.request.RequestData;
import common.request.RequestType;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.CustomerList;
import common.shoppinglistapi.ShopList;
import server.*;
import server.loadedcustomerlists.LoadedCustomerLists;
import server.loadedcustomerlists.ShoppingListInfo;
import server.loadedshopslist.LoadedShopLists;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CustomerListRequestHandler {

    private final Socket clientSocket;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final LoadedShopLists loadedShopLists;
    private final LoadedCustomerLists loadedCustomerLists;
    private ShopList shopList;
    private final boolean isCustomerListOwner;
    private CustomerList customerList;
    private final Changelog changelog;

    public CustomerListRequestHandler(Socket clientSocket, ObjectInputStream ois, ObjectOutputStream oos, LoadedShopLists loadedShopLists, LoadedCustomerLists loadedCustomerLists, String customerListName, String ownerName, String editorName, String shopName) {
        this.clientSocket = clientSocket;
        this.ois = ois;
        this.oos = oos;
        this.isCustomerListOwner = (!editorName.isEmpty() && !ownerName.isEmpty()) && editorName.equals(ownerName);
        this.shopList = new ShopList(shopName);
        this.loadedShopLists = loadedShopLists;
        this.loadedCustomerLists = loadedCustomerLists;
        this.customerList = new CustomerList(customerListName, ownerName, editorName, this.shopList);
        this.changelog = new Changelog(customerListName, ownerName, shopName);
    }

    private boolean shopListLoad() {
        if (this.loadedShopLists.containsShopList(this.shopList.getListName())) {
            this.shopList = this.loadedShopLists.getShopList(this.shopList.getListName());
            this.loadedShopLists.incrementUsersCounter(this.shopList.getListName());
        } else {
            if (!this.shopList.loadPublicVersion()) {
                return false;
            }
            this.loadedShopLists.addShopList(this.shopList);
        }
        this.customerList.setShopList(this.shopList);
        return this.shopList != null;
    }

    private void shopListGetCategoriesNames() throws Exception {
        this.oos.writeObject(this.shopList.getCategoriesNames());
    }

    private void shopListGetProducts(RequestData requestData) throws Exception {
        String categoryName = (String) requestData.data();
        this.oos.writeObject(this.shopList.getProducts(categoryName));
    }

    private boolean customerListLoad() {
        ShoppingListInfo shoppingListInfo = new ShoppingListInfo(this.customerList.getOwnerName(), this.customerList.getShopName(), this.customerList.getListName());
        if (this.loadedCustomerLists.containsCustomerList(shoppingListInfo)) {
            this.customerList = this.loadedCustomerLists.getCustomerList(shoppingListInfo);
            this.loadedCustomerLists.incrementUsersCounter(shoppingListInfo);
        } else {
            this.customerList.load();
            this.loadedCustomerLists.addCustomerList(this.customerList);
        }
        if (this.isCustomerListOwner) {
            this.loadedCustomerLists.setOwnerConnected(shoppingListInfo, this.isCustomerListOwner);
        }
        return this.shopList != null;
    }

    private void customerListClear() throws Exception {
        this.customerList.clear();
        this.oos.writeObject(true);
        this.changelog.addEntry(this.customerList.getEditorName() + ": cleared shopping list");
    }

    private void customerListGetTotalPrice() throws Exception {
        this.oos.writeObject(this.customerList.totalPrice());
    }

    private void customerListGetCurrencyType() throws Exception {
        this.oos.writeObject(this.customerList.getCurrencyType());
    }

    private void customerListChangeCurrencyType(RequestData requestData) throws Exception {
        this.customerList.setCurrencyType((CurrencyType) requestData.data());
        this.oos.writeObject(true);
    }

    private void customerListGetCategoriesNames() throws Exception {
        this.oos.writeObject(this.customerList.getCategoriesNames());
    }

    private void customerListRemoveCategory(RequestData requestData) throws Exception {
        String categoryName = (String) requestData.data();
        boolean operationStatus = this.customerList.removeCategory(categoryName);
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.customerList.getEditorName() + ": removed \"" + categoryName + "\" category");
        }
    }

    private void customerListGetProducts(RequestData requestData) throws Exception {
        String categoryName = (String) requestData.data();
        this.oos.writeObject(this.customerList.getProducts(categoryName));
    }

    private void customerListAddProduct(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        int productQuantity = Integer.parseInt(data.get(2));
        boolean operationStatus = this.customerList.addProductFromShop(categoryName, productName, productQuantity);
        if (operationStatus) {
            this.changelog.addEntry(this.customerList.getEditorName() + ": added " + productQuantity + "x \"" + productName + "\" product to \"" + categoryName + "\" category");
        }
        this.oos.writeObject(operationStatus);
    }

    private void customerListRemoveProduct(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        boolean operationStatus = this.customerList.removeProduct(categoryName, productName);
        if (this.customerList.isCategoryEmpty(categoryName)) {
            this.customerList.removeCategory(categoryName);
        }
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.customerList.getEditorName() + ": removed \"" + productName + "\" product from \"" + categoryName + "\" category");
        }
    }

    private void customerListRemoveProductsUnavailableInShop() throws Exception {
        this.customerList.removeProductsUnavailableInShop();
        this.oos.writeObject(true);
    }

    private void customerListIncreaseProductQuantity(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        this.customerList.removeProductsUnavailableInShop();
        boolean operationStatus = this.customerList.addUnitsOfProduct(categoryName, productName, 1);
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.customerList.getEditorName() + ": added " + 1 + "x \"" + productName + "\" product to \"" + categoryName + "\" category");
        }
    }

    private void customerListDecreaseProductQuantity(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        this.customerList.removeProductsUnavailableInShop();
        boolean operationStatus = this.customerList.removeUnitsOfProduct(categoryName, productName, 1);
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.customerList.getEditorName() + ": removed " + 1 + "x \"" + productName + "\" product from \"" + categoryName + "\" category");
        }
    }

    private void customerListReload() throws Exception {
        boolean operationStatus = this.customerList.load();
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.clear();
        }
    }

    private void customerListSave() throws Exception {
        boolean operationStatus = this.customerList.save();
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.pushEntriesToDatabase();
        }
    }

    private void customerListClone(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String clonedListName = data.get(0);
        String clonedListOwnerName = data.get(1);
        this.oos.writeObject(this.customerList.clone(clonedListName, clonedListOwnerName));
    }

    private void customerListShare(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String userToShareWith = data.get(0);
        boolean fullAccess = Boolean.parseBoolean(data.get(1));
        boolean operationStatus = this.customerList.share(userToShareWith, fullAccess);
        this.oos.writeObject(operationStatus);
    }

    private boolean customerListDelete() throws Exception {
        if (this.customerList.getEditorName().equals(this.customerList.getOwnerName())) {
            boolean operationStatus = this.customerList.delete();
            this.oos.writeObject(operationStatus);
            if (operationStatus) {
                this.loadedShopLists.decrementUsersCounter(this.shopList.getListName());
            }
            return operationStatus;
        }
        return false;
    }

    public void run() {
        try {
            if (!this.shopListLoad() || !this.customerListLoad()) {
                this.oos.writeObject(false);
                return;
            } else {
                this.oos.writeObject(true);
            }
        } catch (Exception e) {
            return;
        }
        while (true) {
            try {
                RequestData requestData = (RequestData) this.ois.readObject();
                RequestType requestType = requestData.header();
                String log = TimeManager.parseToDate(System.currentTimeMillis()) + " " + Main.getSocketInfo(this.clientSocket) + ": request " + requestType;
                ServerLogsRegister.addLog(log);
                TextFormatter.printlnYellow(log);
                switch (requestType) {
                    case SHOP_LIST_GET_CATEGORIES_NAMES:
                        this.shopListGetCategoriesNames();
                        break;
                    case SHOP_LIST_GET_PRODUCTS:
                        this.shopListGetProducts(requestData);
                        break;
                    case CUSTOMER_LIST_GET_CATEGORIES_NAMES:
                        this.customerListGetCategoriesNames();
                        break;
                    case CUSTOMER_LIST_REMOVE_CATEGORY:
                        this.customerListRemoveCategory(requestData);
                        break;
                    case CUSTOMER_LIST_GET_TOTAL_PRICE:
                        this.customerListGetTotalPrice();
                        break;
                    case CUSTOMER_LIST_GET_PRODUCTS:
                        this.customerListGetProducts(requestData);
                        break;
                    case CUSTOMER_LIST_ADD_PRODUCT:
                        this.customerListAddProduct(requestData);
                        break;
                    case CUSTOMER_LIST_INCREASE_PRODUCT_QUANTITY:
                        this.customerListIncreaseProductQuantity(requestData);
                        break;
                    case CUSTOMER_LIST_DECREASE_PRODUCT_QUANTITY:
                        this.customerListDecreaseProductQuantity(requestData);
                        break;
                    case CUSTOMER_LIST_REMOVE_PRODUCT:
                        this.customerListRemoveProduct(requestData);
                        break;
                    case CUSTOMER_LIST_REMOVE_UNAVAILABLE_PRODUCTS:
                        this.customerListRemoveProductsUnavailableInShop();
                        break;
                    case CUSTOMER_LIST_GET_CURRENCY_TYPE:
                        this.customerListGetCurrencyType();
                        break;
                    case CUSTOMER_LIST_CHANGE_CURRENCY_TYPE:
                        this.customerListChangeCurrencyType(requestData);
                        break;
                    case CUSTOMER_LIST_CLEAR:
                        this.customerListClear();
                        break;
                    case CUSTOMER_LIST_RELOAD:
                        this.customerListReload();
                        break;
                    case CUSTOMER_LIST_SAVE:
                        this.customerListSave();
                        break;
                    case CUSTOMER_LIST_CLONE:
                        this.customerListClone(requestData);
                        break;
                    case CUSTOMER_LIST_SHARE:
                        this.customerListShare(requestData);
                        break;
                    case CUSTOMER_LIST_DELETE:
                        if (this.customerListDelete()) {
                            return;
                        }
                        break;
                    case CUSTOMER_LIST_CLOSE:
                        if (this.isCustomerListOwner) {
                            this.loadedCustomerLists.setOwnerConnected(this.customerList.info(), false);
                        }
                        this.loadedCustomerLists.decrementUsersCounter(this.customerList.info());
                        this.loadedShopLists.decrementUsersCounter(this.shopList.getListName());
                        return;
                    default:
                        log = TimeManager.parseToDate(System.currentTimeMillis()) + " " + Main.getSocketInfo(this.clientSocket) + ": request unknown - " + requestType;
                        ServerLogsRegister.addLog(log);
                        TextFormatter.printlnRed(log);
                        this.oos.writeObject(false);
                }
            } catch (Exception e) {
                String log = TimeManager.parseToDate(System.currentTimeMillis()) + " " + Main.getSocketInfo(this.clientSocket) + ": " + e.getMessage();
                ServerLogsRegister.addLog(log);
                System.out.println(log);
                if (this.isCustomerListOwner) {
                    this.loadedCustomerLists.setOwnerConnected(this.customerList.info(), false);
                }
                this.loadedCustomerLists.decrementUsersCounter(this.customerList.info());
                this.loadedShopLists.decrementUsersCounter(this.shopList.getListName());
                return;
            }
        }
    }

}
