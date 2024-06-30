package common.shoppinglistapi;

import server.databaseapi.DatabaseOperations;
import server.databaseapi.DatabasePath;

public class CustomerList extends ShoppingList {

    private ShopList shopList;

    public CustomerList(String listName, String ownerName, String editorName, ShopList shopList) {
        super(listName, ownerName, editorName, shopList.getListName());
        this.shopList = shopList;
    }

    public String getShopName() {
        return this.shopList.getListName();
    }

    public void setShopList(ShopList shopList) {
        this.shopList = shopList;
    }

    public void removeProductsUnavailableInShop() {
        for (var categoryName : this.getCategoriesNames()) {
            if (!this.shopList.containsCategory(categoryName)) {
                this.removeCategory(categoryName);
            } else {
                for (var productName : this.getProductsNames(categoryName)) {
                    if (!this.shopList.containsProductInCategory(categoryName, productName)) {
                        this.removeProduct(categoryName, productName);
                    }
                }
            }
        }
    }

    public boolean addProductFromShop(String categoryName, String productName, int unitsToAdd) {
        if (!this.shopList.containsProductInCategory(categoryName, productName)) {
            return false;
        }
        if (this.containsProductInCategory(categoryName, productName)) {
            return this.addUnitsOfProduct(categoryName, productName, unitsToAdd);
        } else {
            return this.addProduct(categoryName, this.shopList.copyProduct(categoryName, productName));
        }
    }

    public boolean share(String userToShareWith, boolean fullAccess) {
        return DatabaseOperations.shareCustomerList(this.getOwnerName(), this.getShopName(), this.getListName(), userToShareWith, fullAccess);
    }

    public boolean delete() {
        return DatabaseOperations.deleteCustomerList(this.getOwnerName(), this.getShopName(), this.getListName());
    }

    public boolean load() {
        this.removeProductsUnavailableInShop();
        return this.loadFromFile(DatabasePath.customerListFile(this.getOwnerName(), this.getShopName(), this.getListName()));
    }

    public boolean save() {
        this.removeProductsUnavailableInShop();
        return this.saveToFile(DatabasePath.customerListFile(this.getOwnerName(), this.getShopName(), this.getListName()));
    }

    public boolean clone(String clonedListName, String clonedListOwnerName) {
        if (DatabaseOperations.createCustomerList(clonedListOwnerName, this.getShopName(), clonedListName)) {
            return this.saveToFile(DatabasePath.customerListFile(clonedListOwnerName, this.getShopName(), clonedListName));
        }
        return false;
    }

}
