package server.loadedshopslist;

import common.shoppinglistapi.ShopList;

import java.util.concurrent.ConcurrentHashMap;

public class LoadedShopLists {

    private final ConcurrentHashMap<String, ShopData> shopLists;

    public LoadedShopLists() {
        this.shopLists = new ConcurrentHashMap<>();
    }

    public synchronized void addShopList(ShopList shopList) {
        this.shopLists.put(shopList.getListName(), new ShopData(shopList, false, 1));
    }

    public synchronized ShopList getShopList(String shopListName) {
        if (this.containsShopList(shopListName)) {
            return this.shopLists.get(shopListName).getShop();
        }
        return null;
    }

    public void reloadShopList(String shopListName) {
        ShopList shopList = this.getShopList(shopListName);
        if (shopList != null) {
            System.out.println("\"" + shopListName + "\" shop list reloaded");
            shopList.loadPublicVersion();
        }
    }

    public synchronized boolean containsShopList(String shopListName) {
        return this.shopLists.containsKey(shopListName);
    }

    public synchronized boolean isShopListInUse(String shopListName) {
        ShopData shopData = this.shopLists.get(shopListName);
        if (shopData == null) {
            return false;
        }
        return shopData.isOwnerConnected() || shopData.getUsersConnected() > 0;
    }

    public synchronized void removeIfUnused(String shopName) {
        if (!isShopListInUse(shopName)) {
            this.shopLists.remove(shopName);
        }
    }

    public synchronized void setOwnerStatus(String wantedShopListName, boolean isConnected) {
        for (var shopListName : this.shopLists.keySet()) {
            if (shopListName.equals(wantedShopListName)) {
                this.shopLists.get(shopListName).setOwnerConnected(isConnected);
                this.removeIfUnused(shopListName);
                return;
            }
        }
    }

    public synchronized void incrementUsersCounter(String shopListName) {
        ShopData shopData = this.shopLists.get(shopListName);
        if (shopData != null) {
            shopData.incrementUsersConnected();
        }
    }

    public synchronized void decrementUsersCounter(String shopListName) {
        ShopData shopData = this.shopLists.get(shopListName);
        if (shopData != null) {
            shopData.decrementUsersConnected();
            this.removeIfUnused(shopListName);
        }
    }

    public void print() {
        if (this.shopLists.isEmpty()) {
            System.out.println("There are no public shop versions loaded to server memory.");
            return;
        }
        System.out.println("Public shop versions loaded:");
        System.out.println("[shop name] / [is owner connected] / [customers connected]");
        for (var shop : this.shopLists.values()) {
            System.out.println(shop);
        }
        System.out.println("Total public shop versions loaded: " + this.shopLists.size());
    }

}
