package server.loadedcustomerlists;

import common.shoppinglistapi.CustomerList;

import java.util.concurrent.ConcurrentHashMap;

public class LoadedCustomerLists {

    private final ConcurrentHashMap<ShoppingListInfo, CustomerListData> customerLists;

    public LoadedCustomerLists() {
        this.customerLists = new ConcurrentHashMap<>();
    }

    public synchronized void addCustomerList(CustomerList shoppingList) {
        this.customerLists.put(new ShoppingListInfo(shoppingList.getOwnerName(), shoppingList.getShopName(), shoppingList.getListName()), new CustomerListData(shoppingList, false, 1));
    }

    public synchronized CustomerList getCustomerList(ShoppingListInfo shoppingListInfo) {
        if (this.containsCustomerList(shoppingListInfo)) {
            return this.customerLists.get(shoppingListInfo).getCustomerList();
        }
        return null;
    }

    public synchronized boolean containsCustomerList(ShoppingListInfo shoppingListInfo) {

        return this.customerLists.containsKey(shoppingListInfo);
    }

    public void setOwnerConnected(ShoppingListInfo shoppingListInfo, boolean ownerConnected) {
        this.customerLists.get(shoppingListInfo).setOwnerConnected(ownerConnected);
    }

    public synchronized boolean customerListIsInUse(ShoppingListInfo shoppingListInfo) {
        CustomerListData customerListData = this.customerLists.get(shoppingListInfo);
        if (customerListData == null) {
            return false;
        }
        return customerListData.isOwnerConnected() || customerListData.getUsersConnected() > 0;
    }

    public synchronized void removeIfUnused(ShoppingListInfo shoppingListInfo) {
        if (!customerListIsInUse(shoppingListInfo)) {
            this.customerLists.remove(shoppingListInfo);
        }
    }

    public synchronized void incrementUsersCounter(ShoppingListInfo shoppingListInfo) {
        for (var customerListEntry : this.customerLists.entrySet()) {
            if (customerListEntry.getKey().equals(shoppingListInfo)) {
                customerListEntry.getValue().incrementUsersConnected();
                return;
            }
        }
    }

    public synchronized void decrementUsersCounter(ShoppingListInfo shoppingListInfo) {
        CustomerListData customerListData = this.customerLists.get(shoppingListInfo);
        if (customerListData != null) {
            customerListData.decrementUsersConnected();
            this.removeIfUnused(shoppingListInfo);
        }
    }

    public void printCustomerLists() {
        if (this.customerLists.isEmpty()) {
            System.out.println("There are no customer lists with active connections.");
            return;
        }
        System.out.println("Customer lists loaded:");
        System.out.println("[owner name] / [shop name] / [list name] / [is owner connected] / [customers connected]");
        for (var customerList : this.customerLists.entrySet()) {
            System.out.println(customerList.getKey() + " / " + customerList.getValue().isOwnerConnected() + " / " + customerList.getValue().getUsersConnected());
        }
        System.out.println("Total customer lists loaded: " + this.customerLists.size());
    }

}
