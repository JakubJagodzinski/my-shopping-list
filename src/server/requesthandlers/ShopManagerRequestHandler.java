package server.requesthandlers;

import common.request.RequestData;
import common.request.RequestType;
import common.shoppinglistapi.CurrencyType;
import common.shoppinglistapi.Product;
import common.shoppinglistapi.ShopList;
import common.shoppinglistapi.UnitType;
import server.*;
import server.loadedshopslist.LoadedShopLists;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ShopManagerRequestHandler {

    private final Socket clientSocket;
    private final ObjectInputStream ois;
    private final ObjectOutputStream oos;
    private final LoadedShopLists loadedShopLists;
    private final ShopList shopList;
    private final Changelog changelog;

    public ShopManagerRequestHandler(Socket clientSocket, ObjectInputStream ois, ObjectOutputStream oos, LoadedShopLists loadedShopLists, String shopManagerName, String shopName) {
        this.clientSocket = clientSocket;
        this.ois = ois;
        this.oos = oos;
        this.loadedShopLists = loadedShopLists;
        this.shopList = new ShopList(shopName, shopManagerName, shopManagerName);
        this.changelog = new Changelog(shopName, shopManagerName, shopName);
    }

    private void shopListClear() throws Exception {
        this.shopList.clear();
        this.oos.writeObject(true);
        this.changelog.addEntry(this.shopList.getEditorName() + " cleared \"" + this.shopList.getListName() + "\" shop list");
    }

    private void shopListGetTotalPrice() throws Exception {
        this.oos.writeObject(this.shopList.totalPrice());
    }

    private void shopListGetCurrencyType() throws Exception {
        this.oos.writeObject(this.shopList.getCurrencyType());
    }

    private void shopListChangeCurrencyType(RequestData requestData) throws Exception {
        this.shopList.setCurrencyType((CurrencyType) requestData.data());
        this.oos.writeObject(true);
    }

    private void shopListGetCategoriesNames() throws Exception {
        this.oos.writeObject(this.shopList.getCategoriesNames());
    }

    private void shopListRemoveCategory(RequestData requestData) throws Exception {
        String categoryName = (String) requestData.data();
        boolean operationStatus = this.shopList.removeCategory(categoryName);
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.shopList.getEditorName() + " removed \"" + categoryName + "\" category from shop");
        }
    }

    private void shopListGetProducts(RequestData requestData) throws Exception {
        String categoryName = (String) requestData.data();
        this.oos.writeObject(this.shopList.getProducts(categoryName));
    }

    private void shopListAddProduct(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        int productQuantity = Integer.parseInt(data.get(2));
        double unitPrice = Double.parseDouble(data.get(3));
        CurrencyType currencyType = CurrencyType.valueOf(data.get(4));
        double unitSize = Double.parseDouble(data.get(5));
        UnitType unitType = UnitType.valueOf(data.get(6));
        boolean operationStatus = this.shopList.addProduct(categoryName, new Product(productName, productQuantity, unitPrice, currencyType, unitSize, unitType));
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.shopList.getEditorName() + " added \"" + productName + "\" product in \"" + categoryName + "\" category to shop");
        }
    }

    private void shopListEditProduct(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        String newProductName = data.get(2);
        int productQuantity = this.shopList.getProductQuantity(categoryName, productName);
        double productUnitPrice = Double.parseDouble(data.get(3));
        CurrencyType productCurrencyType = CurrencyType.valueOf(data.get(4));
        double productUnitSize = Double.parseDouble(data.get(5));
        UnitType productUnitType = UnitType.valueOf(data.get(6));
        Product newProductData = new Product(newProductName, productQuantity, productUnitPrice, productCurrencyType, productUnitSize, productUnitType);
        boolean operationStatus = this.shopList.editProduct(categoryName, productName, newProductData);
        if (operationStatus) {
            this.changelog.addEntry(this.shopList.getEditorName() + " edited \"" + productName + "\" product from \"" + categoryName + "\" category");
        }
        this.oos.writeObject(operationStatus);
    }

    private void shopListIncreaseProductQuantity(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        int unitsToAdd = Integer.parseInt(data.get(2));
        boolean operationStatus = this.shopList.addUnitsOfProduct(categoryName, productName, unitsToAdd);
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.shopList.getEditorName() + " added " + unitsToAdd + "x \"" + productName + "\" product in \"" + categoryName + "\" category to shop");
        }
    }

    private void shopListDecreaseProductQuantity(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        int unitsToRemove = Integer.parseInt(data.get(2));
        boolean operationStatus = this.shopList.removeUnitsOfProduct(categoryName, productName, unitsToRemove);
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.shopList.getEditorName() + " removed " + unitsToRemove + "x \"" + productName + "\" product from \"" + categoryName + "\" category to shop");
        }
    }

    private void shopListRemoveProduct(RequestData requestData) throws Exception {
        ArrayList<String> data = (ArrayList<String>) requestData.data();
        String categoryName = data.get(0);
        String productName = data.get(1);
        boolean operationStatus = this.shopList.removeProduct(categoryName, productName);
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.addEntry(this.shopList.getEditorName() + " removed \"" + productName + "\" product from \"" + categoryName + "\" category to shop");
        }
    }

    private boolean shopListLoadLocalVersion() throws Exception {
        boolean operationStatus = this.shopList.loadLocalVersion();
        this.oos.writeObject(operationStatus);
        return operationStatus;
    }

    private void shopListSaveLocalVersion() throws Exception {
        boolean operationStatus = this.shopList.saveLocalVersion();
        this.oos.writeObject(operationStatus);
        if (operationStatus) {
            this.changelog.pushEntriesToDatabase();
        }
    }

    private void shopListLoadPublicVersion() throws Exception {
        this.oos.writeObject(this.shopList.loadPublicVersion());
    }

    private void shopListPushChangesToPublicVersion() throws Exception {
        boolean operationStatus = this.shopList.pushChangesToPublicVersion();
        this.oos.writeObject(operationStatus);
        if (operationStatus && this.loadedShopLists.containsShopList(this.shopList.getListName())) {
            this.loadedShopLists.reloadShopList(this.shopList.getListName());
        }
    }

    public boolean shopListDelete() throws Exception {
        boolean operationStatus = this.shopList.delete();
        this.oos.writeObject(operationStatus);
        return operationStatus;
    }

    public void run() {
        try {
            if (!this.shopListLoadLocalVersion()) {
                return;
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
                    case SHOP_LIST_CLEAR:
                        this.shopListClear();
                        break;
                    case SHOP_LIST_GET_TOTAL_PRICE:
                        this.shopListGetTotalPrice();
                        break;
                    case SHOP_LIST_GET_CURRENCY_TYPE:
                        this.shopListGetCurrencyType();
                        break;
                    case SHOP_LIST_CHANGE_CURRENCY_TYPE:
                        this.shopListChangeCurrencyType(requestData);
                        break;
                    case SHOP_LIST_GET_CATEGORIES_NAMES:
                        this.shopListGetCategoriesNames();
                        break;
                    case SHOP_LIST_REMOVE_CATEGORY:
                        this.shopListRemoveCategory(requestData);
                        break;
                    case SHOP_LIST_ADD_PRODUCT:
                        this.shopListAddProduct(requestData);
                        break;
                    case SHOP_LIST_REMOVE_PRODUCT:
                        this.shopListRemoveProduct(requestData);
                        break;
                    case SHOP_LIST_GET_PRODUCTS:
                        this.shopListGetProducts(requestData);
                        break;
                    case SHOP_LIST_EDIT_PRODUCT:
                        this.shopListEditProduct(requestData);
                        break;
                    case SHOP_LIST_DECREASE_PRODUCT_QUANTITY:
                        this.shopListDecreaseProductQuantity(requestData);
                        break;
                    case SHOP_LIST_INCREASE_PRODUCT_QUANTITY:
                        this.shopListIncreaseProductQuantity(requestData);
                        break;
                    case SHOP_LIST_SAVE_LOCAL_VERSION:
                        this.shopListSaveLocalVersion();
                        break;
                    case SHOP_LIST_PUSH_CHANGES_TO_PUBLIC_VERSION:
                        this.shopListPushChangesToPublicVersion();
                        break;
                    case SHOP_LIST_LOAD_LOCAL_VERSION:
                        this.shopListLoadLocalVersion();
                        break;
                    case SHOP_LIST_LOAD_PUBLIC_VERSION:
                        this.shopListLoadPublicVersion();
                        break;
                    case SHOP_LIST_DELETE:
                        if (this.shopListDelete()) {
                            this.loadedShopLists.setOwnerStatus(this.shopList.getListName(), false);
                            return;
                        }
                        break;
                    case SHOP_LIST_CLOSE:
                        this.loadedShopLists.setOwnerStatus(this.shopList.getListName(), false);
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
                this.loadedShopLists.setOwnerStatus(this.shopList.getListName(), false);
                return;
            }
        }
    }

}
