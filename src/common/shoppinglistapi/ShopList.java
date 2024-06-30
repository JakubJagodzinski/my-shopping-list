package common.shoppinglistapi;

import server.databaseapi.DatabaseOperations;
import server.databaseapi.DatabasePath;

public class ShopList extends ShoppingList {

    public ShopList(String shopListName, String ownerName, String editorName) {
        super(shopListName, ownerName, editorName, shopListName);
    }

    public ShopList(String shopListName) {
        super(shopListName, "", "", shopListName);
    }

    public boolean editProduct(String categoryName, String productName, Product newProductData) {
        this.removeProduct(categoryName, productName);
        return this.addProduct(categoryName, newProductData);
    }

    public boolean loadLocalVersion() {
        return this.loadFromFile(DatabasePath.shopLocalListFile(this.getOwnerName(), this.getListName()));
    }

    public boolean loadPublicVersion() {
        return this.loadFromFile(DatabasePath.shopPublicListFile(this.getListName()));
    }

    public boolean saveLocalVersion() {
        return this.saveToFile(DatabasePath.shopLocalListFile(this.getOwnerName(), this.getListName()));
    }

    public boolean pushChangesToPublicVersion() {
        return this.saveToFile(DatabasePath.shopPublicListFile(this.getListName()));
    }

    public boolean delete() {
        return DatabaseOperations.removeShop(this.getOwnerName(), this.getListName());
    }

}
