package server.loadedshopslist;

import common.shoppinglistapi.ShopList;

public class ShopData {

    private final ShopList shop;
    private boolean isOwnerConnected;
    private int usersConnected;

    public ShopData(ShopList shop, boolean isOwnerConnected, int usersConnected) {
        this.shop = shop;
        this.isOwnerConnected = isOwnerConnected;
        this.usersConnected = usersConnected;
    }

    public ShopList getShop() {
        return this.shop;
    }

    public String getShopName() {
        return this.shop.getListName();
    }

    public boolean isOwnerConnected() {
        return this.isOwnerConnected;
    }

    public void setOwnerConnected(boolean ownerConnected) {
        this.isOwnerConnected = ownerConnected;
    }

    public int getUsersConnected() {
        return this.usersConnected;
    }

    public void incrementUsersConnected() {
        ++this.usersConnected;
    }

    public void decrementUsersConnected() {
        --this.usersConnected;
    }

    @Override
    public String toString() {
        return this.getShopName() + " / " + this.isOwnerConnected + " / " + this.usersConnected;
    }

}
