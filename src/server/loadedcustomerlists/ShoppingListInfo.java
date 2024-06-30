package server.loadedcustomerlists;

public record ShoppingListInfo(String ownerName, String shopName, String listName) {

    @Override
    public String toString() {
        return this.ownerName + "/" + this.shopName + "/" + this.listName;
    }

    @Override
    public boolean equals(Object obj) {
        return this.ownerName.equals(((ShoppingListInfo) obj).ownerName) && this.shopName.equals(((ShoppingListInfo) obj).shopName) && this.listName.equals(((ShoppingListInfo) obj).listName);
    }

}
